package com.deliverytech.delivery.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis de um pedido no sistema")
public enum StatusPedido {

    @Schema(description = "Pedido foi criado mas ainda não foi confirmado pelo restaurante")
    PENDENTE("Aguardando confirmação do restaurante"),

    @Schema(description = "Pedido foi confirmado pelo restaurante e está sendo preparado")
    CONFIRMADO("Confirmado pelo restaurante"),

    @Schema(description = "Pedido está sendo preparado na cozinha")
    PREPARANDO("Em preparação"),

    @Schema(description = "Pedido está pronto e aguardando entregador")
    PRONTO("Pronto para entrega"),

    @Schema(description = "Pedido saiu para entrega")
    SAIU_PARA_ENTREGA("Saiu para Entrega"),

    @Schema(description = "Pedido foi entregue com sucesso ao cliente")
    ENTREGUE("Entregue"),

    @Schema(description = "Pedido foi cancelado pelo cliente ou restaurante")
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    @Schema(description = "Descrição legível do status")
    public String getDescricao() {
        return descricao;
    }
}
