package br.com.fiap.ecovolt_api.dto;

public class DetalhesNivelDto {

    private Long id;
    private Integer numero;
    private Integer xpMinimo;
    private Integer xpMaximo;
    private String descricao;
    private String bordaPerfil;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getXpMinimo() { return xpMinimo; }
    public void setXpMinimo(Integer xpMinimo) { this.xpMinimo = xpMinimo; }
    public Integer getXpMaximo() { return xpMaximo; }
    public void setXpMaximo(Integer xpMaximo) { this.xpMaximo = xpMaximo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getBordaPerfil() { return bordaPerfil; }
    public void setBordaPerfil(String bordaPerfil) { this.bordaPerfil = bordaPerfil; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
