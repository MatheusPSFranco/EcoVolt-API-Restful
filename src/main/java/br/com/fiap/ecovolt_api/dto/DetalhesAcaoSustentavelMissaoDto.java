package br.com.fiap.ecovolt_api.dto;

public class DetalhesAcaoSustentavelMissaoDto {

    private Long idAcaoSustentavel;
    private Long idMissao;
    private Integer quantidadeNecessaria;
    private String obrigatoria;

    public Long getIdAcaoSustentavel() { return idAcaoSustentavel; }
    public void setIdAcaoSustentavel(Long idAcaoSustentavel) { this.idAcaoSustentavel = idAcaoSustentavel; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public Integer getQuantidadeNecessaria() { return quantidadeNecessaria; }
    public void setQuantidadeNecessaria(Integer quantidadeNecessaria) { this.quantidadeNecessaria = quantidadeNecessaria; }
    public String getObrigatoria() { return obrigatoria; }
    public void setObrigatoria(String obrigatoria) { this.obrigatoria = obrigatoria; }
}
