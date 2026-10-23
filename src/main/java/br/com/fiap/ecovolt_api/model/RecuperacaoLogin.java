package br.com.fiap.ecovolt_api.model;

import java.time.LocalDateTime;

public class RecuperacaoLogin {

    private Long id;
    private Usuario usuario;
    private LoginDiario login;
    private LocalDateTime dataRecuperacao;
    private LocalDateTime dataLoginRecuperado;
    private String mesReferencia;
    private Character anuncioAssistido;
    private Integer anoReferencia;
    private String status;

    //Constructors
    public RecuperacaoLogin(){}

    public RecuperacaoLogin(Long id, Usuario usuario, LoginDiario login, LocalDateTime dataRecuperacao, LocalDateTime dataLoginRecuperado, String mesReferencia, Character anuncioAssistido, Integer anoReferencia, String status) {
        this.id = id;
        this.usuario = usuario;
        this.login = login;
        this.dataRecuperacao = dataRecuperacao;
        this.dataLoginRecuperado = dataLoginRecuperado;
        this.mesReferencia = mesReferencia;
        this.anuncioAssistido = anuncioAssistido;
        this.anoReferencia = anoReferencia;
        this.status = status;
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

    public LoginDiario getLogin() {
        return login;
    }

    public void setLogin(LoginDiario login) {
        this.login = login;
    }

    public LocalDateTime getDataRecuperacao() {
        return dataRecuperacao;
    }

    public void setDataRecuperacao(LocalDateTime dataRecuperacao) {
        this.dataRecuperacao = dataRecuperacao;
    }

    public LocalDateTime getDataLoginRecuperado() {
        return dataLoginRecuperado;
    }

    public void setDataLoginRecuperado(LocalDateTime dataLoginRecuperado) {
        this.dataLoginRecuperado = dataLoginRecuperado;
    }

    public String getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(String mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public Character getAnuncioAssistido() {
        return anuncioAssistido;
    }

    public void setAnuncioAssistido(Character anuncioAssistido) {
        this.anuncioAssistido = anuncioAssistido;
    }

    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
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
        return  "Recuperação Login{id= " + id + ", Usuario= " + usuario + ", Login= " + login +
                ", Data Login Recuperado= " + dataLoginRecuperado + ", Status= " + status + "}";
    }
}
