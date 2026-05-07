package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.FeriadoClient;
import com.dados.br.hub.dto.FeriadoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeriadoServiceTest {

    @Mock
    private FeriadoClient feriadoClient;

    @InjectMocks
    private FeriadoService feriadoService;

    private List<FeriadoResponseDto> response;

    @BeforeEach
    void setup() {
        response = new ArrayList<>();
    }

    @Test
    @DisplayName("Deve buscar feriados por ano corretamente")
    void deveBuscarFeriadosPorAnoCorretamente() {
        Integer ano = 2024;

        when(feriadoClient.buscarFeriados(ano)).thenReturn(response);

        Object resultado = feriadoService.buscarFeriados(ano);

        assertSame(response, resultado);
        verify(feriadoClient).buscarFeriados(ano);
    }

    @Test
    @DisplayName("Deve usar ano atual como padrão quando ano for nulo")
    void deveUsarAnoAtualQuandoAnoForNulo() {
        Integer anoAtual = LocalDate.now().getYear();

        when(feriadoClient.buscarFeriados(anoAtual)).thenReturn(response);

        Object resultado = feriadoService.buscarFeriados(null);

        assertSame(response, resultado);
        verify(feriadoClient).buscarFeriados(anoAtual);
    }

    @Test
    @DisplayName("Deve chamar client apenas uma vez ao buscar feriados")
    void naoDeveChamarClientMaisDeUmaVez() {
        Integer ano = 2025;

        when(feriadoClient.buscarFeriados(ano)).thenReturn(response);

        feriadoService.buscarFeriados(ano);

        verify(feriadoClient, times(1)).buscarFeriados(ano);
        verifyNoMoreInteractions(feriadoClient);
    }
}
    

