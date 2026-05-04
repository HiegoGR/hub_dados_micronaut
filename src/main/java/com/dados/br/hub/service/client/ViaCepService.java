package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.ViacepClient;
import com.dados.br.hub.dto.ViaCepResponseDto;
import jakarta.inject.Singleton;

@Singleton
public class ViaCepService {

    private final ViacepClient viaCepClient;

    public ViaCepService(ViacepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public ViaCepResponseDto buscar(String cep) {
        return viaCepClient.buscarCep(cep);
    }
}
