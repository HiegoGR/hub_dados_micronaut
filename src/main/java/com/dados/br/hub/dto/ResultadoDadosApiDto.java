package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class ResultadoDadosApiDto {

    private Object cep;
    private Object cnpj;
    private Object fipe;
    private List<?> feriados;
    private Object cambio;
    private Map<String, String> errosParciais;
}
