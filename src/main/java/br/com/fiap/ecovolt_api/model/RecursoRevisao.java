package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class RecursoRevisao {

    private Long id;
    private Validacao validacao;
    private Usuario usuario;
    private Usuario revisor;
    private String motivo;
    private String descricao;
    private String status;
    private LocalDate dataAbertura;
    private LocalDate dataResposta;
    private String resultado;
    private String observacao;

    //Constructors
    public RecursoRevisao(){}

    public RecursoRevisao(Long id, Validacao validacao, Usuario usuario, Usuario revisor, String motivo, String descricao, String status, LocalDate dataAbertura, LocalDate dataResposta, String resultado, String observacao) {
        this.id = id;
        this.validacao = validacao;
        this.usuario = usuario;
        this.revisor = revisor;
        this.motivo = motivo;
        this.descricao = descricao;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataResposta = dataResposta;
        this.resultado = resultado;
        this.observacao = observacao;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Validacao getValidacao() {
        return validacao;
    }

    public void setValidacao(Validacao validacao) {
        this.validacao = validacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getRevisor() {
        return revisor;
    }

    public void setRevisor(Usuario revisor) {
        this.revisor = revisor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(LocalDate dataResposta) {
        this.dataResposta = dataResposta;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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
        return  "Recurso Revisão{id= " + id + ", Validação= " + validacao + ", Usuario= " + usuario + ", Revisor= " + revisor +
                ", Motivo= " + motivo  + ", Resultado= " + resultado + "}";
    }
}
