package br.com.fiap.ecovolt_api.dto;

public class DetalhesOdsMissaoDto {

    private Long idOds;
    private Long idMissao;
    private Double pesoOds;

    public Long getIdOds() { return idOds; }
    public void setIdOds(Long idOds) { this.idOds = idOds; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public Double getPesoOds() { return pesoOds; }
    public void setPesoOds(Double pesoOds) { this.pesoOds = pesoOds; }
}
