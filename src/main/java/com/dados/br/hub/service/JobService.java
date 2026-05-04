package com.dados.br.hub.service;

import com.dados.br.hub.dto.*;
import com.dados.br.hub.produce.JobProducer;
import com.dados.br.hub.enums.JobStatus;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Singleton
public class JobService {

    private final JobStatusService jobStatusService;
    private final JobProducer jobProducer;

    public JobService(JobStatusService jobStatusService, JobProducer jobProducer) {
        this.jobStatusService = jobStatusService;
        this.jobProducer = jobProducer;
    }

    public JobResponse criarJob(JobResponseDto request) {
        log.info("[Criando Job] Recebendo dados para enriquecimento");
        String jobId = UUID.randomUUID().toString();

        log.info("[Criando Job] Salvando jobId: {}", jobId);
        JobInfo jobInfo = new JobInfo(jobId, JobStatus.RECEBIDO);
        jobStatusService.save(jobInfo);

        log.info("[Criando Job] Enviando jobId para fila de processamento");
        ConsultaSolicitadaEvent event = new ConsultaSolicitadaEvent(jobId, request);
        jobProducer.enviarJobRecebido(event);

        log.info("[Criando Job] Job criado com sucesso");
        return new JobResponse(jobId, JobStatus.RECEBIDO.name());
    }

    public JobInfo findById(String jobId) {
        log.info("[Buscando Job] Buscando jobId: {}", jobId);
        return jobStatusService.findById(jobId).orElse(null);
    }
}
