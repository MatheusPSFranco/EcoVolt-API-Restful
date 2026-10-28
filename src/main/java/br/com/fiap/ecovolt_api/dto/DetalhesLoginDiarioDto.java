package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDateTime;

public class DetalhesLoginDiarioDto {

    private Long id;
    private Long idUsuario;
    private LocalDateTime data;
    private Integer tempoUsoMinutos;
    private Character loginValido;
    private Integer sequenciaAtual;
    private Integer bonusGerado;
    private Character notificacaoExibida;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public Integer getTempoUsoMinutos() { return tempoUsoMinutos; }
    public void setTempoUsoMinutos(Integer tempoUsoMinutos) { this.tempoUsoMinutos = tempoUsoMinutos; }
    public Character getLoginValido() { return loginValido; }
    public void setLoginValido(Character loginValido) { this.loginValido = loginValido; }
    public Integer getSequenciaAtual() { return sequenciaAtual; }
    public void setSequenciaAtual(Integer sequenciaAtual) { this.sequenciaAtual = sequenciaAtual; }
    public Integer getBonusGerado() { return bonusGerado; }
    public void setBonusGerado(Integer bonusGerado) { this.bonusGerado = bonusGerado; }
    public Character getNotificacaoExibida() { return notificacaoExibida; }
    public void setNotificacaoExibida(Character notificacaoExibida) { this.notificacaoExibida = notificacaoExibida; }
}
