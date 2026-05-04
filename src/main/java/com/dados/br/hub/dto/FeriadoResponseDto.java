package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Serdeable
@NoArgsConstructor
@Introspected
public class FeriadoResponseDto {

    private String date;
    private String name;
    private String type;
    private String weekday;
}
