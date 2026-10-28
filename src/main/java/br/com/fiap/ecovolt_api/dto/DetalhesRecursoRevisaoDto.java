package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDate;

public class DetalhesRecursoRevisaoDto {

    private Long id;
    private Long idValidacao;
    private Long idUsuario;
    private Long idRevisor;
    private String motivo;
    private String descricao;
    private String status;
    private LocalDate dataAbertura;
    private LocalDate dataResposta;
    private String resultado;
    private String observacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
    public LocalDate getDataResposta() { return dataResposta; }
    public void setDataResposta(LocalDate dataResposta) { this.dataResposta = dataResposta; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
