package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.CambioClient;
import com.dados.br.hub.dto.ContacaoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CambioServiceTest {

    @Mock
    private CambioClient cambioClient;

    @InjectMocks
    private CambioService cambioService;

    private ContacaoResponseDto response;

    @BeforeEach
    void setup() {
        response = new ContacaoResponseDto();
    }

    @Test
    @DisplayName("Deve buscar cotação com moeda e data informadas")
    void deveBuscarCotacaoComMoedaEDataInformadas() {
        String moeda = "EUR";
        String data = "2026-05-01";

        when(cambioClient.buscarCotacao(moeda, data)).thenReturn(response);

        ContacaoResponseDto resultado = cambioService.cotacao(moeda, data);

        assertSame(response, resultado);
        verify(cambioClient).buscarCotacao(moeda, data);
    }

    @Test
    @DisplayName("Deve usar moeda padrão quando vier nula")
    void deveUsarUsdQuandoMoedaForNula() {
        String data = "2026-05-01";

        when(cambioClient.buscarCotacao("USD", data)).thenReturn(response);

        ContacaoResponseDto resultado = cambioService.cotacao(null, data);

        assertSame(response, resultado);
        verify(cambioClient).buscarCotacao("USD", data);
    }

    @Test
    @DisplayName("Deve usar moeda padrão quando vier vazia")
    void deveUsarUsdQuandoMoedaForVazia() {
        String data = "2026-05-01";

        when(cambioClient.buscarCotacao("USD", data)).thenReturn(response);

        ContacaoResponseDto resultado = cambioService.cotacao(" ", data);

        assertSame(response, resultado);
        verify(cambioClient).buscarCotacao("USD", data);
    }

    @Test
    @DisplayName("Deve usar data atual como padrão quando for null")
    void deveUsarDataAtualMenosUmQuandoDataForNula() {
        String dataEsperada = LocalDate.now().minusDays(1).toString();

        when(cambioClient.buscarCotacao("USD", dataEsperada)).thenReturn(response);

        ContacaoResponseDto resultado = cambioService.cotacao(null, null);

        assertSame(response, resultado);
        verify(cambioClient).buscarCotacao("USD", dataEsperada);
    }

    @Test
    @DisplayName("Caso seja data atual o mesmo deve pegar D-1")
    void deveUsarDataAtualMenosUmQuandoDataForHoje() {
        String dataHoje = LocalDate.now().toString();
        String dataEsperada = LocalDate.now().minusDays(1).toString();

        when(cambioClient.buscarCotacao("USD", dataEsperada)).thenReturn(response);

        ContacaoResponseDto resultado = cambioService.cotacao(null, dataHoje);

        assertSame(response, resultado);
        verify(cambioClient).buscarCotacao("USD", dataEsperada);
    }

    @Test
    @DisplayName("Não deve executar chamadas adicionais ao client após buscar cotação")
    void naoDeveChamarClientMaisDeUmaVez() {
        String data = "2026-05-01";

        when(cambioClient.buscarCotacao("BRL", data)).thenReturn(response);

        cambioService.cotacao("BRL", data);

        verify(cambioClient, times(1)).buscarCotacao("BRL", data);
        verifyNoMoreInteractions(cambioClient);
    }
}
