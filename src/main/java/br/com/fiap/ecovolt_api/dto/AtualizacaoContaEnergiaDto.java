package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class AtualizacaoContaEnergiaDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    private Long idRecompensa;

    @NotBlank(message = "O nome do titular é obrigatório")
    private String nomeTitular;

    @NotBlank(message = "A concessionária é obrigatória")
    private String concessionaria;

    private String numeroInstalacao;
    private String mesReferencia;

    @PositiveOrZero(message = "O valor da conta deve ser zero ou positivo")
    private Double valorConta;

    @PositiveOrZero(message = "O valor do subsídio deve ser zero ou positivo")
    private Double valorSubsidio;

    private String arquivoComprovante;
    private String statusPagamento;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdRecompensa() { return idRecompensa; }
    public void setIdRecompensa(Long idRecompensa) { this.idRecompensa = idRecompensa; }
    public String getNomeTitular() { return nomeTitular; }
    public void setNomeTitular(String nomeTitular) { this.nomeTitular = nomeTitular; }
    public String getConcessionaria() { return concessionaria; }
    public void setConcessionaria(String concessionaria) { this.concessionaria = concessionaria; }
    public String getNumeroInstalacao() { return numeroInstalacao; }
    public void setNumeroInstalacao(String numeroInstalacao) { this.numeroInstalacao = numeroInstalacao; }
    public String getMesReferencia() { return mesReferencia; }
    public void setMesReferencia(String mesReferencia) { this.mesReferencia = mesReferencia; }
    public Double getValorConta() { return valorConta; }
    public void setValorConta(Double valorConta) { this.valorConta = valorConta; }
    public Double getValorSubsidio() { return valorSubsidio; }
    public void setValorSubsidio(Double valorSubsidio) { this.valorSubsidio = valorSubsidio; }
    public String getArquivoComprovante() { return arquivoComprovante; }
    public void setArquivoComprovante(String arquivoComprovante) { this.arquivoComprovante = arquivoComprovante; }
    public String getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }
}
