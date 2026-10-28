package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AtualizacaoConquistaDto {

    @NotNull(message = "O ID do nível é obrigatório")
    @Positive(message = "O ID do nível deve ser positivo")
    private Long idNivel;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;
    private String tipo;
    private String criterioDesbloqueio;
    private Integer quantidadeNecessaria;
    private String medalha;
    private String status;

    public Long getIdNivel() { return idNivel; }
    public void setIdNivel(Long idNivel) { this.idNivel = idNivel; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getCriterioDesbloqueio() { return criterioDesbloqueio; }
    public void setCriterioDesbloqueio(String criterioDesbloqueio) { this.criterioDesbloqueio = criterioDesbloqueio; }
    public Integer getQuantidadeNecessaria() { return quantidadeNecessaria; }
    public void setQuantidadeNecessaria(Integer quantidadeNecessaria) { this.quantidadeNecessaria = quantidadeNecessaria; }
    public String getMedalha() { return medalha; }
    public void setMedalha(String medalha) { this.medalha = medalha; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
