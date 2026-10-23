package br.com.fiap.ecovolt_api.model;

public class ContaEnergia {

    private Long id;
    private Usuario usuario;
    private Recompensa recompensa;
    private String nomeTitular;
    private String concessionaria;
    private String numeroInstalacao;
    private String mesReferencia;
    private Double valorConta;
    private Double valorSubsidio;
    private String arquivoComprovante;
    private String statusPagamento;

    //Constructors
    public ContaEnergia(){}

    public ContaEnergia(Long id, Usuario usuario, Recompensa recompensa, String nomeTitular, String concessionaria, String numeroInstalacao, String mesReferencia, Double valorConta, Double valorSubsidio, String arquivoComprovante, String statusPagamento) {
        this.id = id;
        this.usuario = usuario;
        this.recompensa = recompensa;
        this.nomeTitular = nomeTitular;
        this.concessionaria = concessionaria;
        this.numeroInstalacao = numeroInstalacao;
        this.mesReferencia = mesReferencia;
        this.valorConta = valorConta;
        this.valorSubsidio = valorSubsidio;
        this.arquivoComprovante = arquivoComprovante;
        this.statusPagamento = statusPagamento;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getConcessionaria() {
        return concessionaria;
    }

    public void setConcessionaria(String concessionaria) {
        this.concessionaria = concessionaria;
    }

    public String getNumeroInstalacao() {
        return numeroInstalacao;
    }

    public void setNumeroInstalacao(String numeroInstalacao) {
        this.numeroInstalacao = numeroInstalacao;
    }

    public String getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(String mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public Double getValorConta() {
        return valorConta;
    }

    public void setValorConta(Double valorConta) {
        this.valorConta = valorConta;
    }

    public Double getValorSubsidio() {
        return valorSubsidio;
    }

    public void setValorSubsidio(Double valorSubsidio) {
        this.valorSubsidio = valorSubsidio;
    }

    public String getArquivoComprovante() {
        return arquivoComprovante;
    }

    public void setArquivoComprovante(String arquivoComprovante) {
        this.arquivoComprovante = arquivoComprovante;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    //toString
    @Override
    public  String toString(){
        return  "Conta Energia{id= " + id + ", Usuario= " + usuario + ", Recompensa= " + recompensa + ", Nome Titular= " + nomeTitular +
                ", Conceissionaria= " + concessionaria  + ", Numero Intalação= " + numeroInstalacao +
                ", Mes Referencia= " + mesReferencia + ", Valor Da Conta= " + valorConta +
                ", Valor Do Subsidio= " + valorSubsidio + ", Status Pagamento= " + statusPagamento +  "}";
    }
}
