package com.dados.br.hub.clients;

import com.dados.br.hub.dto.FeriadoResponseDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client(id = "brasilapi")
public interface FeriadoClient {

    @Get("/feriados/v1/{ano}")
    List<FeriadoResponseDto> buscarFeriados(Integer ano);
}
