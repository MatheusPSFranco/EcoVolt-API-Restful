package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDateTime;

public class DetalhesEnvioAcaoDto {

    private Long id;
    private Long idUsuario;
    private Long idAcaoSustentavel;
    private LocalDateTime dataEnvio;
    private String tipoEvidencia;
    private String arquivoEvidencia;
    private String status;
    private Integer pontuacaoPrevista;
    private String observacaoUsuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdAcaoSustentavel() { return idAcaoSustentavel; }
    public void setIdAcaoSustentavel(Long idAcaoSustentavel) { this.idAcaoSustentavel = idAcaoSustentavel; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
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
