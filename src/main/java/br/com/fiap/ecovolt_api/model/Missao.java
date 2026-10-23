package br.com.fiap.ecovolt_api.model;

public class Missao {

    private Long id;
    private String nome;
    private String descricao;
    private String dificuldade;
    private Integer pontuacaoRecompensa;
    private Integer xpRecompensa;
    private Integer quantidadeSemanal;
    private String tipo;
    private Boolean status;
    private Tier tier;
    private Nivel nivel;

    //Constructors
    public Missao(){}

    public Missao(Long id, String nome, String descricao, String dificuldade, Integer pontuacaoRecompensa, Integer xpRecompensa, Integer quantidadeSemanal, String tipo, Boolean status, Tier tier, Nivel nivel) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.pontuacaoRecompensa = pontuacaoRecompensa;
        this.xpRecompensa = xpRecompensa;
        this.quantidadeSemanal = quantidadeSemanal;
        this.tipo = tipo;
        this.status = status;
        this.tier = tier;
        this.nivel = nivel;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPontuacaoRecompensa() {
        return pontuacaoRecompensa;
    }

    public void setPontuacaoRecompensa(Integer pontuacaoRecompensa) {
        this.pontuacaoRecompensa = pontuacaoRecompensa;
    }

    public Integer getXpRecompensa() {
        return xpRecompensa;
    }

    public void setXpRecompensa(Integer xpRecompensa) {
        this.xpRecompensa = xpRecompensa;
    }

    public Integer getQuantidadeSemanal() {
        return quantidadeSemanal;
    }

    public void setQuantidadeSemanal(Integer quantidadeSemanal) {
        this.quantidadeSemanal = quantidadeSemanal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    //toString
    @Override
    public  String toString(){
        return  "Missão{id= " + id + ", Nome= " + nome + ", Descrição= " + descricao + ", Dificuldade= " + dificuldade +
                ", Pontuação Reconpensa= " + pontuacaoRecompensa  + ", Xp Reconpensa= " + xpRecompensa +
                ", Quantidade Semanal= " + quantidadeSemanal + ", Tipo= " + tipo + ", Tier= " + tier +
                ", Nivel= " + nivel + "}";
    }
}
