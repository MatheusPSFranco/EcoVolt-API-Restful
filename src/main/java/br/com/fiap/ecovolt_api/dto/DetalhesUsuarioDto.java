package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDate;

public class DetalhesUsuarioDto {

    private Long id;
    private String nome;
    private String email;
    private LocalDate dataCadastro;
    private String status;
    private String tipo;
    private Integer xpTotal;
    private Integer pontuacaoTotal;
    private LocalDate dataUltimoLogin;
    private Character ativo;
    private Long idTier;
    private Long idNivel;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getXpTotal() { return xpTotal; }
    public void setXpTotal(Integer xpTotal) { this.xpTotal = xpTotal; }
    public Integer getPontuacaoTotal() { return pontuacaoTotal; }
    public void setPontuacaoTotal(Integer pontuacaoTotal) { this.pontuacaoTotal = pontuacaoTotal; }
    public LocalDate getDataUltimoLogin() { return dataUltimoLogin; }
    public void setDataUltimoLogin(LocalDate dataUltimoLogin) { this.dataUltimoLogin = dataUltimoLogin; }
    public Character getAtivo() { return ativo; }
    public void setAtivo(Character ativo) { this.ativo = ativo; }
    public Long getIdTier() { return idTier; }
    public void setIdTier(Long idTier) { this.idTier = idTier; }
    public Long getIdNivel() { return idNivel; }
    public void setIdNivel(Long idNivel) { this.idNivel = idNivel; }
}
