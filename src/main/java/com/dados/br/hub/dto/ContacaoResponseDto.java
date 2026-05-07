package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Serdeable
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class ContacaoResponseDto {

    private List<CotacaoDto> cotacoes;
    private String moeda;
    private String data;
}
