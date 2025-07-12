package com.deliverytech.delivery.dto.response;

import java.math.BigDecimal;
import java.util.Objects;

public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private Boolean disponivel;
    private BigDecimal preco;
    private boolean ativo;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(boolean ativo, String categoria, String descricao,
                              Boolean disponivel, Long id, String nome, BigDecimal preco) {
        this.ativo = ativo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoResponseDTO that = (ProdutoResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProdutoResponseDTO{" +
                "ativo=" + isAtivo() +
                ", id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", descricao='" + getDescricao() + '\'' +
                ", categoria='" + getCategoria() + '\'' +
                ", disponivel=" + getDisponivel() +
                ", preco=" + getPreco() +
                '}';
    }
}
