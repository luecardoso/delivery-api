package com.deliverytech.delivery.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    public void inativar(){
        this.ativo = false;
    }

    public Cliente() {
    }

    public Cliente(boolean ativo, String email, String endereco, Long id, String nome, List<Pedido> pedidos, String telefone) {
        this.ativo = ativo;
        this.email = email;
        this.endereco = endereco;
        this.id = id;
        this.nome = nome;
        this.pedidos = pedidos;
        this.telefone = telefone;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + getNome() + '\'' +
                ", Telefone='" + getTelefone() + '\'' +
                ", id=" + getId() +
                ", endereco='" + getEndereco() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", ativo=" + isAtivo() +
                '}';
    }
}
