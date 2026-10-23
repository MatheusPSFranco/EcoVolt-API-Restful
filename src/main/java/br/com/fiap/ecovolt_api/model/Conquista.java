package br.com.fiap.ecovolt_api.model;

public class Conquista {

    private Long id;
    private Nivel nivel;
    private String nome;
    private String descricao;
    private String tipo;
    private String criterioDesbloqueio;
    private Integer quantidadeNecessaria;
    private String medalha;
    private String status;

    //Constructors
    public Conquista(){}

    public Conquista(Long id, Nivel nivel, String nome, String descricao, String tipo, String criterioDesbloqueio, Integer quantidadeNecessaria, String medalha, String status) {
        this.id = id;
        this.nivel = nivel;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.criterioDesbloqueio = criterioDesbloqueio;
        this.quantidadeNecessaria = quantidadeNecessaria;
        this.medalha = medalha;
        this.status = status;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCriterioDesbloqueio() {
        return criterioDesbloqueio;
    }

    public void setCriterioDesbloqueio(String criterioDesbloqueio) {
        this.criterioDesbloqueio = criterioDesbloqueio;
    }

    public Integer getQuantidadeNecessaria() {
        return quantidadeNecessaria;
    }

    public void setQuantidadeNecessaria(Integer quantidadeNecessaria) {
        this.quantidadeNecessaria = quantidadeNecessaria;
    }

    public String getMedalha() {
        return medalha;
    }

    public void setMedalha(String medalha) {
        this.medalha = medalha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //toString
    @Override
    public  String toString(){
        return  "Conquista{id= " + id + ", Nivel= " + nivel + ", Nome= " + nome + ", Descrição= " + descricao +
                ", Tipo= " + tipo  + ", Criterio Desbloqueio= " + criterioDesbloqueio +
                ", Quantiddade Necessaria= " + quantidadeNecessaria + ", Status= " + status + "}";
    }
}
