package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class ConsultarDadosApiResponse {

    @NotBlank
    private String cep;

    @NotBlank
    private String cnpj;

    @NotNull
    private Integer anoFeriado;

    @NotBlank
    private String moedaCambio;

    @NotBlank
    private String anoCambio;
}
