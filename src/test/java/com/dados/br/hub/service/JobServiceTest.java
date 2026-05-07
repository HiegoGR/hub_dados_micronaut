package com.dados.br.hub.service;

import com.dados.br.hub.dto.JobInfo;
import com.dados.br.hub.dto.JobResponse;
import com.dados.br.hub.dto.JobResponseDto;
import com.dados.br.hub.dto.ConsultaSolicitadaEvent;
import com.dados.br.hub.enums.JobStatus;
import com.dados.br.hub.produce.JobProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobStatusService jobStatusService;

    @Mock
    private JobProducer jobProducer;

    @InjectMocks
    private JobService jobService;

    private JobResponseDto request;

    @BeforeEach
    void setup() {
        request = new JobResponseDto();
    }

    @Test
    @DisplayName("Deve criar job com status RECEBIDO e enviar evento para Kafka")
    void deveCriarJobComStatusRecebidoEEnviarEventoParaKafka() {
        JobResponse response = jobService.criarJob(request);

        assertNotNull(response);
        assertNotNull(response.getJobId());
        assertEquals(JobStatus.RECEBIDO.name(), response.getStatus());

        ArgumentCaptor<JobInfo> jobInfoCaptor = ArgumentCaptor.forClass(JobInfo.class);
        verify(jobStatusService).save(jobInfoCaptor.capture());

        JobInfo jobSalvo = jobInfoCaptor.getValue();

        assertEquals(response.getJobId(), jobSalvo.getJobId());
        assertEquals(JobStatus.RECEBIDO, jobSalvo.getStatus());

        ArgumentCaptor<ConsultaSolicitadaEvent> eventCaptor =
                ArgumentCaptor.forClass(ConsultaSolicitadaEvent.class);

        verify(jobProducer).enviarJobRecebido(eventCaptor.capture());

        ConsultaSolicitadaEvent evento = eventCaptor.getValue();

        assertEquals(response.getJobId(), evento.getJobId());
        assertSame(request, evento.getDados());

        verifyNoMoreInteractions(jobStatusService, jobProducer);
    }

    @Test
    @DisplayName("Deve buscar job pelo id quando existir")
    void deveBuscarJobPeloIdQuandoExistir() {
        String jobId = UUID.randomUUID().toString();
        JobInfo jobInfo = new JobInfo(jobId, JobStatus.RECEBIDO);

        when(jobStatusService.findById(jobId)).thenReturn(Optional.of(jobInfo));

        JobInfo resultado = jobService.findById(jobId);

        assertNotNull(resultado);
        assertEquals(jobId, resultado.getJobId());
        assertEquals(JobStatus.RECEBIDO, resultado.getStatus());

        verify(jobStatusService).findById(jobId);
        verifyNoMoreInteractions(jobStatusService);
        verifyNoInteractions(jobProducer);
    }

    @Test
    @DisplayName("Deve retornar null quando job não existir")
    void deveRetornarNullQuandoJobNaoExistir() {
        String jobId = UUID.randomUUID().toString();

        when(jobStatusService.findById(jobId)).thenReturn(Optional.empty());

        JobInfo resultado = jobService.findById(jobId);

        assertNull(resultado);

        verify(jobStatusService).findById(jobId);
        verifyNoMoreInteractions(jobStatusService);
        verifyNoInteractions(jobProducer);
    }
}


