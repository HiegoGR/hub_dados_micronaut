package com.dados.br.hub.service.client;

import com.dados.br.hub.clients.CnpjClient;
import com.dados.br.hub.dto.CnpjResponseDto;
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
public class CnpjServiceTest {

    @Mock
    private CnpjClient cnpjClient;

    @InjectMocks
    private CnpjService cnpjService;

    private CnpjResponseDto response;

    @BeforeEach
    void setup() {
        response = new CnpjResponseDto();
    }

    @Test
    @DisplayName("Deve buscar CNPJ corretamente")
    void deveBuscarCnpjCorretamente() {
        String cnpj = "12345678901234";

        when(cnpjClient.buscarCnpj(cnpj)).thenReturn(response);

        CnpjResponseDto resultado = cnpjService.buscarCnpj(cnpj);

        assertSame(response, resultado);
        verify(cnpjClient).buscarCnpj(cnpj);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CNPJ for vazio")
    void deveLancarExcecaoQuandoCnpjForVazio() {
        String cnpj = "";

        Exception exception = assertThrows(
                                RuntimeException.class,
                                () -> cnpjService.buscarCnpj(cnpj)
        );

        assertEquals("Campo Cnpj esta vazio", exception.getMessage());
        verifyNoInteractions(cnpjClient);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CNPJ for inválido")
    void deveLancarExcecaoQuandoCnpjForInvalido() {
        String cnpj = "12345";

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> cnpjService.buscarCnpj(cnpj)
        );
        assertEquals("Cnpj invalido", exception.getMessage());
        verifyNoInteractions(cnpjClient);
    }

    @Test
    @DisplayName("Deve remover caracteres especiais do CNPJ antes de buscar")
    void deveRemoverCaracteresEspeciaisDoCnpjAntesDeBuscar() {
        String cnpjComMascara = "12.345.678/9012-34";
        String cnpjSemMascara = "12345678901234";

        when(cnpjClient.buscarCnpj(cnpjSemMascara)).thenReturn(response);

        CnpjResponseDto resultado = cnpjService.buscarCnpj(cnpjComMascara);

        assertSame(response, resultado);
        verify(cnpjClient).buscarCnpj(cnpjSemMascara);
    }

    @Test
    @DisplayName("Verificar se chamou o client uma vez")
    void deveChamarClientApenasUmaVez() {
        String cnpj = "12345678000199";

        when(cnpjClient.buscarCnpj(cnpj)).thenReturn(response);

        cnpjService.buscarCnpj(cnpj);

        verify(cnpjClient, times(1)).buscarCnpj(cnpj);
        verifyNoMoreInteractions(cnpjClient);
    }
}
    
    

