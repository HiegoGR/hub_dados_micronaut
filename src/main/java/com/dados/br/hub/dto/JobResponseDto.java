package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Serdeable
@Data
@NoArgsConstructor
@Introspected
public class JobResponseDto {
    @NotBlank
    private String cep;

    @NotBlank
    private String cnpj;

    @NotNull
    private Integer anoFeriado;

    @NotBlank
    private String moedaCambio;

    private LocalDate anoCambio;
}
