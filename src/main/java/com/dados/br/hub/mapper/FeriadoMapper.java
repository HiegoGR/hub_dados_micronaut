package com.dados.br.hub.mapper;

import com.dados.br.hub.dto.FeriadoResponseDto;
import com.dados.br.hub.entity.FeriadoEntity;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class FeriadoMapper {

    public FeriadoEntity toEntity(FeriadoResponseDto dto){
        FeriadoEntity entity = new FeriadoEntity();

        entity.setDate(dto.getDate());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setWeekday(dto.getWeekday());

        return entity;
    }

    public List<FeriadoEntity> toEntityList(List<?> dtos) {
        return dtos.stream()
                .map(item -> toEntity((FeriadoResponseDto) item))
                .toList();
    }

    public FeriadoResponseDto toDto(FeriadoEntity entity){
        FeriadoResponseDto dto = new FeriadoResponseDto();

        dto.setDate(entity.getDate());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setWeekday(entity.getWeekday());

        return dto;
    }
}
