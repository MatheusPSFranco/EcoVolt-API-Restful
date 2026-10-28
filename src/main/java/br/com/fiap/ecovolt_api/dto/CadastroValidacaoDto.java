package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CadastroValidacaoDto {

    @NotNull(message = "O ID do envio de ação é obrigatório")
    @Positive(message = "O ID do envio de ação deve ser positivo")
    private Long idEnvioAcao;

    private String tipo;
    private String status;
    private String resultadoIa;
    private Double scoreConfiancaIa;
    private Character fraudeDetectada;
    private Character conteudoRepetido;
    private Character textoConfirmado;
    private Character ocorrenciaAprovada;
    private String observacao;

    public Long getIdEnvioAcao() { return idEnvioAcao; }
    public void setIdEnvioAcao(Long idEnvioAcao) { this.idEnvioAcao = idEnvioAcao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getResultadoIa() { return resultadoIa; }
    public void setResultadoIa(String resultadoIa) { this.resultadoIa = resultadoIa; }
    public Double getScoreConfiancaIa() { return scoreConfiancaIa; }
    public void setScoreConfiancaIa(Double scoreConfiancaIa) { this.scoreConfiancaIa = scoreConfiancaIa; }
    public Character getFraudeDetectada() { return fraudeDetectada; }
    public void setFraudeDetectada(Character fraudeDetectada) { this.fraudeDetectada = fraudeDetectada; }
    public Character getConteudoRepetido() { return conteudoRepetido; }
    public void setConteudoRepetido(Character conteudoRepetido) { this.conteudoRepetido = conteudoRepetido; }
    public Character getTextoConfirmado() { return textoConfirmado; }
    public void setTextoConfirmado(Character textoConfirmado) { this.textoConfirmado = textoConfirmado; }
    public Character getOcorrenciaAprovada() { return ocorrenciaAprovada; }
    public void setOcorrenciaAprovada(Character ocorrenciaAprovada) { this.ocorrenciaAprovada = ocorrenciaAprovada; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
