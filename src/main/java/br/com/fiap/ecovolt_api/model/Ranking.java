package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class Ranking {

    private Long id;
    private Usuario usuario;
    private Tier tier;
    private String periodoReferencia;
    private Integer pontuacaoTotalPeriodo;
    private Integer posicao;
    private String status;
    private LocalDate dataAtualizacao;

    //Constructors
    public Ranking(){}

    public Ranking(Long id, Usuario usuario, Tier tier, String periodoReferencia, Integer pontuacaoTotalPeriodo, Integer posicao, String status, LocalDate dataAtualizacao) {
        this.id = id;
        this.usuario = usuario;
        this.tier = tier;
        this.periodoReferencia = periodoReferencia;
        this.pontuacaoTotalPeriodo = pontuacaoTotalPeriodo;
        this.posicao = posicao;
        this.status = status;
        this.dataAtualizacao = dataAtualizacao;
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

    public String getPeriodoReferencia() {
        return periodoReferencia;
    }

    public void setPeriodoReferencia(String periodoReferencia) {
        this.periodoReferencia = periodoReferencia;
    }

    public Integer getPontuacaoTotalPeriodo() {
        return pontuacaoTotalPeriodo;
    }

    public void setPontuacaoTotalPeriodo(Integer pontuacaoTotalPeriodo) {
        this.pontuacaoTotalPeriodo = pontuacaoTotalPeriodo;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    //toString
    @Override
    public  String toString(){
        return  "Ranking{id= " + id + ", Usuario= " + usuario + ", Tier= " + tier + ", Periodo Referente= " + periodoReferencia +
                ", Pontuação Total= " + pontuacaoTotalPeriodo  + ", Posição= " + posicao +
                ", Status= " + status + "}";
    }
}
