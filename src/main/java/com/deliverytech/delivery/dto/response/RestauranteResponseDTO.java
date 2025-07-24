package com.deliverytech.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Schema(description = "Dados de resposta de um restaurante")
public class RestauranteResponseDTO {

    @Schema(description = "Identificador único do restaurante", example = "1")
    private Long id;

    @Schema(description = "Nome do restaurante", example = "Pizza Palace")
    private String nome;

    @Schema(description = "Categoria/tipo de culinária do restaurante", example = "Italiana")
    private String categoria;

    @Schema(description = "Endereço completo do restaurante",
            example = "Rua das Pizzas, 123 - Centro, São Paulo - SP")
    private String endereco;

    @Schema(description = "Telefone de contato do restaurante", example = "(11) 1234-5678")
    private String telefone;

    private BigDecimal taxaEntrega;

    @Schema(description = "Avaliação média do restaurante (0 a 5 estrelas)",
            example = "4.5",
            minimum = "0",
            maximum = "5")
    private BigDecimal avaliacao;

    @Schema(description = "Indica se o restaurante está ativo no sistema", example = "true")
    private Boolean ativo;

    private Integer tempoEntregaMinutos;

    @Schema(description = "Data e hora de criação do registro", example = "2024-06-04T10:30:00")
    private LocalDateTime dataCriacao;


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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
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
