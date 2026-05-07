package com.dados.br.hub.mapper;

import com.dados.br.hub.dto.CambioResponseDto;
import com.dados.br.hub.dto.CotacaoDto;
import com.dados.br.hub.dto.FeriadoResponseDto;
import com.dados.br.hub.dto.ViaCepResponseDto;
import com.dados.br.hub.entity.CambioEntity;
import com.dados.br.hub.entity.CotacaoEntity;
import com.dados.br.hub.entity.FeriadoEntity;
import com.dados.br.hub.entity.ViaCepEntity;
import jakarta.inject.Singleton;

import java.util.List;

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
