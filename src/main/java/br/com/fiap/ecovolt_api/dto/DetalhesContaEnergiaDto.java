package br.com.fiap.ecovolt_api.dto;

public class DetalhesContaEnergiaDto {

    private Long id;
    private Long idUsuario;
    private Long idRecompensa;
    private String nomeTitular;
    private String concessionaria;
    private String numeroInstalacao;
    private String mesReferencia;
    private Double valorConta;
    private Double valorSubsidio;
    private String arquivoComprovante;
    private String statusPagamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
