package com.deliverytech.delivery.repository.projection;

import java.math.BigDecimal;

// Interface de Projeção
public interface RelatorioVendas {
    String getNomeRestaurante();
    BigDecimal getTotalVendas();
    Long getQuantidePedidos();
}
