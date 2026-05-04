package com.dados.br.hub.clients;

import com.dados.br.hub.dto.CnpjResponseDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client(id = "brasilapi")
public interface CnpjClient {

    @Get("/cnpj/v1/{cnpj}")
    CnpjResponseDto buscarCnpj(String cnpj);
}
