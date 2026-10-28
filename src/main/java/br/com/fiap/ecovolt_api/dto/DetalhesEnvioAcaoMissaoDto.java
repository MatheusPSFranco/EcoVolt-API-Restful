package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDateTime;

public class DetalhesEnvioAcaoMissaoDto {

    private Long idEnvioAcao;
    private Long idMissao;
    private String contabilizado;
    private LocalDateTime dataContabilizacao;

    public Long getIdEnvioAcao() { return idEnvioAcao; }
    public void setIdEnvioAcao(Long idEnvioAcao) { this.idEnvioAcao = idEnvioAcao; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public String getContabilizado() { return contabilizado; }
    public void setContabilizado(String contabilizado) { this.contabilizado = contabilizado; }
    public LocalDateTime getDataContabilizacao() { return dataContabilizacao; }
    public void setDataContabilizacao(LocalDateTime dataContabilizacao) { this.dataContabilizacao = dataContabilizacao; }
}
