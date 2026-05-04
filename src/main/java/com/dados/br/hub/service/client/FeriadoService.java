package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.FeriadoClient;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.util.Objects;

@Singleton
public class FeriadoService {

    private final FeriadoClient feriadoClient;

    public FeriadoService(FeriadoClient feriadoClient) {
        this.feriadoClient = feriadoClient;
    }

    public Object buscarFeriados(Integer ano) {
        ano = validarAno(ano);
        return feriadoClient.buscarFeriados(ano);
    }

    private Integer validarAno(Integer valor) {
        return Objects.isNull(valor) ? LocalDate.now().getYear() : valor;
    }
}
