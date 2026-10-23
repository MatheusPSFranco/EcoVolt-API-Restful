package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class Recompensa {

    private Long id;
    private Usuario usuario;
    private Tier tier;
    private Ranking ranking;
    private String tipo;
    private String descricao;
    private Double valor;
    private Double valorMaximo;
    private String status;
    private LocalDate dataConcessao;
    private String periodoReferencia;

    //Constructors
    public Recompensa(){}

    public Recompensa(Long id, Usuario usuario, Tier tier, Ranking ranking, String tipo, String descricao, Double valor, Double valorMaximo, String status, LocalDate dataConcessao, String periodoReferencia) {
        this.id = id;
        this.usuario = usuario;
        this.tier = tier;
        this.ranking = ranking;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.valorMaximo = valorMaximo;
        this.status = status;
        this.dataConcessao = dataConcessao;
        this.periodoReferencia = periodoReferencia;
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

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataConcessao() {
        return dataConcessao;
    }

    public void setDataConcessao(LocalDate dataConcessao) {
        this.dataConcessao = dataConcessao;
    }

    public String getPeriodoReferencia() {
        return periodoReferencia;
    }

    public void setPeriodoReferencia(String periodoReferencia) {
        this.periodoReferencia = periodoReferencia;
    }

    //toString
    @Override
    public  String toString(){
        return  "Recompensa{id= " + id + ", Usuario= " + usuario + ", Tipo= " + tipo + ", Descrição= " + descricao +
                ", Valor= " + valor  + ", Status= " + status + "}";
    }
}
