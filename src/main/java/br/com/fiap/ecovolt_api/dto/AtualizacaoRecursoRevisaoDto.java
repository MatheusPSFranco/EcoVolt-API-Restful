package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AtualizacaoRecursoRevisaoDto {

    @NotNull(message = "O ID da validação é obrigatório")
    @Positive(message = "O ID da validação deve ser positivo")
    private Long idValidacao;

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    private Long idRevisor;

    @NotBlank(message = "O motivo é obrigatório")
    private String motivo;

    private String descricao;
    private String status;
    private String resultado;
    private String observacao;

    public Long getIdValidacao() { return idValidacao; }
    public void setIdValidacao(Long idValidacao) { this.idValidacao = idValidacao; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdRevisor() { return idRevisor; }
    public void setIdRevisor(Long idRevisor) { this.idRevisor = idRevisor; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
