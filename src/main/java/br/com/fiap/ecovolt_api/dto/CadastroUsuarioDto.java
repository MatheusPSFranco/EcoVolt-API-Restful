package br.com.fiap.ecovolt_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CadastroUsuarioDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senhaHash;

    private String status;
    private String tipo;
    private Character ativo;

    @NotNull(message = "O ID do tier é obrigatório")
    @Positive(message = "O ID do tier deve ser positivo")
    private Long idTier;

    @NotNull(message = "O ID do nível é obrigatório")
    @Positive(message = "O ID do nível deve ser positivo")
    private Long idNivel;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Character getAtivo() { return ativo; }
    public void setAtivo(Character ativo) { this.ativo = ativo; }
    public Long getIdTier() { return idTier; }
    public void setIdTier(Long idTier) { this.idTier = idTier; }
    public Long getIdNivel() { return idNivel; }
    public void setIdNivel(Long idNivel) { this.idNivel = idNivel; }
}
