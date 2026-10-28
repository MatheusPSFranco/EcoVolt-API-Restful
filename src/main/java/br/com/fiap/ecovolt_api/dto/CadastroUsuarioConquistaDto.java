package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CadastroUsuarioConquistaDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    @NotNull(message = "O ID da conquista é obrigatório")
    @Positive(message = "O ID da conquista deve ser positivo")
    private Long idConquista;

    @PositiveOrZero(message = "O progresso atual deve ser zero ou positivo")
    private Integer progressoAtual;

    private String status;
    private Character visivelPerfil;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdConquista() { return idConquista; }
    public void setIdConquista(Long idConquista) { this.idConquista = idConquista; }
    public Integer getProgressoAtual() { return progressoAtual; }
    public void setProgressoAtual(Integer progressoAtual) { this.progressoAtual = progressoAtual; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Character getVisivelPerfil() { return visivelPerfil; }
    public void setVisivelPerfil(Character visivelPerfil) { this.visivelPerfil = visivelPerfil; }
}
