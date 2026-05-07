package com.dados.br.hub.service;

import com.dados.br.hub.dto.*;
import com.dados.br.hub.entity.JobEntity;
import com.dados.br.hub.enums.JobStatus;
import com.dados.br.hub.mapper.JobResultadoMapper;
import com.dados.br.hub.produce.JobProducer;
import com.dados.br.hub.repository.JobRepository;
import com.dados.br.hub.service.client.CambioService;
import com.dados.br.hub.service.client.CnpjService;
import com.dados.br.hub.service.client.FeriadoService;
import com.dados.br.hub.service.client.ViaCepService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProcessamentoDadosApisExternasTest {

    private JobStatusService jobStatusService;
    private JobProducer jobProducer;
    private ExecutorService executorService;

    private CnpjService cnpjService;
    private ViaCepService viaCepService;
    private FeriadoService feriadoService;
    private CambioService cambioService;
    private JobRepository jobRepository;
    private JobResultadoMapper jobResultadoMapper;

    private ProcessamentoDadosApisExternas processamentoDadosApisExternas;

    @BeforeEach
    void setup() {
        jobStatusService = mock(JobStatusService.class);
        jobProducer = mock(JobProducer.class);
        executorService = Executors.newFixedThreadPool(4);

        cnpjService = mock(CnpjService.class);
        viaCepService = mock(ViaCepService.class);
        feriadoService = mock(FeriadoService.class);
        cambioService = mock(CambioService.class);
        jobRepository = mock(JobRepository.class);
        jobResultadoMapper = mock(JobResultadoMapper.class);

        processamentoDadosApisExternas = new ProcessamentoDadosApisExternas(
                jobStatusService,
                jobProducer,
                executorService,
                cnpjService,
                viaCepService,
                feriadoService,
                cambioService,
                jobRepository,
                jobResultadoMapper
        );
    }

    @AfterEach
    void tearDown() {
        executorService.shutdownNow();
    }

    @Test
    @DisplayName("Deve processar dados com sucesso, atualizar status, salvar resultado e enviar evento processado")
    void deveProcessarDadosComSucesso() {
        String jobId = "job-123";

        JobResponseDto request = new JobResponseDto();
        request.setCep("31160100");
        request.setCnpj("12345678000199");
        request.setAnoFeriado(2026);
        request.setMoedaCambio("USD");
        request.setAnoCambio(LocalDate.of(2026, 5, 1));

        ConsultaSolicitadaEvent event = new ConsultaSolicitadaEvent(jobId, request);

        ViaCepResponseDto cepResponse = new ViaCepResponseDto();
        CnpjResponseDto cnpjResponse = new CnpjResponseDto();
        FeriadoResponseDto feriadoResponse = new FeriadoResponseDto();
        ContacaoResponseDto cotacaoResponse = new ContacaoResponseDto();

        JobEntity jobEntity = new JobEntity();
        jobEntity.setJobId(jobId);

        when(viaCepService.buscar("31160100")).thenReturn(cepResponse);
        when(cnpjService.buscarCnpj("12345678000199")).thenReturn(cnpjResponse);
        when(feriadoService.buscarFeriados(2026)).thenReturn(List.of(feriadoResponse));
        when(cambioService.cotacao("USD", "2026-05-01")).thenReturn(cotacaoResponse);
        when(jobResultadoMapper.toEntity(eq(jobId), any(ResultadoDadosApiDto.class))).thenReturn(jobEntity);

        processamentoDadosApisExternas.processarDados(event);

        verify(jobStatusService).updateStatus(jobId, JobStatus.PROCESSANDO);

        verify(viaCepService).buscar("31160100");
        verify(cnpjService).buscarCnpj("12345678000199");
        verify(feriadoService).buscarFeriados(2026);
        verify(cambioService).cotacao("USD", "2026-05-01");

        ArgumentCaptor<ResultadoDadosApiDto> resultadoCaptor =
                ArgumentCaptor.forClass(ResultadoDadosApiDto.class);

        verify(jobStatusService).updateResultado(eq(jobId), resultadoCaptor.capture());

        ResultadoDadosApiDto resultado = resultadoCaptor.getValue();

        assertSame(cepResponse, resultado.getCep());
        assertSame(cnpjResponse, resultado.getCnpj());
        assertSame(cotacaoResponse, resultado.getCotacao());
        assertTrue(resultado.getErrosParciais().isEmpty());

        verify(jobResultadoMapper).toEntity(eq(jobId), same(resultado));
        verify(jobRepository).save(jobEntity);

        ArgumentCaptor<ConsultaProcessadaEvent> processadaCaptor =
                ArgumentCaptor.forClass(ConsultaProcessadaEvent.class);

        verify(jobProducer).enviarJobProcessado(processadaCaptor.capture());

        ConsultaProcessadaEvent eventoProcessado = processadaCaptor.getValue();

        assertEquals(jobId, eventoProcessado.getJobId());
        assertSame(resultado, eventoProcessado.getDados());

        verify(jobProducer, never()).enviarJobErro(any());
        verify(jobStatusService, never()).updateErro(anyString(), anyString());
    }

    @Test
    @DisplayName("Deve registrar erro parcial quando uma API externa falhar e continuar processamento")
    void deveRegistrarErroParcialQuandoApiExternaFalhar() {
        String jobId = "job-erro-parcial";

        JobResponseDto request = new JobResponseDto();
        request.setCep("31160100");
        request.setCnpj("12345678000199");
        request.setAnoFeriado(2026);
        request.setMoedaCambio("USD");
        request.setAnoCambio(LocalDate.of(2026, 5, 1));

        ConsultaSolicitadaEvent event = new ConsultaSolicitadaEvent(jobId, request);

        ViaCepResponseDto cepResponse = new ViaCepResponseDto();
        FeriadoResponseDto feriadoResponse = new FeriadoResponseDto();
        ContacaoResponseDto cotacaoResponse = new ContacaoResponseDto();

        JobEntity jobEntity = new JobEntity();
        jobEntity.setJobId(jobId);

        when(viaCepService.buscar("31160100")).thenReturn(cepResponse);
        when(cnpjService.buscarCnpj("12345678000199")).thenThrow(new RuntimeException("CNPJ indisponível"));
        when(feriadoService.buscarFeriados(2026)).thenReturn(List.of(feriadoResponse));
        when(cambioService.cotacao("USD", "2026-05-01")).thenReturn(cotacaoResponse);
        when(jobResultadoMapper.toEntity(eq(jobId), any(ResultadoDadosApiDto.class))).thenReturn(jobEntity);

        processamentoDadosApisExternas.processarDados(event);

        ArgumentCaptor<ResultadoDadosApiDto> resultadoCaptor =
                ArgumentCaptor.forClass(ResultadoDadosApiDto.class);

        verify(jobStatusService).updateResultado(eq(jobId), resultadoCaptor.capture());

        ResultadoDadosApiDto resultado = resultadoCaptor.getValue();

        assertSame(cepResponse, resultado.getCep());
        assertNull(resultado.getCnpj());
        assertSame(cotacaoResponse, resultado.getCotacao());
        assertEquals("CNPJ indisponível", resultado.getErrosParciais().get("Cnpj"));

        verify(jobRepository).save(jobEntity);
        verify(jobProducer).enviarJobProcessado(any(ConsultaProcessadaEvent.class));
        verify(jobProducer, never()).enviarJobErro(any());
        verify(jobStatusService, never()).updateErro(anyString(), anyString());
    }

    @Test
    @DisplayName("Deve enviar evento de erro quando ocorrer falha geral no processamento")
    void deveEnviarEventoDeErroQuandoOcorrerFalhaGeral() {
        String jobId = "job-falha-geral";

        JobResponseDto request = new JobResponseDto();
        request.setCep("31160100");
        request.setCnpj("12345678000199");
        request.setAnoFeriado(2026);
        request.setMoedaCambio("USD");
        request.setAnoCambio(LocalDate.of(2026, 5, 1));

        ConsultaSolicitadaEvent event = new ConsultaSolicitadaEvent(jobId, request);

        ViaCepResponseDto cepResponse = new ViaCepResponseDto();
        CnpjResponseDto cnpjResponse = new CnpjResponseDto();
        ContacaoResponseDto cotacaoResponse = new ContacaoResponseDto();

        when(viaCepService.buscar("31160100")).thenReturn(cepResponse);
        when(cnpjService.buscarCnpj("12345678000199")).thenReturn(cnpjResponse);
        when(feriadoService.buscarFeriados(2026)).thenReturn(List.of(new FeriadoResponseDto()));
        when(cambioService.cotacao("USD", "2026-05-01")).thenReturn(cotacaoResponse);

        when(jobResultadoMapper.toEntity(eq(jobId), any(ResultadoDadosApiDto.class)))
                .thenThrow(new RuntimeException("Erro ao converter resultado"));

        processamentoDadosApisExternas.processarDados(event);

        verify(jobStatusService).updateStatus(jobId, JobStatus.PROCESSANDO);
        verify(jobStatusService).updateErro(eq(jobId), contains("Erro ao converter resultado"));

        ArgumentCaptor<ConsultaErroEvent> erroCaptor =
                ArgumentCaptor.forClass(ConsultaErroEvent.class);

        verify(jobProducer).enviarJobErro(erroCaptor.capture());

        ConsultaErroEvent erroEvent = erroCaptor.getValue();

        assertEquals(jobId, erroEvent.getJobId());
        assertTrue(erroEvent.getMensagemErro().contains("Erro ao converter resultado"));

        verify(jobRepository, never()).save(any());
        verify(jobProducer, never()).enviarJobProcessado(any());
    }
}

