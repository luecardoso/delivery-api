package com.deliverytech.delivery.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "itens_pedido")
@Schema(description = "Entidade que representa itens de um pedido no sistema")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do item", example = "1")
    private Long id;

    @Schema(description = "Quantidade do produto no pedido", example = "2", required = true)
    private int quantidade;

    @Schema(description = "Preço unitário de um item", example = "10.90", required = true)
    private BigDecimal precoUnitario;

    @Schema(description = "Subtotal do item", example = "10.90", required = true)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public ItemPedido() {
    }

    public ItemPedido(BigDecimal precoUnitario, Produto produto, int quantidade, BigDecimal subtotal) {
        this.precoUnitario = precoUnitario;
        this.produto = produto;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void calcularSubTotal(){
        this.subtotal = this.getPrecoUnitario().multiply(BigDecimal.valueOf(this.getQuantidade()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + getId() +
                ", quantidade=" + getQuantidade() +
                ", precoUnitario=" + getPrecoUnitario() +
                ", subtotal=" + getSubtotal() +
                ", pedido=" + getPedido() +
                ", produto=" + getProduto() +
                '}';
    }
}
