package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CadastroEnvioAcaoDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long idUsuario;

    @NotNull(message = "O ID da ação sustentável é obrigatório")
    @Positive(message = "O ID da ação sustentável deve ser positivo")
    private Long idAcaoSustentavel;

    private String tipoEvidencia;
    private String arquivoEvidencia;
    private String status;

    @PositiveOrZero(message = "A pontuação prevista deve ser zero ou positiva")
    private Integer pontuacaoPrevista;

    private String observacaoUsuario;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdAcaoSustentavel() { return idAcaoSustentavel; }
    public void setIdAcaoSustentavel(Long idAcaoSustentavel) { this.idAcaoSustentavel = idAcaoSustentavel; }
    public String getTipoEvidencia() { return tipoEvidencia; }
    public void setTipoEvidencia(String tipoEvidencia) { this.tipoEvidencia = tipoEvidencia; }
    public String getArquivoEvidencia() { return arquivoEvidencia; }
    public void setArquivoEvidencia(String arquivoEvidencia) { this.arquivoEvidencia = arquivoEvidencia; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getPontuacaoPrevista() { return pontuacaoPrevista; }
    public void setPontuacaoPrevista(Integer pontuacaoPrevista) { this.pontuacaoPrevista = pontuacaoPrevista; }
    public String getObservacaoUsuario() { return observacaoUsuario; }
    public void setObservacaoUsuario(String observacaoUsuario) { this.observacaoUsuario = observacaoUsuario; }
}
