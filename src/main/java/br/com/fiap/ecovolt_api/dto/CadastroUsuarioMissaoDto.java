package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CadastroUsuarioMissaoDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    @NotNull(message = "O ID da missão é obrigatório")
    @Positive(message = "O ID da missão deve ser positivo")
    private Long idMissao;

    private String status;
    private Double progresso;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getProgresso() { return progresso; }
    public void setProgresso(Double progresso) { this.progresso = progresso; }
}
