package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDate;

public class DetalhesRankingDto {

    private Long id;
    private Long idUsuario;
    private Long idTier;
    private String periodoReferencia;
    private Integer pontuacaoTotalPeriodo;
    private Integer posicao;
    private String status;
    private LocalDate dataAtualizacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdTier() { return idTier; }
    public void setIdTier(Long idTier) { this.idTier = idTier; }
    public String getPeriodoReferencia() { return periodoReferencia; }
    public void setPeriodoReferencia(String periodoReferencia) { this.periodoReferencia = periodoReferencia; }
    public Integer getPontuacaoTotalPeriodo() { return pontuacaoTotalPeriodo; }
    public void setPontuacaoTotalPeriodo(Integer pontuacaoTotalPeriodo) { this.pontuacaoTotalPeriodo = pontuacaoTotalPeriodo; }
    public Integer getPosicao() { return posicao; }
    public void setPosicao(Integer posicao) { this.posicao = posicao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDate dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
