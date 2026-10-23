package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class UsuarioMissao {

    private Usuario usuario;
    private Missao missao;
    private LocalDate dataInicio;
    private LocalDate dataConclusao;
    private String status;
    private Double progresso;

    //Constructors
    public UsuarioMissao(){}

    public UsuarioMissao(Usuario usuario, Missao missao, LocalDate dataInicio, LocalDate dataConclusao, String status, Double progresso) {
        this.usuario = usuario;
        this.missao = missao;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.progresso = progresso;
    }

    //Getters and Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProgresso() {
        return progresso;
    }

    public void setProgresso(Double progresso) {
        this.progresso = progresso;
    }

    //toString
    @Override
    public  String toString(){
        return  "Usuario Misssão{Usuario= " + usuario + ", Missão = " + missao + ", Data Inicio= " + dataInicio +
                ", Data Conclusão= " + dataConclusao + ", Status= " + status  + ", Progresso= " + progresso + "}";
    }
}
