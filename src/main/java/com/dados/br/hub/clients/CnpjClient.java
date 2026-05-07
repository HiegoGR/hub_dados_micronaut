package com.dados.br.hub.clients;

import com.dados.br.hub.dto.CnpjResponseDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;

@Client("${brasilapi.url}")
@Header(name = "User-Agent", value = "Mozilla/5.0")
@Header(name = "Accept", value = "application/json")
public interface CnpjClient {

    @Get("/cnpj/v1/{cnpj}")
    CnpjResponseDto buscarCnpj(@PathVariable String cnpj);
}
