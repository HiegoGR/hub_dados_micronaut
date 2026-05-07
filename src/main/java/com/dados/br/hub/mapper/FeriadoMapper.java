package com.dados.br.hub.mapper;

import com.dados.br.hub.dto.ViaCepResponseDto;
import com.dados.br.hub.entity.ViaCepEntity;

public class ViaCepMapper {

    public ViaCepEntity toEntity(ViaCepResponseDto dto){
        ViaCepEntity entity = new ViaCepEntity();

        entity.setCep(dto.getCep());
        entity.setLogradouro(dto.getLogradouro());
        entity.setComplemento(dto.getComplemento());
        entity.setUnidade(dto.getUnidade());
        entity.setBairro(dto.getBairro());
        entity.setLocalidade(dto.getLocalidade());
        entity.setUf(dto.getUf());
        entity.setEstado(dto.getEstado());
        entity.setRegiao(dto.getRegiao());
        entity.setIbge(dto.getIbge());
        entity.setGia(dto.getGia());
        entity.setDdd(dto.getDdd());
        entity.setSiafi(dto.getSiafi());

        return entity;
    }

    public ViaCepResponseDto toDto(ViaCepEntity entity){
        ViaCepResponseDto dto = new ViaCepResponseDto();

        dto.setCep(entity.getCep());
        dto.setLogradouro(entity.getLogradouro());
        dto.setComplemento(entity.getComplemento());
        dto.setUnidade(entity.getUnidade());
        dto.setBairro(entity.getBairro());
        dto.setLocalidade(entity.getLocalidade());
        dto.setUf(entity.getUf());
        dto.setEstado(entity.getEstado());
        dto.setRegiao(entity.getRegiao());
        dto.setIbge(entity.getIbge());
        dto.setGia(entity.getGia());
        dto.setDdd(entity.getDdd());
        dto.setSiafi(entity.getSiafi());

        return dto;
    }
}
