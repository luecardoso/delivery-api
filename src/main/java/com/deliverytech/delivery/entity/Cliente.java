package com.deliverytech.delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clientes")
@Schema(description = "Entidade que representa um cliente no sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do restaurante", example = "1")
    private Long id;

    @Schema(description = "Nome do cliente", example = "João da Silva", required = true)
    private String nome;

    @Schema(description = "Email do cliente", example = "email@email.com", required = true)
    private String email;

    @Schema(description = "Telefone do cliente", example = "+5511999999999", required = true)
    private String telefone;

    @Schema(description = "Endereço do cliente", example = "Rua das Flores, 123", required = true)
    private String endereco;

    @Column(nullable = true)
    @Schema(description = "Acesso do cliente ao sistema", example = "true", required = true)
    private boolean ativo;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Pedido> pedidos;

    public void inativar(){
        this.ativo = false;
    }

    public Cliente() {
    }

    public Cliente(String nome,String email, String endereco, String telefone, LocalDateTime dataCadastro) {
        this.ativo = true;
        this.email = email;
        this.endereco = endereco;
        this.nome = nome;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
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
