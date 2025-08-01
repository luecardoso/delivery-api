package com.deliverytech.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Schema(description = "DTO para requisição de itens do pedido",
        title = "Item Pedido Request DTO")
public class ItemPedidoRequestDTO {

    @Schema(description = "ID do produto", example = "1", required = true)
    @NotNull(message = "O produto é obrigatório")
    @Positive(message = "Produto ID deve ser positivo")
    private Long produtoId;

    @Schema(description = "Quantidade do produto no pedido", example = "2", required = true)
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    @Max(value = 99, message = "A quantidade não pode ser maior que 100")
    private Integer quantidade;

    @Size(max = 200, message = "Observações não podem exceder 200 caracteres")
    private String observacoes;

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
