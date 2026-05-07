package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Serdeable
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class CotacaoDto {

    private Integer paridade_compra;
    private Integer paridade_venda;
    private BigDecimal cotacao_compra;
    private BigDecimal cotacao_venda;
    private String data_hora_cotacao;
    private String tipo_boletim;
    private String moeda;
    private String data;
}
