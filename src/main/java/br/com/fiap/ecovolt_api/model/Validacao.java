package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class Validacao {

    private Long id;
    private EnvioAcao envioAcao;
    private String tipo;
    private String status;
    private String resultadoIa;
    private Double scoreConfiancaIa;
    private Character fraudeDetectada;
    private Character conteudoRepetido;
    private Character textoConfirmado;
    private Character ocorrenciaAprovada;
    private LocalDate data;
    private String observacao;

    //Constructors
    public Validacao() {}

    public Validacao(Long id, EnvioAcao envioAcao, String tipo, String status, String resultadoIa, Double scoreConfiancaIa, Character fraudeDetectada, Character conteudoRepetido, Character textoConfirmado, Character ocorrenciaAprovada, LocalDate data, String observacao) {
        this.id = id;
        this.envioAcao = envioAcao;
        this.tipo = tipo;
        this.status = status;
        this.resultadoIa = resultadoIa;
        this.scoreConfiancaIa = scoreConfiancaIa;
        this.fraudeDetectada = fraudeDetectada;
        this.conteudoRepetido = conteudoRepetido;
        this.textoConfirmado = textoConfirmado;
        this.ocorrenciaAprovada = ocorrenciaAprovada;
        this.data = data;
        this.observacao = observacao;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnvioAcao getEnvioAcao() {
        return envioAcao;
    }

    public void setEnvioAcao(EnvioAcao envioAcao) {
        this.envioAcao = envioAcao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultadoIa() {
        return resultadoIa;
    }

    public void setResultadoIa(String resultadoIa) {
        this.resultadoIa = resultadoIa;
    }

    public Double getScoreConfiancaIa() {
        return scoreConfiancaIa;
    }

    public void setScoreConfiancaIa(Double scoreConfiancaIa) {
        this.scoreConfiancaIa = scoreConfiancaIa;
    }

    public Character getFraudeDetectada() {
        return fraudeDetectada;
    }

    public void setFraudeDetectada(Character fraudeDetectada) {
        this.fraudeDetectada = fraudeDetectada;
    }

    public Character getConteudoRepetido() {
        return conteudoRepetido;
    }

    public void setConteudoRepetido(Character conteudoRepetido) {
        this.conteudoRepetido = conteudoRepetido;
    }

    public Character getTextoConfirmado() {
        return textoConfirmado;
    }

    public void setTextoConfirmado(Character textoConfirmado) {
        this.textoConfirmado = textoConfirmado;
    }

    public Character getOcorrenciaAprovada() {
        return ocorrenciaAprovada;
    }

    public void setOcorrenciaAprovada(Character ocorrenciaAprovada) {
        this.ocorrenciaAprovada = ocorrenciaAprovada;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    //toString
    @Override
    public  String toString(){
        return  "Validação{id= " + id + ", Envio ação= " + envioAcao + ", Tipo= " + tipo + ", Status= " + status +
                ", Resultado IA= " + resultadoIa  + ", Score confiança IA= " + scoreConfiancaIa +
                ", Ocorrencia Aprovada= " + ocorrenciaAprovada + ", Data= " + data + ", Observação= " + observacao + "}";
    }
}
