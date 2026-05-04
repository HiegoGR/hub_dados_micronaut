package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Serdeable
@NoArgsConstructor
@Introspected
public class CambioResponseDto {

    private String simbolo;
    private String nome;
    private String tipoMoeda;

}
