package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class AtualizacaoRankingDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    @NotNull(message = "O ID do tier é obrigatório")
    @Positive(message = "O ID do tier deve ser positivo")
    private Long idTier;

    private String periodoReferencia;

    @PositiveOrZero(message = "A pontuação total deve ser zero ou positiva")
    private Integer pontuacaoTotalPeriodo;

    @Positive(message = "A posição deve ser positiva")
    private Integer posicao;

    private String status;

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
}
