package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDate;

public class DetalhesRecompensaDto {

    private Long id;
    private Long idUsuario;
    private Long idTier;
    private Long idRanking;
    private String tipo;
    private String descricao;
    private Double valor;
    private Double valorMaximo;
    private String status;
    private LocalDate dataConcessao;
    private String periodoReferencia;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdTier() { return idTier; }
    public void setIdTier(Long idTier) { this.idTier = idTier; }
    public Long getIdRanking() { return idRanking; }
    public void setIdRanking(Long idRanking) { this.idRanking = idRanking; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public Double getValorMaximo() { return valorMaximo; }
    public void setValorMaximo(Double valorMaximo) { this.valorMaximo = valorMaximo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataConcessao() { return dataConcessao; }
    public void setDataConcessao(LocalDate dataConcessao) { this.dataConcessao = dataConcessao; }
    public String getPeriodoReferencia() { return periodoReferencia; }
    public void setPeriodoReferencia(String periodoReferencia) { this.periodoReferencia = periodoReferencia; }
}
