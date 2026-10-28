package br.com.fiap.ecovolt_api.dto;

public class DetalhesOdsDto {

    private Long id;
    private Integer numero;
    private String nome;
    private String descricao;
    private String categoria;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
