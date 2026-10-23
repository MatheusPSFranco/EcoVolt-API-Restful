package br.com.fiap.ecovolt_api.model;

public class AcaoSustentavelMissao {

    private AcaoSustentavel acaoSustentavel;
    private Missao missao;
    private Integer quantidadeNecessaria;
    private String obrigatoria;

    //Constructors
    public AcaoSustentavelMissao(){}

    public AcaoSustentavelMissao(AcaoSustentavel acaoSustentavel, Missao missao, Integer quantidadeNecessaria, String obrigatoria) {
        this.acaoSustentavel = acaoSustentavel;
        this.missao = missao;
        this.quantidadeNecessaria = quantidadeNecessaria;
        this.obrigatoria = obrigatoria;
    }

    //Getters and Setters
    public AcaoSustentavel getAcaoSustentavel() {
        return acaoSustentavel;
    }

    public void setAcaoSustentavel(AcaoSustentavel acaoSustentavel) {
        this.acaoSustentavel = acaoSustentavel;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public Integer getQuantidadeNecessaria() {
        return quantidadeNecessaria;
    }

    public void setQuantidadeNecessaria(Integer quantidadeNecessaria) {
        this.quantidadeNecessaria = quantidadeNecessaria;
    }

    public String getObrigatoria() {
        return obrigatoria;
    }

    public void setObrigatoria(String obrigatoria) {
        this.obrigatoria = obrigatoria;
    }

    //toString
    @Override
    public  String toString(){
        return  "Ação Sustentavel Missão{Ação Sustentavel= " + acaoSustentavel + ", Missão= " + missao +
                ", Quantidade Necessaria= " + quantidadeNecessaria + ", Obrigatoria= " + obrigatoria + "}";
    }
}
