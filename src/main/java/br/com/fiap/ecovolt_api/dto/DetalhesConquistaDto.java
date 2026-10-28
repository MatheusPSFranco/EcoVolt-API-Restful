package br.com.fiap.ecovolt_api.dto;

public class DetalhesConquistaDto {

    private Long id;
    private Long idNivel;
    private String nome;
    private String descricao;
    private String tipo;
    private String criterioDesbloqueio;
    private Integer quantidadeNecessaria;
    private String medalha;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
