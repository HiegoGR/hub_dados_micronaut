package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.CambioClient;
import com.dados.br.hub.dto.ContacaoResponseDto;
import com.dados.br.hub.dto.CotacaoDto;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Singleton
public class CambioService {

    private final CambioClient cambioClient;

    public CambioService(CambioClient cambioClient) {
        this.cambioClient = cambioClient;
    }

    public ContacaoResponseDto cotacao(String moeda, String data) {
        moeda = validarDados(moeda,"USD");
        data = validarDados(data,LocalDate.now().toString());
        data = validarDaata(data);

        return cambioClient.buscarCotacao(moeda, data);
    }

    private String validarDados(String valor, String defaultValor) {
        return (Objects.isNull(valor) || valor.isBlank()) ? defaultValor : valor;
    }

    private String validarDaata(String data) {
        return data.equals(LocalDate.now().toString()) ? LocalDate.now().minusDays(1).toString() : data;
    }

}
