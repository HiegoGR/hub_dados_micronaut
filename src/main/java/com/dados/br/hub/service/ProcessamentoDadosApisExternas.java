package com.dados.br.hub.service;

import com.dados.br.hub.entity.JobEntity;
import com.dados.br.hub.mapper.JobResultadoMapper;
import com.dados.br.hub.produce.JobProducer;
import com.dados.br.hub.dto.*;
import com.dados.br.hub.enums.JobStatus;
import com.dados.br.hub.repository.JobRepository;
import com.dados.br.hub.service.client.CambioService;
import com.dados.br.hub.service.client.CnpjService;
import com.dados.br.hub.service.client.FeriadoService;
import com.dados.br.hub.service.client.ViaCepService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
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
    private final JobRepository jobRepository;
    private final JobResultadoMapper jobResultadoMapper;


    public ProcessamentoDadosApisExternas(JobStatusService jobStatusService, JobProducer jobProducer,
                                          @Named("enriquecimento-executor") ExecutorService executorService,
                                          CnpjService cnpjService, ViaCepService viaCepService,
                                          FeriadoService feriadoService, CambioService cambioService,
                                          JobRepository jobRepository, JobResultadoMapper jobResultadoMapper) {
        this.jobStatusService = jobStatusService;
        this.jobProducer = jobProducer;
        this.executorService = executorService;
        this.cnpjService = cnpjService;
        this.viaCepService = viaCepService;
        this.feriadoService = feriadoService;
        this.cambioService = cambioService;
        this.jobRepository = jobRepository;
        this.jobResultadoMapper = jobResultadoMapper;
    }

    public void processarDados(ConsultaSolicitadaEvent consultaSolicitadaEvent){
        String jobId = consultaSolicitadaEvent.getJobId();
        JobResponseDto request = consultaSolicitadaEvent.getDados();

        log.info("[Inicioando Processamento] Processando dados para o job {}", jobId);
        jobStatusService.updateStatus(jobId, JobStatus.PROCESSANDO);

        Map<String,String> errosParciais = new ConcurrentHashMap<>();

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

            log.info("[COTACAO] Processando Cotacao");
            CompletableFuture<Object> cotacao = CompletableFuture.supplyAsync(
                    () -> {
                        try{
                            log.info("[COTACAO] Buscando Cotacao");
                            return cambioService.cotacao(request.getMoedaCambio(),request.getAnoCambio().toString());
                        }catch (Exception e){
                            log.error("[Erro COTACAO] Erro ao buscar Cotacao:{}", e.getMessage());
                            errosParciais.put("Cotacao", e.getMessage());
                            return null;
                        }
                    }, executorService
            );

            CompletableFuture.allOf(
                    cep, cnpj, feriados, cotacao
            ).join();

            ResultadoDadosApiDto resultado = new ResultadoDadosApiDto();
            resultado.setCep(cep.join());
            resultado.setCnpj(cnpj.join());
            resultado.setFeriados((List<?>) feriados.join());
            resultado.setCotacao(cotacao.join());
            resultado.setErrosParciais(errosParciais);

            jobStatusService.updateResultado(jobId, resultado);

            JobEntity jobEntity = jobResultadoMapper.toEntity(jobId, resultado);
            jobRepository.save(jobEntity);

            log.info("[Finalizando Processamento] Processamento finalizado com sucesso, enviando para fila de processamento:");
            jobProducer.enviarJobProcessado(new ConsultaProcessadaEvent(jobId, resultado));
        }catch (Exception e){
            log.error("[Erro] Erro ao consumir apis :{}, enviando para fila de erro", e.getMessage());
            jobStatusService.updateErro(jobId, e.getMessage());
            jobProducer.enviarJobErro(new ConsultaErroEvent(jobId, e.getMessage()));
        }
    }
}
