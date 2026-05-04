package com.dados.br.hub.service;

import com.dados.br.hub.produce.JobProducer;
import com.dados.br.hub.dto.*;
import com.dados.br.hub.enums.JobStatus;
import com.dados.br.hub.service.client.CambioService;
import com.dados.br.hub.service.client.CnpjService;
import com.dados.br.hub.service.client.FeriadoService;
import com.dados.br.hub.service.client.ViaCepService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@Slf4j
@Singleton
public class ProcessamentoDadosApisExternas {

    private final JobStatusService jobStatusService;
    private final JobProducer jobProducer;
    private final ExecutorService executorService;

    private final CnpjService cnpjService;
    private final ViaCepService viaCepService;
    private final FeriadoService feriadoService;
    private final CambioService cambioService;


    public ProcessamentoDadosApisExternas(JobStatusService jobStatusService, JobProducer jobProducer,
                                          @Named("enriquecimento-executor") ExecutorService executorService,
                                          CnpjService cnpjService, ViaCepService viaCepService,
                                          FeriadoService feriadoService, CambioService cambioService) {
        this.jobStatusService = jobStatusService;
        this.jobProducer = jobProducer;
        this.executorService = executorService;
        this.cnpjService = cnpjService;
        this.viaCepService = viaCepService;
        this.feriadoService = feriadoService;
        this.cambioService = cambioService;
    }

    public void processarDados(ConsultaSolicitadaEvent consultaSolicitadaEvent){
        String jobId = consultaSolicitadaEvent.getJobId();
        JobResponseDto request = consultaSolicitadaEvent.getDados();

        log.info("[Inicioando Processamento] Processando dados para o job {}", jobId);
        jobStatusService.updateStatus(jobId, JobStatus.PROCESSANDO);

        Map<String,String> errosParciais = new HashMap<>();

        try {
            log.info("[CEP] Processando cep");
            CompletableFuture<Object> cep = CompletableFuture.supplyAsync(
                    () -> {
                        try{
                            log.info("[CEP] Buscando cep");
                            return viaCepService.buscar(request.getCep());
                        }catch (Exception e){
                            log.error("[Erro CEP] Erro ao buscar cep:{}", e.getMessage());
                            errosParciais.put("cep", e.getMessage());
                            return null;
                        }
                    }, executorService
            );

            log.info("[CNPJ] Processando Cnpj");
            CompletableFuture<Object> cnpj = CompletableFuture.supplyAsync(
                    () -> {
                        try{
                            log.info("[CNPJ] Buscando Cnpj");
                            return cnpjService.buscarCnpj(request.getCnpj());
                        }catch (Exception e){
                            log.error("[Erro CNPJ] Erro ao buscar Cnpj:{}", e.getMessage());
                            errosParciais.put("Cnpj", e.getMessage());
                            return null;
                        }
                    }, executorService
            );

            log.info("[FERIADOS] Processando Feriados");
            CompletableFuture<Object> feriados = CompletableFuture.supplyAsync(
                    () -> {
                        try{
                            log.info("[FERIADOS] Buscando Feriados");
                            return feriadoService.buscarFeriados(request.getAnoFeriado());
                        }catch (Exception e){
                            log.error("[Erro FERIADOS] Erro ao buscar Feriados:{}", e.getMessage());
                            errosParciais.put("Feriados", e.getMessage());
                            return null;
                        }
                    }, executorService
            );

            log.info("[CAMBIO] Processando Cambio");
            CompletableFuture<Object> cambio = CompletableFuture.supplyAsync(
                    () -> {
                        try{
                            log.info("[CAMBIO] Buscando Cambio");
                            return cambioService.buscarValorCambial(request.getMoedaCambio(),request.getAnoCambio().toString());
                        }catch (Exception e){
                            log.error("[Erro CAMBIO] Erro ao buscar Cambio:{}", e.getMessage());
                            errosParciais.put("Cambio", e.getMessage());
                            return null;
                        }
                    }, executorService
            );

            CompletableFuture.allOf(
                    cep, cnpj, feriados, cambio
            ).join();

            ResultadoDadosApiDto resultado = new ResultadoDadosApiDto();
            resultado.setCep(cep.join());
            resultado.setCnpj(cnpj.join());
            resultado.setFeriados((List<?>) feriados.join());
            resultado.setCambio(cambio.join());
            resultado.setErrosParciais(errosParciais);

            jobStatusService.updateResultado(jobId, resultado);
            /*
            TODO:
              ResponseCompletoDto -> Criar um DTO para ter um resultado com poucas informaçoes sem precisar retornar tudo
             fazer um convert de ResultadoDadosApiDto para ResponseCompletoDto mostrar apenas alguns dados
            */
            //jobProducer.enviarJobProcessado(new ConsultaProcessadaEvent(jobId, resultado)); -> resultado seria do ResponseCompletoDto
            jobProducer.enviarJobProcessado(new ConsultaProcessadaEvent(jobId, resultado));
        }catch (Exception e){
            log.error("[Erro] Erro ao consumir apis :{}, enviando para fila de erro", e.getMessage());
            jobStatusService.updateErro(jobId, e.getMessage());
            jobProducer.enviarJobErro(new ConsultaErroEvent(jobId, e.getMessage()));
        }
    }
}
