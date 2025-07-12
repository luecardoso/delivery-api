package com.deliverytech.delivery.dto.response;

import java.util.Objects;

public class ClienteResponse {

    private Long id;
    private String nome;
    private String email;
    private Boolean ativo;

    public ClienteResponse() {
    }

    public ClienteResponse(Boolean ativo, String email, Long id, String nome) {
        this.ativo = ativo;
        this.email = email;
        this.id = id;
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteResponse that = (ClienteResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }

    @Override
    public String toString() {
        return "ClienteResponse{" +
                "ativo=" + getAtivo() +
                ", id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
