package com.dados.br.hub.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Serdeable
@NoArgsConstructor
@Introspected
public class CnpjResponseDto {

    private String uf;
    private String cep;
    private List<Qsa> qsa;
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
    private List<CnaesSecundarios> cnaesSecundarios;
    private String naturezaJuridica;
    private List<RegimeTributario> regimeTributario;
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

    @Data
    @NoArgsConstructor
    @Introspected
    public static class Qsa {
        private String pais;
        private String nomeSocio;
        private String codigoPais;
        private String faixaEtaria;
        private String cnpjCpfDoSocio;
        private String qualificacaoSocio;
        private Integer codigoFaixaEtaria;
        private String dataEntradaSociedade;
        private Integer identificadorDeSocio;
        private String cpfRepresentanteLegal;
        private String nomeRepresentanteLegal;
        private Integer codigoQualificacaoSocio;
        private String qualificacaoRepresentanteLegal;
        private Integer codigoQualificacaoRepresentanteLegal;
    }

    @Data
    @NoArgsConstructor
    @Introspected
    public static class CnaesSecundarios {
        private Long codigo;
        private String descricao;
    }

    @Data
    @NoArgsConstructor
    @Introspected
    public static class RegimeTributario {
        private Integer ano;
        private String cnpjDaScp;
        private String formaDeTributacao;
        private Integer quantidadeDeEscrituracoes;
    }
}


