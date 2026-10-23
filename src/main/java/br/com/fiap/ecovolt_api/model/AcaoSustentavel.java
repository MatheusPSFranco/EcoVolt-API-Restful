package br.com.fiap.ecovolt_api.model;

public class AcaoSustentavel {

    private Long id;
    private Ods ods;
    private String nome;
    private String descricao;
    private String dificuldade;
    private Double impactoAmbiental;
    private Double esforcoNecessario;
    private String  frequenciaRecomendada;
    private Integer pontuacaoBase;
    private Integer limiteDiario;
    private String status;

    //Constructors
    public AcaoSustentavel(){}

    public AcaoSustentavel(Long id, Ods ods, String nome, String descricao, String dificuldade, Double impactoAmbiental, Double esforcoNecessario, String frequenciaRecomendada, Integer pontuacaoBase, Integer limiteDiario, String status) {
        this.id = id;
        this.ods = ods;
        this.nome = nome;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.impactoAmbiental = impactoAmbiental;
        this.esforcoNecessario = esforcoNecessario;
        this.frequenciaRecomendada = frequenciaRecomendada;
        this.pontuacaoBase = pontuacaoBase;
        this.limiteDiario = limiteDiario;
        this.status = status;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ods getOds() {
        return ods;
    }

    public void setOds(Ods ods) {
        this.ods = ods;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Double getImpactoAmbiental() {
        return impactoAmbiental;
    }

    public void setImpactoAmbiental(Double impactoAmbiental) {
        this.impactoAmbiental = impactoAmbiental;
    }

    public Double getEsforcoNecessario() {
        return esforcoNecessario;
    }

    public void setEsforcoNecessario(Double esforcoNecessario) {
        this.esforcoNecessario = esforcoNecessario;
    }

    public String getFrequenciaRecomendada() {
        return frequenciaRecomendada;
    }

    public void setFrequenciaRecomendada(String frequenciaRecomendada) {
        this.frequenciaRecomendada = frequenciaRecomendada;
    }

    public Integer getPontuacaoBase() {
        return pontuacaoBase;
    }

    public void setPontuacaoBase(Integer pontuacaoBase) {
        this.pontuacaoBase = pontuacaoBase;
    }

    public Integer getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(Integer limiteDiario) {
        this.limiteDiario = limiteDiario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //toString
    @Override
    public  String toString(){
        return  "Ação Sustentavel{id= " + id + ", Ods= " + ods + ", Nome= " + nome + ", Descrição= " + descricao +
                ", Dificuldade= " + dificuldade  + ", Impacto Ambiental= " + impactoAmbiental +
                ", Esforço Necessario= " + esforcoNecessario + ", Frequencia Necessaria= " + frequenciaRecomendada +
                ", Pontuação base= " + pontuacaoBase + ", Limite Diario= " + limiteDiario +
                ", Status= " + status + "}";
    }
}
