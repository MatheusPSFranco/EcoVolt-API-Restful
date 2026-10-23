package br.com.fiap.ecovolt_api.model;

import java.time.LocalDateTime;

public class UsuarioConquista {

    private Usuario usuario;
    private Conquista conquista;
    private LocalDateTime dataDesbloqueio;
    private Integer progressoAtual;
    private String status;
    private Character visivelPerfil;

    //Constructors
    public UsuarioConquista(){}

    public UsuarioConquista(Usuario usuario, Conquista conquista, LocalDateTime dataDesbloqueio, Integer progressoAtual, String status, Character visivelPerfil) {
        this.usuario = usuario;
        this.conquista = conquista;
        this.dataDesbloqueio = dataDesbloqueio;
        this.progressoAtual = progressoAtual;
        this.status = status;
        this.visivelPerfil = visivelPerfil;
    }

    //Getters and Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Conquista getConquista() {
        return conquista;
    }

    public void setConquista(Conquista conquista) {
        this.conquista = conquista;
    }

    public LocalDateTime getDataDesbloqueio() {
        return dataDesbloqueio;
    }

    public void setDataDesbloqueio(LocalDateTime dataDesbloqueio) {
        this.dataDesbloqueio = dataDesbloqueio;
    }

    public Integer getProgressoAtual() {
        return progressoAtual;
    }

    public void setProgressoAtual(Integer progressoAtual) {
        this.progressoAtual = progressoAtual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Character getVisivelPerfil() {
        return visivelPerfil;
    }

    public void setVisivelPerfil(Character visivelPerfil) {
        this.visivelPerfil = visivelPerfil;
    }

    //toString
    @Override
    public  String toString(){
        return  "Usuario Conquista{Usuario= " + usuario + ", Conquista= " + conquista + ", Data Desbloqueio= " + dataDesbloqueio +
                ", Progresso Atual= " + progressoAtual + ", Status= " + status  + ", Visivel Perfil= " + visivelPerfil + "}";
    }
}
