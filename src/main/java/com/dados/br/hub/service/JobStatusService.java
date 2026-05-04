package com.dados.br.hub.service;

import com.dados.br.hub.dto.JobInfo;
import com.dados.br.hub.dto.ResultadoDadosApiDto;
import com.dados.br.hub.enums.JobStatus;
import jakarta.inject.Singleton;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class JobStatusService {

    private final Map<String, JobInfo> jobs = new ConcurrentHashMap<>();

    public void save(JobInfo jobInfo) {
        jobs.put(jobInfo.getJobId(), jobInfo);
    }

    public Optional<JobInfo> findById(String jobId) {
        return Optional.ofNullable(jobs.get(jobId));
    }

    public void updateStatus(String jobId, JobStatus status) {
        JobInfo job = jobs.get(jobId);
        if (job != null) {
            job.setStatus(status);
        }
    }

    public void updateResultado(String jobId, ResultadoDadosApiDto resultado) {
        JobInfo job = jobs.get(jobId);
        if (job != null) {
            job.setResultado(resultado);
            job.setStatus(JobStatus.FINALIZADO);
        }
    }

    public void updateErro(String jobId, String erro) {
        JobInfo job = jobs.get(jobId);
        if (job != null) {
            job.setMensagemErro(erro);
            job.setStatus(JobStatus.ERRO);
        }
    }
}
