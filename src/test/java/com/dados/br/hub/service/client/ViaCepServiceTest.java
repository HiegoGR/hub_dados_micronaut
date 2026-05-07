package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.ViacepClient;
import com.dados.br.hub.dto.ViaCepResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ViaCepServiceTest {

    @Mock
    private ViacepClient viaCepClient;

    @InjectMocks
    private ViaCepService viaCepService;

    private ViaCepResponseDto response;

    @BeforeEach
    void setup() {
        response = new ViaCepResponseDto();
    }

    @Test
    @DisplayName("Deve buscar CEP corretamente")
    void deveBuscarCepCorretamente() {
        String cep = "01001000";

        when(viaCepClient.buscarCep(cep)).thenReturn(response);

        ViaCepResponseDto resultado = viaCepService.buscar(cep);

        assertSame(response, resultado);
        verify(viaCepClient).buscarCep(cep);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CEP for vazio")
    void deveLancarExcecaoQuandoCepForVazio() {
        String cep = "";

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> viaCepService.buscar(cep)
        );

        assertEquals("Campo Cep esta vazio", exception.getMessage());
        verifyNoInteractions(viaCepClient);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CEP for inválido")
    void deveLancarExcecaoQuandoCepForInvalido() {
        String cep = "123";

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> viaCepService.buscar(cep)
        );

        assertEquals("Cep invalido. Deve conter 8 digitos numeros. Ex: 01001000", exception.getMessage());
        verifyNoInteractions(viaCepClient);
    }

    @Test
    @DisplayName("Deve remover caracteres especiais do CEP antes de buscar")
    void deveRemoverCaracteresEspeciaisDoCepAntesDeBuscar() {
        String cepComMascara = "01001-000";
        String cepSemMascara = "01001000";

        when(viaCepClient.buscarCep(cepSemMascara)).thenReturn(response);

        ViaCepResponseDto resultado = viaCepService.buscar(cepComMascara);

        assertSame(response, resultado);
        verify(viaCepClient).buscarCep(cepSemMascara);
    }

    @Test
    @DisplayName("Verificar se chamou o client uma vez")
    void deveChamarClientApenasUmaVez() {
        String cep = "01001000";

        when(viaCepClient.buscarCep(cep)).thenReturn(response);

        viaCepService.buscar(cep);

        verify(viaCepClient, times(1)).buscarCep(cep);
        verifyNoMoreInteractions(viaCepClient);
    }
}

