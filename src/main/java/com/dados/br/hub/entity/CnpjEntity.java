package com.dados.br.hub.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Serdeable
@Table(name = "cnpj")
public class CnpjEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uf;
    private String cep;
    private String cnpj;
    private String pais;
    private String email;
    private String porte;
    private String bairro;
    private String numero;
    private String dddFax;
    private String municipio;
    private String logradouro;
    private Long cnaeFiscal;
    private String codigoPais;
    private String complemento;
    private Integer codigoPorte;
    private String razaoSocial;
    private String nomeFantasia;
    private Double capitalSocial;
    private String dddTelefone1;
    private String dddTelefone2;
    private Boolean opcaoPeloMei;
    private String descricaoPorte;
    private Integer codigoMunicipio;
    private String naturezaJuridica;
    private String situacaoEspecial;
    private Boolean opcaoPeloSimples;
    private Integer situacaoCadastral;
    private String dataOpcaoPeloMei;
    private String dataExclusaoDoMei;
    private String cnaeFiscalDescricao;
    private Integer codigoMunicipioIbge;
    private String dataInicioAtividade;
    private String dataSituacaoEspecial;
    private String dataOpcaoPeloSimples;
    private String dataSituacaoCadastral;
    private String nomeCidadeNoExterior;
    private Integer codigoNaturezaJuridica;
    private String dataExclusaoDoSimples;
    private Integer motivoSituacaoCadastral;
    private String enteFederativoResponsavel;
    private Integer identificadorMatrizFilial;
    private Integer qualificacaoDoResponsavel;
    private String descricaoSituacaoCadastral;
    private String descricaoTipoDeLogradouro;
    private String descricaoMotivoSituacaoCadastral;
    private String descricaoIdentificadorMatrizFilial;
}


