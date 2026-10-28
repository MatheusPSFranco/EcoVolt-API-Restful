package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.PositiveOrZero;

public class AtualizacaoUsuarioConquistaDto {

    @PositiveOrZero(message = "O progresso atual deve ser zero ou positivo")
    private Integer progressoAtual;

    private String status;
    private Character visivelPerfil;

    public Integer getProgressoAtual() { return progressoAtual; }
    public void setProgressoAtual(Integer progressoAtual) { this.progressoAtual = progressoAtual; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Character getVisivelPerfil() { return visivelPerfil; }
    public void setVisivelPerfil(Character visivelPerfil) { this.visivelPerfil = visivelPerfil; }
}
