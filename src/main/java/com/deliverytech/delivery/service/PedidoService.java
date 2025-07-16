package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.request.ItemPedidoRequestDTO;
import com.deliverytech.delivery.dto.request.PedidoRequestDTO;
import com.deliverytech.delivery.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO);

    PedidoResponseDTO buscarPedidoPorId(Long id);

    PedidoResponseDTO buscarPedidoPorNumero(String numero);

    List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId);

    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);

    //    List<PedidoResponseDTO> buscarPedidosPorRestaurante(Long restauranteId);
    List<PedidoResponseDTO> buscarPedidosPorRestaurante(Long restauranteId, StatusPedido status);

    BigDecimal calcularTotalPedido(List<ItemPedidoRequestDTO> itens);
//    BigDecimal calcularTotalPedido(PedidoRequestDTO calculoPedidoRequestDTO);

    PedidoResponseDTO cancelarPedido(Long id);

    Page<PedidoResponseDTO> listarPedidosComPaginacao(StatusPedido status, LocalDate dataInicio,
                                                      LocalDate dataFim, Pageable pageable);
}
