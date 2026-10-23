package br.com.fiap.ecovolt_api.model;

import java.time.LocalDateTime;

public class LoginDiario {

    private Long id;
    private Usuario usuario;
    private LocalDateTime data;
    private Integer tempoUsoMinutos;
    private Character loginValido;
    private Integer sequenciaAtual;
    private Integer bonusGerado;
    private Character notificacaoExibida;

    //Constructors
    public LoginDiario(){}

    public LoginDiario(Long id, Usuario usuario, LocalDateTime data, Integer tempoUsoMinutos, Character loginValido, Integer sequenciaAtual, Integer bonusGerado, Character notificacaoExibida) {
        this.id = id;
        this.usuario = usuario;
        this.data = data;
        this.tempoUsoMinutos = tempoUsoMinutos;
        this.loginValido = loginValido;
        this.sequenciaAtual = sequenciaAtual;
        this.bonusGerado = bonusGerado;
        this.notificacaoExibida = notificacaoExibida;
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Integer getTempoUsoMinutos() {
        return tempoUsoMinutos;
    }

    public void setTempoUsoMinutos(Integer tempoUsoMinutos) {
        this.tempoUsoMinutos = tempoUsoMinutos;
    }

    public Character getLoginValido() {
        return loginValido;
    }

    public void setLoginValido(Character loginValido) {
        this.loginValido = loginValido;
    }

    public Integer getSequenciaAtual() {
        return sequenciaAtual;
    }

    public void setSequenciaAtual(Integer sequenciaAtual) {
        this.sequenciaAtual = sequenciaAtual;
    }

    public Integer getBonusGerado() {
        return bonusGerado;
    }

    public void setBonusGerado(Integer bonusGerado) {
        this.bonusGerado = bonusGerado;
    }

    public Character getNotificacaoExibida() {
        return notificacaoExibida;
    }

    public void setNotificacaoExibida(Character notificacaoExibida) {
        this.notificacaoExibida = notificacaoExibida;
    }

    //toString
    @Override
    public  String toString(){
        return  "Login Diario{id= " + id + ", Usuario= " + usuario + ", Data= " + data + ", Temopo em Minutos de Uso= " + tempoUsoMinutos +
                ", Login Valido= " + loginValido  + ", Sequencia Atual= " + sequenciaAtual +
                ", Bonus Gerado= " + bonusGerado + ", Notificação Exibida= " + notificacaoExibida + "}";
    }
}
