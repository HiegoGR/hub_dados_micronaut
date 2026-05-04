package com.dados.br.hub.dto;

import com.dados.br.hub.enums.JobStatus;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class JobInfo {

    private String jobId;
    private JobStatus status;
    private ResultadoDadosApiDto resultado;
    private String mensagemErro;

    public JobInfo(String jobId, JobStatus status) {
        this.jobId = jobId;
        this.status = status;
    }
}