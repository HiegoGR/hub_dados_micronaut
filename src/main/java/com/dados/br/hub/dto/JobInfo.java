package com.dados.br.hub.dto;

import com.dados.br.hub.enums.JobStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Serdeable
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