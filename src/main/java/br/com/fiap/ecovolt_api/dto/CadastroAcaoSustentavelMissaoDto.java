package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CadastroAcaoSustentavelMissaoDto {

    @NotNull(message = "O ID da ação sustentável é obrigatório")
    @Positive(message = "O ID da ação sustentável deve ser positivo")
    private Long idAcaoSustentavel;

    @NotNull(message = "O ID da missão é obrigatório")
    @Positive(message = "O ID da missão deve ser positivo")
    private Long idMissao;

    @PositiveOrZero(message = "A quantidade necessária deve ser zero ou positiva")
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
