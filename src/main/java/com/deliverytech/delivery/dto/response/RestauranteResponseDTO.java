package com.deliverytech.delivery.dto.response;

import java.math.BigDecimal;
import java.util.Objects;

public class RestauranteResponseDTO {

    private Long id;
    private String nome;
    private String categoria;
    private String endereco;
    private String telefone;
    private BigDecimal taxaEntrega;
    private BigDecimal avaliacao;
    private Boolean ativo;
    private Integer tempoEntregaMinutos;

    public RestauranteResponseDTO() {
    }

    public RestauranteResponseDTO(Integer tempoEntregaMinutos, String telefone, BigDecimal taxaEntrega, String nome,
                                  Long id, String endereco, String categoria, BigDecimal avaliacao, Boolean ativo) {
        this.tempoEntregaMinutos = tempoEntregaMinutos;
        this.telefone = telefone;
        this.taxaEntrega = taxaEntrega;
        this.nome = nome;
        this.id = id;
        this.endereco = endereco;
        this.categoria = categoria;
        this.avaliacao = avaliacao;
        this.ativo = ativo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getTempoEntregaMinutos() {
        return tempoEntregaMinutos;
    }

    public void setTempoEntregaMinutos(Integer tempoEntregaMinutos) {
        this.tempoEntregaMinutos = tempoEntregaMinutos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestauranteResponseDTO that = (RestauranteResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RestauranteResponseDTO{" +
                "ativo=" + getAtivo() +
                ", id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", categoria='" + getCategoria() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", taxaEntrega=" + getTaxaEntrega() +
                ", avaliacao=" + getAvaliacao() +
                ", tempoEntregaMinutos=" + getTempoEntregaMinutos() +
                '}';
    }
}
