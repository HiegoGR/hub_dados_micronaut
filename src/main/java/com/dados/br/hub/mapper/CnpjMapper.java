package com.dados.br.hub.mapper;

import com.dados.br.hub.dto.CnpjResponseDto;
import com.dados.br.hub.dto.ViaCepResponseDto;
import com.dados.br.hub.entity.CnpjEntity;
import com.dados.br.hub.entity.ViaCepEntity;
import jakarta.inject.Singleton;

@Singleton
public class CnpjMapper {

    public CnpjEntity toEntity(CnpjResponseDto dto){
        CnpjEntity entity = new CnpjEntity();

        entity.setUf(dto.getUf());
        entity.setCep(dto.getCep());
        entity.setCnpj(dto.getCnpj());
        entity.setPais(dto.getPais());
        entity.setEmail(dto.getEmail());
        entity.setPorte(dto.getPorte());
        entity.setBairro(dto.getBairro());
        entity.setNumero(dto.getNumero());
        entity.setDddFax(dto.getDddFax());
        entity.setMunicipio(dto.getMunicipio());
        entity.setLogradouro(dto.getLogradouro());
        entity.setCnaeFiscal(dto.getCnaeFiscal());
        entity.setCodigoPais(dto.getCodigoPais());
        entity.setComplemento(dto.getComplemento());
        entity.setCodigoPorte(dto.getCodigoPorte());
        entity.setRazaoSocial(dto.getRazaoSocial());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setCapitalSocial(dto.getCapitalSocial());
        entity.setDddTelefone1(dto.getDddTelefone1());
        entity.setDddTelefone2(dto.getDddTelefone2());
        entity.setOpcaoPeloMei(dto.getOpcaoPeloMei());
        entity.setDescricaoPorte(dto.getDescricaoPorte());
        entity.setCodigoMunicipio(dto.getCodigoMunicipio());
        entity.setNaturezaJuridica(dto.getNaturezaJuridica());
        entity.setSituacaoEspecial(dto.getSituacaoEspecial());
        entity.setOpcaoPeloSimples(dto.getOpcaoPeloSimples());
        entity.setSituacaoCadastral(dto.getSituacaoCadastral());
        entity.setDataOpcaoPeloMei(dto.getDataOpcaoPeloMei());
        entity.setDataExclusaoDoMei(dto.getDataExclusaoDoMei());
        entity.setCnaeFiscalDescricao(dto.getCnaeFiscalDescricao());
        entity.setCodigoMunicipioIbge(dto.getCodigoMunicipioIbge());
        entity.setDataInicioAtividade(dto.getDataInicioAtividade());
        entity.setDataSituacaoEspecial(dto.getDataSituacaoEspecial());
        entity.setDataOpcaoPeloSimples(dto.getDataOpcaoPeloSimples());
        entity.setDataSituacaoCadastral(dto.getDataSituacaoCadastral());
        entity.setNomeCidadeNoExterior(dto.getNomeCidadeNoExterior());
        entity.setCodigoNaturezaJuridica(dto.getCodigoNaturezaJuridica());
        entity.setDataExclusaoDoSimples(dto.getDataExclusaoDoSimples());
        entity.setMotivoSituacaoCadastral(dto.getMotivoSituacaoCadastral());
        entity.setEnteFederativoResponsavel(dto.getEnteFederativoResponsavel());
        entity.setIdentificadorMatrizFilial(dto.getIdentificadorMatrizFilial());
        entity.setQualificacaoDoResponsavel(dto.getQualificacaoDoResponsavel());
        entity.setDescricaoSituacaoCadastral(dto.getDescricaoSituacaoCadastral());
        entity.setDescricaoTipoDeLogradouro(dto.getDescricaoTipoDeLogradouro());
        entity.setDescricaoMotivoSituacaoCadastral(dto.getDescricaoMotivoSituacaoCadastral());
        entity.setDescricaoIdentificadorMatrizFilial(dto.getDescricaoIdentificadorMatrizFilial());

        return entity;
    }

}
