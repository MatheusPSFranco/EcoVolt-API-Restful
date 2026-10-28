package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class AtualizacaoRecompensaDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    @NotNull(message = "O ID do tier é obrigatório")
    @Positive(message = "O ID do tier deve ser positivo")
    private Long idTier;

    private Long idRanking;
    private String tipo;
    private String descricao;

    @PositiveOrZero(message = "O valor deve ser zero ou positivo")
    private Double valor;

    @PositiveOrZero(message = "O valor máximo deve ser zero ou positivo")
    private Double valorMaximo;

    private String status;
    private String periodoReferencia;

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
    public String getPeriodoReferencia() { return periodoReferencia; }
    public void setPeriodoReferencia(String periodoReferencia) { this.periodoReferencia = periodoReferencia; }
}
