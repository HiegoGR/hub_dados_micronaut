package com.dados.br.hub.clients;

import com.dados.br.hub.dto.CambioResponseDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("${brasilapi.url}")
public interface CambioClient {

    @Get("/cambio/v1/cotacao/{moeda}/{data}")
    CambioResponseDto buscarCambio(String moeda, String data);
}
