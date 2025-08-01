package com.deliverytech.delivery.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produtos")
@Schema(description = "Entidade que representa um produto no sistema")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do restaurante", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Nome do produto", example = "Pizza Margherita", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @Schema(description = "Descrição do produto", example = "Deliciosa pizza com molho de tomate, mussarela e manjericão",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String descricao;

    @Schema(description = "Preço do produto", example = "29.90", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal preco;

    @Schema(description = "Categoria do produto", example = "Pizzas",requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoria;

    @Schema(description = "Disponibilidade do produto", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean disponivel;

    @Schema(description = "Estoque do produto", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer estoque;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    @Schema(description = "Restaurante vinculado ao produto", example = "Pizzaria Bella", requiredMode = Schema.RequiredMode.REQUIRED)
    private Restaurante restaurante;

    public Produto() {
    }

    public Produto(String categoria, String descricao, boolean disponivel, String nome, BigDecimal preco, Restaurante restaurante) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.nome = nome;
        this.preco = preco;
        this.restaurante = restaurante;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean atico) {
        this.disponivel = atico;
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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(getId(), produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produto{" +
                "categoria='" + getCategoria() + '\'' +
                ", id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", descricao='" + getDescricao() + '\'' +
                ", preco=" + getPreco() +
                ", disponivel=" + getDisponivel() +
                ", restaurante=" + getRestaurante() +
                '}';
    }
}
