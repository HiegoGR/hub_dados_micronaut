package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Serdeable
@NoArgsConstructor
@Introspected
public class ResponseCompletoDto {

    //Dados do cnpj + endereco
    private String cnpj;
    private String uf;
    private String cep;
    private String pais;
    private String porte;
    private String bairro;
    private String municipio;
    private String logradouro;

    //Feriados referente o local da empresa
    private FeriadoResponseDto feriados;

    // cambio da moeda
    private CambioResponseDto cambio;

    private Map<String, String> errosParciais;
}
