package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public class AtualizacaoPontuacaoDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    private Long idEnvioAcao;
    private Long idValidacao;
    private Long idRecursoRevisao;
    private Long idMissao;
    private Long idLoginDiario;
    private Long idInteracao;
    private String tipo;

    @PositiveOrZero(message = "Os pontos obtidos devem ser zero ou positivos")
    private Integer pontosObtidos;

    @PositiveOrZero(message = "O XP obtido deve ser zero ou positivo")
    private Integer xpObtido;

    private LocalDate data;
    private String periodoReferencia;
    private String origem;
    private Long idNivel;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdEnvioAcao() { return idEnvioAcao; }
    public void setIdEnvioAcao(Long idEnvioAcao) { this.idEnvioAcao = idEnvioAcao; }
    public Long getIdValidacao() { return idValidacao; }
    public void setIdValidacao(Long idValidacao) { this.idValidacao = idValidacao; }
    public Long getIdRecursoRevisao() { return idRecursoRevisao; }
    public void setIdRecursoRevisao(Long idRecursoRevisao) { this.idRecursoRevisao = idRecursoRevisao; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public Long getIdLoginDiario() { return idLoginDiario; }
    public void setIdLoginDiario(Long idLoginDiario) { this.idLoginDiario = idLoginDiario; }
    public Long getIdInteracao() { return idInteracao; }
    public void setIdInteracao(Long idInteracao) { this.idInteracao = idInteracao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getPontosObtidos() { return pontosObtidos; }
    public void setPontosObtidos(Integer pontosObtidos) { this.pontosObtidos = pontosObtidos; }
    public Integer getXpObtido() { return xpObtido; }
    public void setXpObtido(Integer xpObtido) { this.xpObtido = xpObtido; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getPeriodoReferencia() { return periodoReferencia; }
    public void setPeriodoReferencia(String periodoReferencia) { this.periodoReferencia = periodoReferencia; }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    public Long getIdNivel() { return idNivel; }
    public void setIdNivel(Long idNivel) { this.idNivel = idNivel; }
}
