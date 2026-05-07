package com.dados.br.hub.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Serdeable
@Table(name = "cotacao")
public class CotacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer paridade_compra;
    private Integer paridade_venda;
    private BigDecimal cotacao_compra;
    private BigDecimal cotacao_venda;
    private String data_hora_cotacao;
    private String tipo_boletim;
    private String moeda;
    private String data;
}
