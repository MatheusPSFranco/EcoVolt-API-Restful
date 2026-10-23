package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class Interacao {

    private Long id;
    private Usuario usuario;
    private String tipo;
    private LocalDate data;
    private Double peso;
    private Character interacaoValida;
    private Character spamDetectado;
    private String conteudoRelacionado;

    //Constructors
    public Interacao(){}

    public Interacao(Long id, Usuario usuario, String tipo, LocalDate data, Double peso, Character interacaoValida, Character spamDetectado, String conteudoRelacionado) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.data = data;
        this.peso = peso;
        this.interacaoValida = interacaoValida;
        this.spamDetectado = spamDetectado;
        this.conteudoRelacionado = conteudoRelacionado;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Character getInteracaoValida() {
        return interacaoValida;
    }

    public void setInteracaoValida(Character interacaoValida) {
        this.interacaoValida = interacaoValida;
    }

    public Character getSpamDetectado() {
        return spamDetectado;
    }

    public void setSpamDetectado(Character spamDetectado) {
        this.spamDetectado = spamDetectado;
    }

    public String getConteudoRelacionado() {
        return conteudoRelacionado;
    }

    public void setConteudoRelacionado(String conteudoRelacionado) {
        this.conteudoRelacionado = conteudoRelacionado;
    }

    //toString
    @Override
    public  String toString(){
        return  "Interação{id= " + id + ", Usuario= " + usuario + ", Tipo= " + tipo + ", Data= " + data +
                ", Peso= " + peso  + ", Interação Valida= " + interacaoValida +
                ", Spam detectado= " + spamDetectado + ", Conteúdo relacionado= " + conteudoRelacionado + "}";
    }
}
