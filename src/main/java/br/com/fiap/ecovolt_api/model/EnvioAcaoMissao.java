package br.com.fiap.ecovolt_api.model;

import java.time.LocalDateTime;

public class EnvioAcaoMissao {

    private EnvioAcao envioAcao;
    private Missao missao;
    private String contabilizado;
    private LocalDateTime dataContabilizacao;

    //Constructors
    public EnvioAcaoMissao(){}

    public EnvioAcaoMissao(EnvioAcao envioAcao, Missao missao, String contabilizado, LocalDateTime dataContabilizacao) {
        this.envioAcao = envioAcao;
        this.missao = missao;
        this.contabilizado = contabilizado;
        this.dataContabilizacao = dataContabilizacao;
    }

    //Getters and Setters
    public EnvioAcao getEnvioAcao() {
        return envioAcao;
    }

    public void setEnvioAcao(EnvioAcao envioAcao) {
        this.envioAcao = envioAcao;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public String getContabilizado() {
        return contabilizado;
    }

    public void setContabilizado(String contabilizado) {
        this.contabilizado = contabilizado;
    }

    public LocalDateTime getDataContabilizacao() {
        return dataContabilizacao;
    }

    public void setDataContabilizacao(LocalDateTime dataContabilizacao) {
        this.dataContabilizacao = dataContabilizacao;
    }

    //toString
    @Override
    public  String toString(){
        return  "Envio Ação Missão{Envio ação= " + envioAcao + ", Missão= " + missao + ", Contabilizado= " + contabilizado +
                ", Data Contabilização= " + dataContabilizacao + "}";
    }
}
