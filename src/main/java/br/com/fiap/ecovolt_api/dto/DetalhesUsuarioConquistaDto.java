package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDateTime;

public class DetalhesUsuarioConquistaDto {

    private Long idUsuario;
    private Long idConquista;
    private LocalDateTime dataDesbloqueio;
    private Integer progressoAtual;
    private String status;
    private Character visivelPerfil;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdConquista() { return idConquista; }
    public void setIdConquista(Long idConquista) { this.idConquista = idConquista; }
    public LocalDateTime getDataDesbloqueio() { return dataDesbloqueio; }
    public void setDataDesbloqueio(LocalDateTime dataDesbloqueio) { this.dataDesbloqueio = dataDesbloqueio; }
    public Integer getProgressoAtual() { return progressoAtual; }
    public void setProgressoAtual(Integer progressoAtual) { this.progressoAtual = progressoAtual; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Character getVisivelPerfil() { return visivelPerfil; }
    public void setVisivelPerfil(Character visivelPerfil) { this.visivelPerfil = visivelPerfil; }
}
