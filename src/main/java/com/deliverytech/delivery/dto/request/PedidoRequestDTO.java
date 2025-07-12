package com.deliverytech.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PedidoRequestDTO {

    @NotNull(message = "O número do pedido é obrigatório")
    private String numeroPedido;

    @NotNull(message = "A data do pedido é obrigatória")
    private String dataPedido;

    @NotNull(message = "O valor do pedido é obrigatório")
    private BigDecimal valorTotal;

    private String observacoes;

    @NotNull(message = "O status do pedido é obrigatório")
    private Long clienteId;

    @NotNull(message = "O restaurante é obrigatório")
    private Long restauranteId;

    private String enderecoEntrega;

    @NotNull(message = "Os itens são obrigatórios")
    private List<ItemPedidoRequestDTO> itens;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(Long clienteId, String dataPedido, String enderecoEntrega, List<ItemPedidoRequestDTO> itens,
                            String numeroPedido, String observacoes, Long restauranteId, BigDecimal valorTotal) {
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
        this.enderecoEntrega = enderecoEntrega;
        this.itens = itens;
        this.numeroPedido = numeroPedido;
        this.observacoes = observacoes;
        this.restauranteId = restauranteId;
        this.valorTotal = valorTotal;
    }

    public @NotNull(message = "O status do pedido é obrigatório") Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(@NotNull(message = "O status do pedido é obrigatório") Long clienteId) {
        this.clienteId = clienteId;
    }

    public @NotNull(message = "A data do pedido é obrigatória") String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(@NotNull(message = "A data do pedido é obrigatória") String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public @NotNull(message = "Os itens são obrigatórios") List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(@NotNull(message = "Os itens são obrigatórios") List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public @NotNull(message = "O número do pedido é obrigatório") String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(@NotNull(message = "O número do pedido é obrigatório") String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public @NotNull(message = "O restaurante é obrigatório") Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(@NotNull(message = "O restaurante é obrigatório") Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public @NotNull(message = "O valor do pedido é obrigatório") BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(@NotNull(message = "O valor do pedido é obrigatório") BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoRequestDTO that = (PedidoRequestDTO) o;
        return Objects.equals(numeroPedido, that.numeroPedido) && Objects.equals(clienteId, that.clienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroPedido, clienteId);
    }

    @Override
    public String toString() {
        return "PedidoRequestDTO{" +
                "clienteId=" + getClienteId() +
                ", numeroPedido='" + getNumeroPedido() + '\'' +
                ", dataPedido='" + getDataPedido() + '\'' +
                ", valorTotal=" + getValorTotal() +
                ", observacoes='" + getObservacoes() + '\'' +
                ", restauranteId=" + getRestauranteId() +
                ", enderecoEntrega='" + getEnderecoEntrega() + '\'' +
                ", itens=" + getItens() +
                '}';
    }
}
