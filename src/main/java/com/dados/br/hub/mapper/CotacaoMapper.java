package com.dados.br.hub.mapper;

import com.dados.br.hub.dto.CambioResponseDto;
import com.dados.br.hub.dto.ContacaoResponseDto;
import com.dados.br.hub.dto.CotacaoDto;
import com.dados.br.hub.entity.CambioEntity;
import com.dados.br.hub.entity.CotacaoEntity;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Singleton
public class CotacaoMapper {

    public CotacaoEntity toEntity(CotacaoDto dto){
        CotacaoEntity entity = new CotacaoEntity();

        entity.setParidade_compra(dto.getParidade_compra());
        entity.setParidade_venda(dto.getParidade_venda());
        entity.setCotacao_compra(dto.getCotacao_compra() == null ? BigDecimal.ZERO : dto.getCotacao_compra());
        entity.setCotacao_venda(dto.getCotacao_venda() == null ? BigDecimal.ZERO : dto.getCotacao_venda());
        entity.setData_hora_cotacao(dto.getData_hora_cotacao());
        entity.setTipo_boletim(dto.getTipo_boletim());
        entity.setMoeda(dto.getMoeda());
        entity.setData(dto.getData());

        return entity;
    }

    public List<CotacaoEntity> toEntityList(List<?> dtos) {
        return dtos.stream()
                .map(item -> toEntity((CotacaoDto) item))
                .toList();
    }


    public List<CotacaoEntity> toEntityList2(ContacaoResponseDto dto) {

        if (dto == null || dto.getCotacoes() == null) {
            return Collections.emptyList();
        }

        return dto.getCotacoes()
                .stream()
                .map(this::toEntity)
                .toList();
    }
}
