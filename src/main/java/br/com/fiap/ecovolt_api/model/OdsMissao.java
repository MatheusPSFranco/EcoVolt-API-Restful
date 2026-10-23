package br.com.fiap.ecovolt_api.model;

public class OdsMissao {

    private Ods ods;
    private Missao missao;
    private Double pesoOds;

    //Constructors
    public OdsMissao(){}

    public OdsMissao(Ods ods, Missao missao, Double pesoOds) {
        this.ods = ods;
        this.missao = missao;
        this.pesoOds = pesoOds;
    }

    //Getters and Setters
    public Ods getOds() {
        return ods;
    }

    public void setOds(Ods ods) {
        this.ods = ods;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public Double getPesoOds() {
        return pesoOds;
    }

    public void setPesoOds(Double pesoOds) {
        this.pesoOds = pesoOds;
    }

    //toString
    @Override
    public  String toString(){
        return  "Ods Missão{Ods= " + ods + ", Missão= " + missao + ", Peso Ods= " + pesoOds + "}";
    }
}
