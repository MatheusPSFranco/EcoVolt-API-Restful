package br.com.fiap.ecovolt_api.model;

public class Nivel {

    private Long id;
    private Integer numero;
    private Integer xpMinimo;
    private Integer xpMaximo;
    private String descricao;
    private String bordaPerfil;
    private String status;

    //Constructors
    public Nivel(){}

    public Nivel(Long id, Integer numero, Integer xpMinimo, Integer xpMaximo, String descricao, String bordaPerfil, String status) {
        this.id = id;
        this.numero = numero;
        this.xpMinimo = xpMinimo;
        this.xpMaximo = xpMaximo;
        this.descricao = descricao;
        this.bordaPerfil = bordaPerfil;
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

    public Integer getXpMinimo() {
        return xpMinimo;
    }

    public void setXpMinimo(Integer xpMinimo) {
        this.xpMinimo = xpMinimo;
    }

    public Integer getXpMaximo() {
        return xpMaximo;
    }

    public void setXpMaximo(Integer xpMaximo) {
        this.xpMaximo = xpMaximo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBordaPerfil() {
        return bordaPerfil;
    }

    public void setBordaPerfil(String bordaPerfil) {
        this.bordaPerfil = bordaPerfil;
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
        return  "Nivel{id= " + id + ", Número= " + numero + ", Xp Minimo= " + xpMaximo + ", Xp Maximo= " + xpMaximo +
                ", Descrição= " + descricao  + ", Borda Perfil= " + bordaPerfil + ", Status= " + status + "}";
    }
}
