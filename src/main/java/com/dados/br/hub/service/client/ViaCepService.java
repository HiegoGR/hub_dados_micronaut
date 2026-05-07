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
        cep = cep.replaceAll("[^0-9]", "");
        validarCep(cep);
        return viaCepClient.buscarCep(cep);
    }

    public void validarCep(String cep) {
        if (cep == null || cep.isEmpty()) {
            throw new RuntimeException("Campo Cep esta vazio");
        }

        if (cep.length() != 8 || !cep.matches("\\d+")) {
            throw new RuntimeException("Cep invalido. Deve conter 8 digitos numeros. Ex: 01001000");
        }
    }
}
