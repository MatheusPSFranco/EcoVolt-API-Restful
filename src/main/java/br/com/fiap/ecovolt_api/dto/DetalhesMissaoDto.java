package br.com.fiap.ecovolt_api.dto;

public class DetalhesMissaoDto {

    private Long id;
    private String nome;
    private String descricao;
    private String dificuldade;
    private Integer pontuacaoRecompensa;
    private Integer xpRecompensa;
    private Integer quantidadeSemanal;
    private String tipo;
    private Boolean status;
    private Long idTier;
    private Long idNivel;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDificuldade() { return dificuldade; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }
    public Integer getPontuacaoRecompensa() { return pontuacaoRecompensa; }
    public void setPontuacaoRecompensa(Integer pontuacaoRecompensa) { this.pontuacaoRecompensa = pontuacaoRecompensa; }
    public Integer getXpRecompensa() { return xpRecompensa; }
    public void setXpRecompensa(Integer xpRecompensa) { this.xpRecompensa = xpRecompensa; }
    public Integer getQuantidadeSemanal() { return quantidadeSemanal; }
    public void setQuantidadeSemanal(Integer quantidadeSemanal) { this.quantidadeSemanal = quantidadeSemanal; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public Long getIdTier() { return idTier; }
    public void setIdTier(Long idTier) { this.idTier = idTier; }
    public Long getIdNivel() { return idNivel; }
    public void setIdNivel(Long idNivel) { this.idNivel = idNivel; }
}
