package br.com.fiap.ecovolt_api.dto;

public class DetalhesAcaoSustentavelDto {

    private Long id;
    private Long idOds;
    private String nome;
    private String descricao;
    private String dificuldade;
    private Double impactoAmbiental;
    private Double esforcoNecessario;
    private String frequenciaRecomendada;
    private Integer pontuacaoBase;
    private Integer limiteDiario;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdOds() { return idOds; }
    public void setIdOds(Long idOds) { this.idOds = idOds; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDificuldade() { return dificuldade; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }
    public Double getImpactoAmbiental() { return impactoAmbiental; }
    public void setImpactoAmbiental(Double impactoAmbiental) { this.impactoAmbiental = impactoAmbiental; }
    public Double getEsforcoNecessario() { return esforcoNecessario; }
    public void setEsforcoNecessario(Double esforcoNecessario) { this.esforcoNecessario = esforcoNecessario; }
    public String getFrequenciaRecomendada() { return frequenciaRecomendada; }
    public void setFrequenciaRecomendada(String frequenciaRecomendada) { this.frequenciaRecomendada = frequenciaRecomendada; }
    public Integer getPontuacaoBase() { return pontuacaoBase; }
    public void setPontuacaoBase(Integer pontuacaoBase) { this.pontuacaoBase = pontuacaoBase; }
    public Integer getLimiteDiario() { return limiteDiario; }
    public void setLimiteDiario(Integer limiteDiario) { this.limiteDiario = limiteDiario; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
