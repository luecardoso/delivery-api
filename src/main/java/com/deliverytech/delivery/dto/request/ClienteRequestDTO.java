package com.deliverytech.delivery.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class ClienteRequest {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    public ClienteRequest() {
    }

    public ClienteRequest(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteRequest that = (ClienteRequest) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail());
    }

    @Override
    public String toString() {
        return "ClienteRequest{" +
                "email='" + getEmail() + '\'' +
                ", nome='" + getNome() + '\'' +
                '}';
    }
}
