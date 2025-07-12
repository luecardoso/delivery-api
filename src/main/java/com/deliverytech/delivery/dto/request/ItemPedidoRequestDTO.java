package com.deliverytech.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ItemPedidoRequestDTO {

    @NotNull(message = "O produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    @Max(value = 10, message = "A quantidade não pode ser maior que 100")
    private Integer quantidade;

    public ItemPedidoRequestDTO() {
    }

    public ItemPedidoRequestDTO(Long produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public @NotNull(message = "O produto é obrigatório") Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(@NotNull(message = "O produto é obrigatório") Long produtoId) {
        this.produtoId = produtoId;
    }

    public @NotNull(message = "A quantidade é obrigatória") @Min(value = 1,
            message = "A quantidade deve ser pelo menos 1") @Max(value = 10,
            message = "A quantidade não pode ser maior que 100") Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(@NotNull(message = "A quantidade é obrigatória")
                              @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
                              @Max(value = 10, message = "A quantidade não pode ser maior que 100") Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoRequestDTO that = (ItemPedidoRequestDTO) o;
        return Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(produtoId);
    }

    @Override
    public String toString() {
        return "ItemPedidoRequestDTO{" +
                "produtoId=" + getProdutoId() +
                ", quantidade=" + getQuantidade() +
                '}';
    }
}
