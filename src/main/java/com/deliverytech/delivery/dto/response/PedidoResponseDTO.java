package com.deliverytech.delivery.dto.response;

import com.deliverytech.delivery.dto.request.ItemPedidoRequestDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PedidoResponseDTO {

    private Long id;
    private String numeroPedido;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private String observacoes;
    private Cliente cliente;
    private Restaurante restaurante;
    private String enderecoEntrega;
    private BigDecimal taxaEntrega;
    List<ItemPedidoRequestDTO> itens;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Cliente cliente, LocalDateTime dataPedido, String enderecoEntrega, Long id,
                             List<ItemPedidoRequestDTO> itens, String numeroPedido, String observacoes,
                             Restaurante restaurante, StatusPedido status, BigDecimal taxaEntrega, BigDecimal valorTotal) {
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.enderecoEntrega = enderecoEntrega;
        this.id = id;
        this.itens = itens;
        this.numeroPedido = numeroPedido;
        this.observacoes = observacoes;
        this.restaurante = restaurante;
        this.status = status;
        this.taxaEntrega = taxaEntrega;
        this.valorTotal = valorTotal;
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

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoResponseDTO that = (PedidoResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PedidoResponseDTO{" +
                "cliente=" + getCliente() +
                ", id=" + getId() +
                ", numeroPedido='" + getNumeroPedido() + '\'' +
                ", dataPedido=" + getDataPedido() +
                ", status='" + getStatus() + '\'' +
                ", valorTotal=" + getValorTotal() +
                ", observacoes='" + getObservacoes() + '\'' +
                ", restaurante=" + getRestaurante() +
                ", enderecoEntrega='" + getEnderecoEntrega() + '\'' +
                ", taxaEntrega=" + getTaxaEntrega() +
                ", itens=" + getItens() +
                '}';
    }
}
