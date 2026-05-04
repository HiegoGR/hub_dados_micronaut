package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Introspected
public class FeriadoResponseDto {

    private String date;
    private String name;
    private String type;
    private String weekday;
}
