package br.com.fiap.ecovolt_api.model;

public class Ods {

    private Long id;
    private Integer numero;
    private String nome;
    private String descricao;
    private String categoria;
    private String status;

    //Constructors
    public Ods(){}

    public Ods(Long id, Integer numero, String nome, String descricao, String categoria, String status) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.status = status;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
        return  "Ods{id= " + id + ", Número= " + numero + ", Nome= " + nome + ", Descrição= " + descricao +
                ", Categoria= " + categoria  + ", Status= " + status + "}";
    }
}
