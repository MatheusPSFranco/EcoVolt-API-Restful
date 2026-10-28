package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CadastroOdsMissaoDto {

    @NotNull(message = "O ID da ODS é obrigatório")
    @Positive(message = "O ID da ODS deve ser positivo")
    private Long idOds;

    @NotNull(message = "O ID da missão é obrigatório")
    @Positive(message = "O ID da missão deve ser positivo")
    private Long idMissao;

    private Double pesoOds;

    public Long getIdOds() { return idOds; }
    public void setIdOds(Long idOds) { this.idOds = idOds; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public Double getPesoOds() { return pesoOds; }
    public void setPesoOds(Double pesoOds) { this.pesoOds = pesoOds; }
}
