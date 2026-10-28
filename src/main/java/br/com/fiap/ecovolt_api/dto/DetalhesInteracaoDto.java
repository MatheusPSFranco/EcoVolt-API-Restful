package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDate;

public class DetalhesInteracaoDto {

    private Long id;
    private Long idUsuario;
    private String tipo;
    private LocalDate data;
    private Double peso;
    private Character interacaoValida;
    private Character spamDetectado;
    private String conteudoRelacionado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    public Character getInteracaoValida() { return interacaoValida; }
    public void setInteracaoValida(Character interacaoValida) { this.interacaoValida = interacaoValida; }
    public Character getSpamDetectado() { return spamDetectado; }
    public void setSpamDetectado(Character spamDetectado) { this.spamDetectado = spamDetectado; }
    public String getConteudoRelacionado() { return conteudoRelacionado; }
    public void setConteudoRelacionado(String conteudoRelacionado) { this.conteudoRelacionado = conteudoRelacionado; }
}
