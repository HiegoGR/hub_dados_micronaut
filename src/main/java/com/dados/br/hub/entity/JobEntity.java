package com.dados.br.hub.entity;

import com.dados.br.hub.enums.JobStatus;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Serdeable
@Table(name = "job_processamento")
public class JobEntity {

    @Id
    private String jobId;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cnpj_id")
    private CnpjEntity cnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "via_cep_id")
    private ViaCepEntity viaCep;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private List<FeriadoEntity> feriados;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private List<CotacaoEntity> cotacao;

    @Column(columnDefinition = "TEXT")
    private String errosParciais;

}
