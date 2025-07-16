package com.deliverytech.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Schema(description = "DTO para requisição de criação ou atualização de cliente",
        title = "Cliente Request DTO")
public class ClienteRequestDTO {

    @Schema(description = "Nome do cliente", example = "João da Silva", required = true)
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "Email do cliente", example = "email@email.com", required = true)
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @Schema(description = "Telefone do cliente", example = "+5511999999999", required = true)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$",
            message = "O telefone deve ser um número válido com 10 a 15 dígitos, podendo iniciar com '+'")
    private String telefone;

    @Schema(description = "Endereço do cliente", example = "Rua das Flores, 123", required = true)
    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String email, String endereco, String nome, String telefone) {
        this.email = email;
        this.endereco = endereco;
        this.nome = nome;
        this.telefone = telefone;
    }

    public @NotBlank(message = "O email é obrigatório")
                        @Email(message = "O email deve ser válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "O email é obrigatório")
                         @Email(message = "O email deve ser válido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "O endereço é obrigatório") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank(message = "O endereço é obrigatório") String endereco) {
        this.endereco = endereco;
    }

    public @NotBlank(message = "O nome é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome é obrigatório") String nome) {
        this.nome = nome;
    }

    public @Pattern(regexp = "^\\+?[0-9]{10,15}$",
                    message = "O telefone deve ser um número válido com 10 a 15 dígitos, podendo iniciar com '+'")
                    String getTelefone() {
        return telefone;
    }

    public void setTelefone(@Pattern(regexp = "^\\+?[0-9]{10,15}$",
                            message = "O telefone deve ser um número válido com 10 a 15 dígitos, podendo iniciar com '+'")
                            String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteRequestDTO that = (ClienteRequestDTO) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail());
    }

    @Override
    public String toString() {
        return "ClienteRequestDTO{" +
                "email='" + getEmail() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                '}';
    }
}
