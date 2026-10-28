package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CadastroTierDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "A ordem é obrigatória")
    @Positive(message = "A ordem deve ser positiva")
    private Integer ordem;

    @PositiveOrZero(message = "Usuários ativos mínimos deve ser zero ou positivo")
    private Integer usuariosAtivosMinimos;

    private Double percentualSubida;
    private Double percentualDescida;

    @PositiveOrZero(message = "Limite de usuários deve ser zero ou positivo")
    private Integer limiteUsuarios;

    private String status;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
    public Integer getUsuariosAtivosMinimos() { return usuariosAtivosMinimos; }
    public void setUsuariosAtivosMinimos(Integer usuariosAtivosMinimos) { this.usuariosAtivosMinimos = usuariosAtivosMinimos; }
    public Double getPercentualSubida() { return percentualSubida; }
    public void setPercentualSubida(Double percentualSubida) { this.percentualSubida = percentualSubida; }
    public Double getPercentualDescida() { return percentualDescida; }
    public void setPercentualDescida(Double percentualDescida) { this.percentualDescida = percentualDescida; }
    public Integer getLimiteUsuarios() { return limiteUsuarios; }
    public void setLimiteUsuarios(Integer limiteUsuarios) { this.limiteUsuarios = limiteUsuarios; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
