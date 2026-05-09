package com.dados.br.hub.clients;

import com.dados.br.hub.dto.CambioResponseDto;
import com.dados.br.hub.dto.ContacaoResponseDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;

@Client("${brasilapi.url}")
public interface CambioClient {

    @Get("/cambio/v1/moedas")
    CambioResponseDto buscarCambio();

    @Get("/cambio/v1/cotacao/{moeda}/{data}")
    ContacaoResponseDto buscarCotacao(@PathVariable String moeda, @PathVariable String data);
}
