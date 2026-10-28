package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class AtualizacaoNivelDto {

    @NotNull(message = "O número do nível é obrigatório")
    @Positive(message = "O número deve ser positivo")
    private Integer numero;

    @NotNull(message = "O XP mínimo é obrigatório")
    @PositiveOrZero(message = "O XP mínimo deve ser zero ou positivo")
    private Integer xpMinimo;

    @NotNull(message = "O XP máximo é obrigatório")
    @Positive(message = "O XP máximo deve ser positivo")
    private Integer xpMaximo;

    private String descricao;
    private String bordaPerfil;
    private String status;

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getXpMinimo() { return xpMinimo; }
    public void setXpMinimo(Integer xpMinimo) { this.xpMinimo = xpMinimo; }
    public Integer getXpMaximo() { return xpMaximo; }
    public void setXpMaximo(Integer xpMaximo) { this.xpMaximo = xpMaximo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getBordaPerfil() { return bordaPerfil; }
    public void setBordaPerfil(String bordaPerfil) { this.bordaPerfil = bordaPerfil; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
