package com.dados.br.hub.convert;

import com.dados.br.hub.dto.ResultadoDadosApiDto;
import com.dados.br.hub.dto.ResponseCompletoDto;

public class ResponseCompletoConvert {

    public ResponseCompletoDto convertResultadoDadosApiDtoToResponseCompletoDto(ResultadoDadosApiDto resultado) {
        ResponseCompletoDto response = new ResponseCompletoDto();
        response.setCep(resultado.getCep().toString());
        response.setCnpj(resultado.getCnpj().toString());
        response.setErrosParciais(resultado.getErrosParciais());
        return response;
    }
}
