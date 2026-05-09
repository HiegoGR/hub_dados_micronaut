package com.dados.br.hub.controller;

import com.dados.br.hub.dto.CnpjResponseDto;
import com.dados.br.hub.service.client.CnpjService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

@Controller("/cnpj")
public class CnpjController {

    private final CnpjService cnpjService;

    public CnpjController(CnpjService cnpjService) {
        this.cnpjService = cnpjService;
    }

    @Get("/{cnpj}")
    public HttpResponse<CnpjResponseDto> buscarCpnj(@PathVariable String cnpj) {
        return HttpResponse.ok(cnpjService.buscarCnpj(cnpj));
    }
}
