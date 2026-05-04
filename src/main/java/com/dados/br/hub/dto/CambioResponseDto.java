package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Introspected
public class CambioResponseDto {

    private String simbolo;
    private String nome;
    private String tipoMoeda;

}
