package com.deliverytech.delivery.enums;

public enum StatusPedido {

    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    PREPARANDO("Confirmado"),
    SAIU_PARA_ENTREGA("Saiu para Entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
