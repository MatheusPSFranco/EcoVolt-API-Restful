package br.com.fiap.ecovolt_api.model;

public class Tier {

    private Long id;
    private String nome;
    private String descricao;
    private Integer ordem;
    private Integer usuariosAtivosMinimos;
    private Double percentualSubida;
    private Double percentualDescida;
    private Integer limiteUsuarios;
    private String status;

    //Constructors
    public Tier(){}

    public Tier(Long id, String nome, String descricao, Integer ordem, Integer usuariosAtivosMinimos, Double percentualSubida, Double percentualDescida, Integer limiteUsuarios, String status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ordem = ordem;
        this.usuariosAtivosMinimos = usuariosAtivosMinimos;
        this.percentualSubida = percentualSubida;
        this.percentualDescida = percentualDescida;
        this.limiteUsuarios = limiteUsuarios;
        this.status = status;
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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getUsuariosAtivosMinimos() {
        return usuariosAtivosMinimos;
    }

    public void setUsuariosAtivosMinimos(Integer usuariosAtivosMinimos) {
        this.usuariosAtivosMinimos = usuariosAtivosMinimos;
    }

    public Double getPercentualSubida() {
        return percentualSubida;
    }

    public void setPercentualSubida(Double percentualSubida) {
        this.percentualSubida = percentualSubida;
    }

    public Double getPercentualDescida() {
        return percentualDescida;
    }

    public void setPercentualDescida(Double percentualDescida) {
        this.percentualDescida = percentualDescida;
    }

    public Integer getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public void setLimiteUsuarios(Integer limiteUsuarios) {
        this.limiteUsuarios = limiteUsuarios;
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
        return  "Tier{id= " + id + ", Nome= " + nome + ", Descrição= " + descricao +
                ", Usuarios Ativos Minimos= " + usuariosAtivosMinimos  + ", Percentual de Descida= " + percentualDescida +
                ", Percentual de Subida " + percentualSubida + ", Limite Usuario= " + limiteUsuarios +
                ", Status= " + status + "}";
    }
}
