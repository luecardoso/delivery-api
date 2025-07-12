package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.ItemPedidoRequestDTO;
import com.deliverytech.delivery.dto.request.PedidoRequestDTO;
import com.deliverytech.delivery.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery.enums.StatusPedido;
import com.deliverytech.delivery.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Criar novo pedido
     */
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO pedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    /**
     * Buscar pedido por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Listar pedidos por cliente
     */
    @GetMapping("/clientes/{clienteId}/pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidosPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Buscar pedido por número
     */
    @GetMapping("/numero/{numeroPedido}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorNumero(@PathVariable String numeroPedido) {

        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorNumero(numeroPedido);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Atualizar status do pedido
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id,
                                             @RequestParam StatusPedido status) {
        PedidoResponseDTO dto = pedidoService.atualizarStatusPedido(id, status);
        return ResponseEntity.ok(dto);
    }

    /**
     * Cancelar pedido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.cancelarPedido(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularValorTotalPedido(@RequestBody List<ItemPedidoRequestDTO> itens) {
        BigDecimal valorTotal = pedidoService.calcularTotalPedido(itens);
        return ResponseEntity.ok(valorTotal);
    }
}

