package br.com.fiap.ecovolt_api.dto;

public class AtualizacaoUsuarioMissaoDto {

    private String status;
    private Double progresso;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getProgresso() { return progresso; }
    public void setProgresso(Double progresso) { this.progresso = progresso; }
}
