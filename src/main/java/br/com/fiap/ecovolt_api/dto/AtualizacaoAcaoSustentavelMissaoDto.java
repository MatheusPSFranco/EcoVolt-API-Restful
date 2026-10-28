package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.PositiveOrZero;

public class AtualizacaoAcaoSustentavelMissaoDto {

    @PositiveOrZero(message = "A quantidade necessária deve ser zero ou positiva")
    private Integer quantidadeNecessaria;

    private String obrigatoria;

    public Integer getQuantidadeNecessaria() { return quantidadeNecessaria; }
    public void setQuantidadeNecessaria(Integer quantidadeNecessaria) { this.quantidadeNecessaria = quantidadeNecessaria; }
    public String getObrigatoria() { return obrigatoria; }
    public void setObrigatoria(String obrigatoria) { this.obrigatoria = obrigatoria; }
}
