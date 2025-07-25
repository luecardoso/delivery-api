package com.deliverytech.delivery.dto.request;

import com.deliverytech.delivery.validation.ValidCEP;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Schema(description = "DTO para requisição de pedidos", title = "Pedido Request DTO")
public class PedidoRequestDTO {

    @Schema(description = "Número do pedido", example = "12345", required = true)
    @NotNull(message = "O número do pedido é obrigatório")
    private String numeroPedido;

    @Schema(description = "Data do pedido", example = "2023-10-01", required = true)
    @NotNull(message = "A data do pedido é obrigatória")
    private String dataPedido;

    @Schema(description = "Valor total do pedido", example = "99.99", required = true)
    @NotNull(message = "O valor do pedido é obrigatório")
    private BigDecimal valorTotal;

    @Schema(description = "Observações do pedido", example = "Não colocar cebola")
    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    @Schema(description = "ID do Cliente", example = "1", required = true)
    @NotNull(message = "O id do cliente é obrigatório")
    @Positive(message = "Cliente ID deve ser positivo")
    private Long clienteId;

    @Schema(description = "ID do restaurante", example = "1", required = true)
    @NotNull(message = "O restaurante é obrigatório")
    @Positive(message = "Restaurante ID deve ser positivo")
    private Long restauranteId;

    @Schema(description = "Endereço de entrega do pedido", example = "Rua das Flores, 123")
    @Size(max = 200, message = "Endereço não pode exceder 200 caracteres")
    private String enderecoEntrega;

    @Schema(description = "Lista de itens do pedido", required = true)
    @NotNull(message = "Os itens são obrigatórios")
    @NotEmpty(message = "Lista de itens não pode estar vazia")
    @Valid
    private List<ItemPedidoRequestDTO> itens;

    @NotBlank(message = "CEP é obrigatório")
    @ValidCEP
    private String cep;

    @NotBlank(message = "Forma de pagamento é obrigatória")
    @Pattern(regexp = "^(DINHEIRO|CARTAO_CREDITO|CARTAO_DEBITO|PIX)$",
            message = "Forma de pagamento deve ser: DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO ou PIX")
    private String formaPagamento;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(String cep, Long clienteId, String dataPedido, String enderecoEntrega, String formaPagamento,
                            List<ItemPedidoRequestDTO> itens, String numeroPedido, String observacoes,
                            Long restauranteId, BigDecimal valorTotal) {
        this.cep = cep;
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
        this.enderecoEntrega = enderecoEntrega;
        this.formaPagamento = formaPagamento;
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

    public @NotBlank(message = "CEP é obrigatório") String getCep() {
        return cep;
    }

    public void setCep(@NotBlank(message = "CEP é obrigatório") String cep) {
        this.cep = cep;
    }

    public @NotBlank(message = "Forma de pagamento é obrigatória") @Pattern(regexp = "^(DINHEIRO|CARTAO_CREDITO|CARTAO_DEBITO|PIX)$",
            message = "Forma de pagamento deve ser: DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO ou PIX") String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(@NotBlank(message = "Forma de pagamento é obrigatória") @Pattern(regexp = "^(DINHEIRO|CARTAO_CREDITO|CARTAO_DEBITO|PIX)$",
            message = "Forma de pagamento deve ser: DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO ou PIX") String formaPagamento) {
        this.formaPagamento = formaPagamento;
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
