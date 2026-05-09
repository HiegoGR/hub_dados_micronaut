package com.dados.br.hub.mapper;

import com.dados.br.hub.dto.CambioResponseDto;
import com.dados.br.hub.entity.CambioEntity;
import jakarta.inject.Singleton;

@Singleton
public class CambioMapper {

    public CambioEntity toEntity(CambioResponseDto dto){
        CambioEntity entity = new CambioEntity();

        entity.setNome(dto.getNome());
        entity.setSimbolo(dto.getSimbolo());
        entity.setTipoMoeda(dto.getTipoMoeda());

        return entity;
    }

    public CambioResponseDto toDto(CambioEntity dto){
        CambioResponseDto entity = new CambioResponseDto();

        dto.setNome(entity.getNome());
        dto.setSimbolo(entity.getSimbolo());
        dto.setTipoMoeda(entity.getTipoMoeda());

        return entity;
    }
}
