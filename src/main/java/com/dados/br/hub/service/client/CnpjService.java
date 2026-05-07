package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.CnpjClient;
import com.dados.br.hub.dto.CnpjResponseDto;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Singleton;

@ExecuteOn(TaskExecutors.BLOCKING)
@Singleton
public class CnpjService {

    private final CnpjClient cnpjClient;

    public CnpjService(CnpjClient cnpjClient) {
        this.cnpjClient = cnpjClient;
    }

    public CnpjResponseDto buscarCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^A-Za-z0-9]", "");
        validarCnpj(cnpj);
        return cnpjClient.buscarCnpj(cnpj);
    }

    public void validarCnpj(String cnpj) {
        if(cnpj.isEmpty()){
            throw new RuntimeException("Campo Cnpj esta vazio");
        }

        if(cnpj.length() != 14){
            throw new RuntimeException("Cnpj invalido");
        }

    }

}
