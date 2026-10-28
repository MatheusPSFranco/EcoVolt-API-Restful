package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class AtualizacaoInteracaoDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    private String tipo;
    private LocalDate data;
    private Double peso;
    private Character interacaoValida;
    private Character spamDetectado;
    private String conteudoRelacionado;

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
