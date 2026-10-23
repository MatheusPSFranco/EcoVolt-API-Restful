package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class Pontuacao {

    private Long id;
    private Usuario usuario;
    private EnvioAcao envioAcao;
    private Validacao validacao;
    private RecursoRevisao recursoRevisao;
    private Missao missao;
    private LoginDiario loginDiario;
    private Interacao interacao;
    private String tipo;
    private Integer pontosObtidos;
    private Integer xpObtido;
    private LocalDate data;
    private String periodoReferencia;
    private String origem;
    private Nivel nivel;

    //Constructors
    public Pontuacao(){}

    public Pontuacao(Long id, Usuario usuario, EnvioAcao envioAcao, Validacao validacao, RecursoRevisao recursoRevisao, Missao missao, LoginDiario loginDiario, Interacao interacao, String tipo, Integer pontosObtidos, Integer xpObtido, LocalDate data, String periodoReferencia, String origem, Nivel nivel) {
        this.id = id;
        this.usuario = usuario;
        this.envioAcao = envioAcao;
        this.validacao = validacao;
        this.recursoRevisao = recursoRevisao;
        this.missao = missao;
        this.loginDiario = loginDiario;
        this.interacao = interacao;
        this.tipo = tipo;
        this.pontosObtidos = pontosObtidos;
        this.xpObtido = xpObtido;
        this.data = data;
        this.periodoReferencia = periodoReferencia;
        this.origem = origem;
        this.nivel = nivel;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getPeriodoReferencia() {
        return periodoReferencia;
    }

    public void setPeriodoReferencia(String periodoReferencia) {
        this.periodoReferencia = periodoReferencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getXpObtido() {
        return xpObtido;
    }

    public void setXpObtido(Integer xpObtido) {
        this.xpObtido = xpObtido;
    }

    public Integer getPontosObtidos() {
        return pontosObtidos;
    }

    public void setPontosObtidos(Integer pontosObtidos) {
        this.pontosObtidos = pontosObtidos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Interacao getInteracao() {
        return interacao;
    }

    public void setInteracao(Interacao interacao) {
        this.interacao = interacao;
    }

    public LoginDiario getLoginDiario() {
        return loginDiario;
    }

    public void setLoginDiario(LoginDiario loginDiario) {
        this.loginDiario = loginDiario;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public RecursoRevisao getRecursoRevisao() {
        return recursoRevisao;
    }

    public void setRecursoRevisao(RecursoRevisao recursoRevisao) {
        this.recursoRevisao = recursoRevisao;
    }

    public Validacao getValidacao() {
        return validacao;
    }

    public void setValidacao(Validacao validacao) {
        this.validacao = validacao;
    }

    public EnvioAcao getEnvioAcao() {
        return envioAcao;
    }

    public void setEnvioAcao(EnvioAcao envioAcao) {
        this.envioAcao = envioAcao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //toString
    @Override
    public  String toString(){
        return  "Pontuação{id= " + id + ", Usuario= " + usuario + ", Ponto Obtido= " + pontosObtidos +
                ", Xp obtido= " + xpObtido + "}";
    }
}
