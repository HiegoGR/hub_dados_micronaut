package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.CambioClient;
import com.dados.br.hub.dto.CambioResponseDto;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.util.Objects;

@Singleton
public class CambioService {

    private final CambioClient cambioClient;

    public CambioService(CambioClient cambioClient) {
        this.cambioClient = cambioClient;
    }

    public CambioResponseDto buscarValorCambial(String moeda, String data) {
        moeda = validarDados(moeda,"USD");
        data = validarDados(data,LocalDate.now().toString());

        return cambioClient.buscarCambio(moeda, data);
    }

    private String validarDados(String valor, String defaultValor) {
        return (Objects.isNull(valor) || valor.isBlank()) ? defaultValor : valor;
    }

}
