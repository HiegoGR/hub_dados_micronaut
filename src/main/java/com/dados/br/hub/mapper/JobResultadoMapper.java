package com.dados.br.hub.mapper;

import com.dados.br.hub.dto.*;
import com.dados.br.hub.entity.*;
import com.dados.br.hub.enums.JobStatus;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Singleton
public class JobResultadoMapper {

    private final ObjectMapper objectMapper;
    private final ViaCepMapper viaCepMapper;
    private final CnpjMapper cnpjMapper;
    private final CambioMapper cambioMapper;
    private final FeriadoMapper feriadoMapper;

    public JobResultadoMapper(
            ObjectMapper objectMapper,
            ViaCepMapper viaCepMapper,
            CnpjMapper cnpjMapper,
            CambioMapper cambioMapper,
            FeriadoMapper feriadoMapper) {
        this.objectMapper = objectMapper;
        this.viaCepMapper = viaCepMapper;
        this.cnpjMapper = cnpjMapper;
        this.cambioMapper = cambioMapper;
        this.feriadoMapper = feriadoMapper;
    }

    public JobEntity toEntity(String jobId, ResultadoDadosApiDto dto) {
        JobEntity entity = new JobEntity();

        entity.setJobId(jobId);
        entity.setStatus(JobStatus.FINALIZADO);

        entity.setViaCep(dto.getCep() == null ? null : viaCepMapper.toEntity((ViaCepResponseDto) dto.getCep()));
        entity.setCnpj(dto.getCnpj() == null ? null : cnpjMapper.toEntity((CnpjResponseDto) dto.getCnpj()));
        entity.setCambio(dto.getCambio() == null ? null : cambioMapper.toEntity((CambioResponseDto) dto.getCambio()));
        entity.setFeriados(dto.getFeriados() == null ? Collections.emptyList() : feriadoMapper.toEntityList((List<?>) dto.getFeriados()));
        entity.setErrosParciais(converterErros(dto.getErrosParciais()));

        return entity;
    }

    public String converterErros(Map<String, String> erros) {
        try {
            if (erros == null || erros.isEmpty()) {
                return null;
            }
            return objectMapper.writeValueAsString(erros);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter erros parciais para JSON", e);
        }
    }
}
