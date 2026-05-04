package com.dados.br.hub.clients;

import com.dados.br.hub.dto.ViaCepResponseDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client(id = "viacep")
public interface ViacepClient {

    @Get("/{cep}/json/")
    ViaCepResponseDto buscarCep(String cep);
}
