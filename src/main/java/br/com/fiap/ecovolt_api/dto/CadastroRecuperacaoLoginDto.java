package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class CadastroRecuperacaoLoginDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    private Long idLogin;
    private LocalDateTime dataLoginRecuperado;
    private String mesReferencia;
    private Character anuncioAssistido;
    private Integer anoReferencia;
    private String status;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdLogin() { return idLogin; }
    public void setIdLogin(Long idLogin) { this.idLogin = idLogin; }
    public LocalDateTime getDataLoginRecuperado() { return dataLoginRecuperado; }
    public void setDataLoginRecuperado(LocalDateTime dataLoginRecuperado) { this.dataLoginRecuperado = dataLoginRecuperado; }
    public String getMesReferencia() { return mesReferencia; }
    public void setMesReferencia(String mesReferencia) { this.mesReferencia = mesReferencia; }
    public Character getAnuncioAssistido() { return anuncioAssistido; }
    public void setAnuncioAssistido(Character anuncioAssistido) { this.anuncioAssistido = anuncioAssistido; }
    public Integer getAnoReferencia() { return anoReferencia; }
    public void setAnoReferencia(Integer anoReferencia) { this.anoReferencia = anoReferencia; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
