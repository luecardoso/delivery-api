package com.deliverytech.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProdutoRequestDTO {

    @NotNull(message = "O Nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "A descrição do produto é obrigatória")
    private String descricao;

    @NotNull(message = "O preço do produto é obrigatório")
    private BigDecimal preco;

    @NotNull(message = "A categoria do produto é obrigatória")
    private String categoria;

    @NotNull(message = "A disponibilidade do produto é obrigatória")
    private Boolean disponivel;

    @NotNull(message = "O restaurante é obrigatório")
    private Long restauranteId;

    public ProdutoRequestDTO() {
    }

    public ProdutoRequestDTO(String categoria, String descricao, Boolean disponivel, String nome, BigDecimal preco, Long restauranteId) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.nome = nome;
        this.preco = preco;
        this.restauranteId = restauranteId;
    }

    public @NotNull(message = "A categoria do produto é obrigatória") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotNull(message = "A categoria do produto é obrigatória") String categoria) {
        this.categoria = categoria;
    }

    public @NotNull(message = "A descrição do produto é obrigatória") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull(message = "A descrição do produto é obrigatória") String descricao) {
        this.descricao = descricao;
    }

    public @NotNull(message = "A disponibilidade do produto é obrigatória") Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(@NotNull(message = "A disponibilidade do produto é obrigatória") Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public @NotNull(message = "O Nome do produto é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@NotNull(message = "O Nome do produto é obrigatório") String nome) {
        this.nome = nome;
    }

    public @NotNull(message = "O preço do produto é obrigatório") BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(@NotNull(message = "O preço do produto é obrigatório") BigDecimal preco) {
        this.preco = preco;
    }

    public @NotNull(message = "O restaurante é obrigatório") Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(@NotNull(message = "O restaurante é obrigatório") Long restauranteId) {
        this.restauranteId = restauranteId;
    }


}
