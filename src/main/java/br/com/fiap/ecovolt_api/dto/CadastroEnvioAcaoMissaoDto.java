package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CadastroEnvioAcaoMissaoDto {

    @NotNull(message = "O ID do envio de ação é obrigatório")
    @Positive(message = "O ID do envio de ação deve ser positivo")
    private Long idEnvioAcao;

    @NotNull(message = "O ID da missão é obrigatório")
    @Positive(message = "O ID da missão deve ser positivo")
    private Long idMissao;

    private String contabilizado;

    public Long getIdEnvioAcao() { return idEnvioAcao; }
    public void setIdEnvioAcao(Long idEnvioAcao) { this.idEnvioAcao = idEnvioAcao; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public String getContabilizado() { return contabilizado; }
    public void setContabilizado(String contabilizado) { this.contabilizado = contabilizado; }
}
