package com.dados.br.hub.service;

import com.dados.br.hub.dto.JobInfo;
import com.dados.br.hub.dto.ResultadoDadosApiDto;
import com.dados.br.hub.enums.JobStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class JobStatusServiceTest {

    private final JobStatusService jobStatusService = new JobStatusService();

    @Test
    @DisplayName("Deve salvar job e buscar pelo id")
    void deveSalvarJobEBuscarPeloId() {
        JobInfo jobInfo = new JobInfo("job-123", JobStatus.RECEBIDO);

        jobStatusService.save(jobInfo);

        Optional<JobInfo> resultado = jobStatusService.findById("job-123");

        assertTrue(resultado.isPresent());
        assertEquals("job-123", resultado.get().getJobId());
        assertEquals(JobStatus.RECEBIDO, resultado.get().getStatus());
    }

    @Test
    @DisplayName("Deve retornar vazio quando job não existir")
    void deveRetornarVazioQuandoJobNaoExistir() {
        Optional<JobInfo> resultado = jobStatusService.findById("job-inexistente");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve atualizar status do job")
    void deveAtualizarStatusDoJob() {
        JobInfo jobInfo = new JobInfo("job-123", JobStatus.RECEBIDO);
        jobStatusService.save(jobInfo);

        jobStatusService.updateStatus("job-123", JobStatus.PROCESSANDO);

        Optional<JobInfo> resultado = jobStatusService.findById("job-123");

        assertTrue(resultado.isPresent());
        assertEquals(JobStatus.PROCESSANDO, resultado.get().getStatus());
    }

    @Test
    @DisplayName("Não deve lançar erro ao atualizar status de job inexistente")
    void naoDeveLancarErroAoAtualizarStatusDeJobInexistente() {
        assertDoesNotThrow(() ->
                jobStatusService.updateStatus("job-inexistente", JobStatus.PROCESSANDO)
        );
    }

    @Test
    @DisplayName("Deve atualizar resultado e marcar job como FINALIZADO")
    void deveAtualizarResultadoEMarcarJobComoFinalizado() {
        String jobId = UUID.randomUUID().toString();

        JobInfo jobInfo = new JobInfo(jobId, JobStatus.PROCESSANDO);
        ResultadoDadosApiDto resultadoDados = new ResultadoDadosApiDto();

        jobStatusService.save(jobInfo);

        jobStatusService.updateResultado(jobId, resultadoDados);

        Optional<JobInfo> resultado = jobStatusService.findById(jobId);

        assertTrue(resultado.isPresent());
        assertSame(resultadoDados, resultado.get().getResultado());
        assertEquals(JobStatus.FINALIZADO, resultado.get().getStatus());
    }

    @Test
    @DisplayName("Não deve lançar erro ao atualizar resultado de job inexistente")
    void naoDeveLancarErroAoAtualizarResultadoDeJobInexistente() {
        ResultadoDadosApiDto resultadoDados = new ResultadoDadosApiDto();

        assertDoesNotThrow(() ->
                jobStatusService.updateResultado("job-inexistente", resultadoDados)
        );
    }

    @Test
    @DisplayName("Deve atualizar erro e marcar job como ERRO")
    void deveAtualizarErroEMarcarJobComoErro() {
        String jobId = UUID.randomUUID().toString();

        JobInfo jobInfo = new JobInfo(jobId, JobStatus.PROCESSANDO);
        String mensagemErro = "Erro ao consultar API externa";

        jobStatusService.save(jobInfo);

        jobStatusService.updateErro(jobId, mensagemErro);

        Optional<JobInfo> resultado = jobStatusService.findById(jobId);

        assertTrue(resultado.isPresent());
        assertEquals(mensagemErro, resultado.get().getMensagemErro());
        assertEquals(JobStatus.ERRO, resultado.get().getStatus());
    }

    @Test
    @DisplayName("Não deve lançar erro ao atualizar erro de job inexistente")
    void naoDeveLancarErroAoAtualizarErroDeJobInexistente() {
        assertDoesNotThrow(() ->
                jobStatusService.updateErro("job-inexistente", "Erro")
        );
    }
}
