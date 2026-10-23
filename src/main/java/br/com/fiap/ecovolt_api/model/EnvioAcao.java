package br.com.fiap.ecovolt_api.model;

import java.time.LocalDateTime;

public class EnvioAcao {

    private Long id;
    private Usuario usuario;
    private AcaoSustentavel acaoSustentavel;
    private LocalDateTime dataEnvio;
    private String tipoEvidencia;
    private String arquivoEvidencia;
    private String status;
    private Integer pontuacaoPrevista;
    private String observacaoUsuario;

    //Constructors
    public EnvioAcao(){}

    public EnvioAcao(Long id, Usuario usuario, AcaoSustentavel acaoSustentavel, LocalDateTime dataEnvio, String tipoEvidencia, String arquivoEvidencia, String status, Integer pontuacaoPrevista, String observacaoUsuario) {
        this.id = id;
        this.usuario = usuario;
        this.acaoSustentavel = acaoSustentavel;
        this.dataEnvio = dataEnvio;
        this.tipoEvidencia = tipoEvidencia;
        this.arquivoEvidencia = arquivoEvidencia;
        this.status = status;
        this.pontuacaoPrevista = pontuacaoPrevista;
        this.observacaoUsuario = observacaoUsuario;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AcaoSustentavel getAcaoSustentavel() {
        return acaoSustentavel;
    }

    public void setAcaoSustentavel(AcaoSustentavel acaoSustentavel) {
        this.acaoSustentavel = acaoSustentavel;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public void setTipoEvidencia(String tipoEvidencia) {
        this.tipoEvidencia = tipoEvidencia;
    }

    public String getArquivoEvidencia() {
        return arquivoEvidencia;
    }

    public void setArquivoEvidencia(String arquivoEvidencia) {
        this.arquivoEvidencia = arquivoEvidencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPontuacaoPrevista() {
        return pontuacaoPrevista;
    }

    public void setPontuacaoPrevista(Integer pontuacaoPrevista) {
        this.pontuacaoPrevista = pontuacaoPrevista;
    }

    public String getObservacaoUsuario() {
        return observacaoUsuario;
    }

    public void setObservacaoUsuario(String observacaoUsuario) {
        this.observacaoUsuario = observacaoUsuario;
    }

    //toString
    @Override
    public  String toString(){
        return  "Envio Ação{id= " + id + ", Usuario= " + usuario + ", Ação Sustentavel= " + acaoSustentavel +
                ", Data Envio= " + dataEnvio + ", Tipo Evidencia= " + tipoEvidencia  + ", Status= " + status +
                ", Pontuação Prevista= " + pontuacaoPrevista + ", Observação= " + observacaoUsuario + "}";
    }
}
