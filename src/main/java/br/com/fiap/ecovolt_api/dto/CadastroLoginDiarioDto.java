package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CadastroLoginDiarioDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    @PositiveOrZero(message = "O tempo de uso deve ser zero ou positivo")
    private Integer tempoUsoMinutos;

    private Character loginValido;

    @PositiveOrZero(message = "A sequência atual deve ser zero ou positiva")
    private Integer sequenciaAtual;

    @PositiveOrZero(message = "O bônus gerado deve ser zero ou positivo")
    private Integer bonusGerado;

    private Character notificacaoExibida;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Integer getTempoUsoMinutos() { return tempoUsoMinutos; }
    public void setTempoUsoMinutos(Integer tempoUsoMinutos) { this.tempoUsoMinutos = tempoUsoMinutos; }
    public Character getLoginValido() { return loginValido; }
    public void setLoginValido(Character loginValido) { this.loginValido = loginValido; }
    public Integer getSequenciaAtual() { return sequenciaAtual; }
    public void setSequenciaAtual(Integer sequenciaAtual) { this.sequenciaAtual = sequenciaAtual; }
    public Integer getBonusGerado() { return bonusGerado; }
    public void setBonusGerado(Integer bonusGerado) { this.bonusGerado = bonusGerado; }
    public Character getNotificacaoExibida() { return notificacaoExibida; }
    public void setNotificacaoExibida(Character notificacaoExibida) { this.notificacaoExibida = notificacaoExibida; }
}
