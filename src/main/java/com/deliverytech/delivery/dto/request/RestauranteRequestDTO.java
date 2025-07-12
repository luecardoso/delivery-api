package com.deliverytech.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class RestauranteRequestDTO {

    @NotNull(message = "O nome do restaurante é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do restaurante deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "A categoria do restaurante é obrigatória")
    private String categoria;

    @NotNull(message = "O endereço do restaurante é obrigatório")
    private String endereco;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$",
            message = "O telefone deve ser um número válido com 10 a 15 dígitos, podendo iniciar com '+'")
    private String telefone;

    @NotNull(message = "A taxa de entrega do restaurante é obrigatória")
    @DecimalMin("0.0")
    private BigDecimal taxaEntrega;

    private BigDecimal avaliacao;

    @NotNull(message = "O status do restaurante é obrigatório")
    private Boolean ativo = true;

    @Min(10)
    @Max(120)
    private Integer tempoEntregaMinutos;

    public RestauranteRequestDTO() {
    }

    public RestauranteRequestDTO(Boolean ativo, BigDecimal avaliacao, String categoria, String endereco,
                                 String nome, BigDecimal taxaEntrega, String telefone, Integer tempoEntregaMinutos) {
        this.ativo = ativo;
        this.avaliacao = avaliacao;
        this.categoria = categoria;
        this.endereco = endereco;
        this.nome = nome;
        this.taxaEntrega = taxaEntrega;
        this.telefone = telefone;
        this.tempoEntregaMinutos = tempoEntregaMinutos;
    }

    public @NotNull(message = "O status do restaurante é obrigatório") Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(@NotNull(message = "O status do restaurante é obrigatório") Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        this.avaliacao = avaliacao;
    }

    public @NotNull(message = "A categoria do restaurante é obrigatória") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotNull(message = "A categoria do restaurante é obrigatória") String categoria) {
        this.categoria = categoria;
    }

    public @NotNull(message = "O endereço do restaurante é obrigatório") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotNull(message = "O endereço do restaurante é obrigatório") String endereco) {
        this.endereco = endereco;
    }

    public @NotNull(message = "O nome do restaurante é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do restaurante deve ter entre 3 e 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotNull(message = "O nome do restaurante é obrigatório")
                        @Size(min = 3, max = 100, message = "O nome do restaurante deve ter entre 3 e 100 caracteres")
                        String nome) {
        this.nome = nome;
    }

    public @NotNull(message = "A taxa de entrega do restaurante é obrigatória")
                    @DecimalMin("0.0") BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(@NotNull(message = "A taxa de entrega do restaurante é obrigatória")
                               @DecimalMin("0.0") BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
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

    public @Min(10) @Max(120) Integer getTempoEntregaMinutos() {
        return tempoEntregaMinutos;
    }

    public void setTempoEntregaMinutos(@Min(10) @Max(120) Integer tempoEntregaMinutos) {
        this.tempoEntregaMinutos = tempoEntregaMinutos;
    }


    @Override
    public String toString() {
        return "RestauranteRequestDTO{" +
                "ativo=" + getAtivo() +
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
