package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AtualizacaoOdsDto {

    @NotNull(message = "O número da ODS é obrigatório")
    @Positive(message = "O número deve ser positivo")
    private Integer numero;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;
    private String categoria;
    private String status;

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
