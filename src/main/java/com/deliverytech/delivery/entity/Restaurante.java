package com.deliverytech.delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurantes")
@Schema(description = "Entidade que representa um restaurante no sistema")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do restaurante", example = "1")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Schema(description = "Nome do restaurante", example = "Pizza Palace", required = true)
    private String nome;

    @Column(nullable = false)
    @NotBlank(message = "Categoria é obrigatória")
    @Schema(description = "Categoria/ po de culinária do restaurante", example = "Italiana", required = true)
    private String categoria;

    @Column(nullable = false)
    @NotBlank(message = "Endereço é obrigatório")
    @Schema(description = "Endereço completo do restaurante", example = "Rua das Pizzas, 123 - Centro, São Paulo - SP", required = true)
    private String endereco;

    @Column(nullable = false)
    @NotBlank(message = "Telefone é obrigatório")
    @Schema(description = "Telefone de contato do restaurante", example = "(11) 1234-5678", required = true)
    private String telefone;

    private BigDecimal taxaEntrega;

    @Column(precision = 3, scale = 2)
    @Schema(description = "Avaliação média do restaurante (0 a 5 estrelas)", example = "4.5",  minimum = "0", maximum = "5")
    private BigDecimal avaliacao;

    @Column(nullable = false)
    @Schema(description = "Indica se o restaurante está a vo no sistema", example = "true", defaultValue = "true")
    private boolean ativo;

    @Schema(description = "Tempo de entrega do restaurante", example = "45")
    private Integer tempoEntregaMinutos;

    @Schema(description = "Data e hora de criação do registro", example = "2024-06-04T10:30:00")
    private String horarioFuncionamento;

    @Column(name = "data_criacao", nullable = false)
    @Schema(description = "Data e hora de criação do registro", example = "2024-06-04T10:30:00")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Lista de produtos oferecidos pelo restaurante")
    @JsonIgnore
    private List<Produto> produtos;

    @OneToMany(mappedBy = "restaurante")
    @JsonIgnore
    private List<Pedido> pedidos;

    public void inativar() {
        this.ativo = false;
    }

    public Restaurante() {
    }

    public Restaurante(BigDecimal avaliacao, String categoria, String endereco,String nome, BigDecimal taxaEntrega, String telefone) {
        this.ativo = true;
        this.avaliacao = avaliacao;
        this.categoria = categoria;
        this.endereco = endereco;
        this.nome = nome;
        this.taxaEntrega = taxaEntrega;
        this.telefone = telefone;
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
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

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getTempoEntregaMinutos() {
        return tempoEntregaMinutos;
    }

    public void setTempoEntregaMinutos(Integer tempoEntregaMinutos) {
        this.tempoEntregaMinutos = tempoEntregaMinutos;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
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
        Restaurante that = (Restaurante) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "telefone='" + getTelefone() + '\'' +
                ", taxaEntrega=" + getTaxaEntrega() +
                ", nome='" + getNome() + '\'' +
                ", horarioFuncionamento='" + getHorarioFuncionamento() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", id=" + getId() +
                ", categoria='" + getCategoria() + '\'' +
                ", avaliacao=" + getAvaliacao() +
                ", ativo=" + isAtivo()
                ;
    }
}
