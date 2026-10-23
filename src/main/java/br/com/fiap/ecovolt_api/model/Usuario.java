package br.com.fiap.ecovolt_api.model;

import java.time.LocalDate;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private LocalDate dataCadastro;
    private String status;
    private String tipo;
    private Integer xpTotal;
    private Integer pontuacaoTotal;
    private LocalDate dataUltimoLogin;
    private Character ativo;
    private Tier tier;
    private Nivel nivel;

    //Constructors
    public Usuario(){}

    public Usuario(Long id, String nome, String email, String senhaHash, LocalDate dataCadastro, String status, String tipo, Integer xpTotal, Integer pontuacaoTotal, LocalDate dataUltimoLogin, Character ativo, Tier tier, Nivel nivel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.dataCadastro = dataCadastro;
        this.status = status;
        this.tipo = tipo;
        this.xpTotal = xpTotal;
        this.pontuacaoTotal = pontuacaoTotal;
        this.dataUltimoLogin = dataUltimoLogin;
        this.ativo = ativo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getXpTotal() {
        return xpTotal;
    }

    public void setXpTotal(Integer xpTotal) {
        this.xpTotal = xpTotal;
    }

    public Integer getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(Integer pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public LocalDate getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public void setDataUltimoLogin(LocalDate dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }

    public Character getAtivo() {
        return ativo;
    }

    public void setAtivo(Character ativo) {
        this.ativo = ativo;
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

    //toString()
    @Override
    public String toString(){
        return "Usuario{Nome=" + nome + ", Tipo= " + tipo + ", Status= " + status + "}";
    }
}
