package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class AtualizacaoMissaoDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;
    private String dificuldade;

    @PositiveOrZero(message = "A pontuação recompensa deve ser zero ou positiva")
    private Integer pontuacaoRecompensa;

    @PositiveOrZero(message = "O XP recompensa deve ser zero ou positivo")
    private Integer xpRecompensa;

    @PositiveOrZero(message = "A quantidade semanal deve ser zero ou positiva")
    private Integer quantidadeSemanal;

    private String tipo;
    private Boolean status;

    @NotNull(message = "O ID do tier é obrigatório")
    @Positive(message = "O ID do tier deve ser positivo")
    private Long idTier;

    @NotNull(message = "O ID do nível é obrigatório")
    @Positive(message = "O ID do nível deve ser positivo")
    private Long idNivel;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDificuldade() { return dificuldade; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }
    public Integer getPontuacaoRecompensa() { return pontuacaoRecompensa; }
    public void setPontuacaoRecompensa(Integer pontuacaoRecompensa) { this.pontuacaoRecompensa = pontuacaoRecompensa; }
    public Integer getXpRecompensa() { return xpRecompensa; }
    public void setXpRecompensa(Integer xpRecompensa) { this.xpRecompensa = xpRecompensa; }
    public Integer getQuantidadeSemanal() { return quantidadeSemanal; }
    public void setQuantidadeSemanal(Integer quantidadeSemanal) { this.quantidadeSemanal = quantidadeSemanal; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public Long getIdTier() { return idTier; }
    public void setIdTier(Long idTier) { this.idTier = idTier; }
    public Long getIdNivel() { return idNivel; }
    public void setIdNivel(Long idNivel) { this.idNivel = idNivel; }
}
