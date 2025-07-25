package com.deliverytech.delivery.entity;

import com.deliverytech.delivery.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedidos")
@Schema(description = "Entidade que representa um pedido no sistema")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do restaurante", example = "1")
    private Long id;

    @Schema(description = "Data do pedido", example = "2023-10-01", required = true)
    private LocalDateTime dataPedido;

    @Schema(description = "Endereço de entrega do pedido", example = "Rua das Flores, 123")
    private String enderecoEntrega;

    @Schema(description = "Subtotal do pedido", example = "99.99", required = true)
    private BigDecimal subtotal;

    @Schema(description = "Taxa de entrega do pedido", example = "5.0", required = true)
    private BigDecimal taxaEntrega;

    @Schema(description = "Valor total do pedido", example = "99.99", required = true)
    private BigDecimal valorTotal;

    @Schema(description = "Observações do pedido", example = "Não colocar cebola")
    private String observacoes;

    @Schema(description = "Número do pedido", example = "12345", required = true)
    private String numeroPedido;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status do pedido", example = "ENTREGUE")
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido itemPedido){
        itens.add(itemPedido);
    }

    public void confirmar(){

    }

    public Pedido(String numeroPedido,BigDecimal valorTotal, BigDecimal taxaEntrega, BigDecimal subtotal, StatusPedido status, Restaurante restaurante, List<ItemPedido> itens, String enderecoEntrega, LocalDateTime dataPedido, Cliente cliente) {
        this.numeroPedido = numeroPedido;
        this.valorTotal = valorTotal;
        this.taxaEntrega = taxaEntrega;
        this.subtotal = subtotal;
        this.status = status;
        this.restaurante = restaurante;
        this.itens = itens;
        this.enderecoEntrega = enderecoEntrega;
        this.dataPedido = dataPedido;
        this.cliente = cliente;
    }

    public Pedido() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "cliente=" + getCliente() +
                ", dataPedido=" + getDataPedido() +
                ", enderecoEntrega='" + getEnderecoEntrega() + '\'' +
                ", numeroPedido='" + getNumeroPedido() + '\'' +
                ", observacoes='" + getObservacoes() + '\'' +
                ", restaurante=" + getRestaurante() +
                ", subtotal=" + getSubtotal() +
                ", taxaEntrega=" + getTaxaEntrega() +
                ", valorTotal=" + getValorTotal() +
                '}';
    }
}
