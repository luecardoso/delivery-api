package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.request.ItemPedidoRequestDTO;
import com.deliverytech.delivery.dto.request.PedidoRequestDTO;
import com.deliverytech.delivery.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO);
    PedidoResponseDTO buscarPedidoPorId(Long id);
    PedidoResponseDTO buscarPedidoPorNumero(String numero);
    List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId);
    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);
    List<PedidoResponseDTO> buscarPedidosPorRestaurante(Long restauranteId);
    BigDecimal calcularTotalPedido(List<ItemPedidoRequestDTO> itens);
    PedidoResponseDTO cancelarPedido(Long id);
}
