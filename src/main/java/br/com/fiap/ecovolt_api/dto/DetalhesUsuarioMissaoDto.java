package br.com.fiap.ecovolt_api.dto;

import java.time.LocalDate;

public class DetalhesUsuarioMissaoDto {

    private Long idUsuario;
    private Long idMissao;
    private LocalDate dataInicio;
    private LocalDate dataConclusao;
    private String status;
    private Double progresso;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getProgresso() { return progresso; }
    public void setProgresso(Double progresso) { this.progresso = progresso; }
}
