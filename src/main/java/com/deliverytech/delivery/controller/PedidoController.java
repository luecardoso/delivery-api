package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.ItemPedidoRequestDTO;
import com.deliverytech.delivery.dto.request.PedidoRequestDTO;
import com.deliverytech.delivery.dto.response.*;
import com.deliverytech.delivery.enums.StatusPedido;
import com.deliverytech.delivery.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "Operações relacionadas aos pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Criar pedido",
            description = "Cria um novo pedido no sistema",
            security = @SecurityRequirement(name = "Bearer Authen ca on"),
            tags = {"Pedidos"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Pedido já existe")
    })
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> criarPedido(@Parameter(description = "Dados do pedido a ser criado")
                                                                             @Valid @RequestBody
                                                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                                     description = "Dados do pedido a ser criado"
                                                                             )
                                                                             PedidoRequestDTO dto) {
        PedidoResponseDTO pedido = pedidoService.criarPedido(dto);
        ApiResponseWrapper<PedidoResponseDTO> response =
                new ApiResponseWrapper<>(true, pedido, "Produto criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID",
            description = "Recupera os detalhes de um pedido específico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> buscarPedidoPorId(@Parameter(description = "ID do pedido")
                                                                                   @PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        ApiResponseWrapper<PedidoResponseDTO> response =
                new ApiResponseWrapper<>(true, pedido, "Produto criado com sucesso");
        return ResponseEntity.ok(response);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar pedidos",
            description = "Lista pedidos com Filtros opcionais e paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    public ResponseEntity<PagedResponseWrapper<PedidoResponseDTO>> listarPedidosComPaginacao(@Parameter(description = "Status do pedido")
                                                                                             @RequestParam(required = false) StatusPedido status,
                                                                                             @Parameter(description = "Data inicial")
                                                                                             @RequestParam(required = false)
                                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                                                                                             @Parameter(description = "Data final")
                                                                                             @RequestParam(required = false)
                                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
                                                                                             @Parameter(description = "Parâmetros de paginação")
                                                                                             Pageable pageable) {
        Page<PedidoResponseDTO> pedidos = pedidoService.listarPedidosComPaginacao(status, dataInicio, dataFim, pageable);
        PagedResponseWrapper<PedidoResponseDTO> response = new PagedResponseWrapper<>(pedidos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clientes/{clienteId}/pedidos")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Listar pedidos por cliente",
            description = "Lista todos os pedidos de um cliente específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<List<PedidoResponseDTO>>> buscarPedidosPorCliente(@Parameter(description = "Id do pedido")
                                                                                               @PathVariable Long clienteId) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorCliente(clienteId);
        ApiResponseWrapper<List<PedidoResponseDTO>> response =
                new ApiResponseWrapper<>(true, pedidos, "Histórico recuperado com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/numero/{numeroPedido}")
    @Operation(summary = "Listar pedidos por numero",
            description = "Lista todos os pedidos pelo numero do pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> buscarPedidoPorNumero(@PathVariable String numeroPedido) {

        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorNumero(numeroPedido);
        ApiResponseWrapper<PedidoResponseDTO> response =
                new ApiResponseWrapper<>(true, pedido, "Produto criado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('RESTAURANTE') or hasRole('ADMIN')")
    @Operation(summary = "Atualizar status do pedido",
            description = "Atualiza o status de um pedido específico pelo ID",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            tags = {"Pedidos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Transição do status inválida")
    })
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> atualizarStatus(@Parameter(description = "Id do pedido")
                                                                                 @PathVariable Long id,
                                                                                 @Valid @RequestBody StatusPedido status) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.atualizarStatusPedido(id, status);
        ApiResponseWrapper<PedidoResponseDTO> response =
                new ApiResponseWrapper<>(true, pedidoResponseDTO, "Status atualizado com sucesso");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido",
            description = "Cancela um pedido pelo ID se possivel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser cancelado")
    })
    public ResponseEntity<Void> cancelarPedido(@Parameter(description = "Id do pedido") @PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calcular")
    @Operation(summary = "Calcular valor total do pedido",
            description = "Calcula o valor total de um pedido com base nos itens fornecidos sem salvar")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Valor total calculado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<BigDecimal> calcularValorTotalPedido(@Valid @RequestBody List<ItemPedidoRequestDTO> itens) {
        BigDecimal valorTotal = pedidoService.calcularTotalPedido(itens);
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/restaurante/{restauranteId}")
    @PreAuthorize("hasRole('RESTAURANTE')")
    @Operation(summary = "Pedidos do restaurante",
            description = "Lista todos os pedidos de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos recuperados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<List<PedidoResponseDTO>>> buscarPorRestaurante(@Parameter(description = "ID do restaurante")
                                                                                            @PathVariable Long restauranteId,
                                                                                            @Parameter(description = "Status do pedido")
                                                                                            @RequestParam(required = false) StatusPedido status) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorRestaurante(restauranteId, status);
        ApiResponseWrapper<List<PedidoResponseDTO>> response =
                new ApiResponseWrapper<>(true, pedidos, "Pedidos recuperados com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meus")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Listar meus pedidos",
            description = "Retorna os pedidos do cliente autenticado",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            tags = {"Pedidos"})
    public ResponseEntity<PagedResponseWrapper<PedidoResponseDTO>> listarMeusPedidos(@Parameter(description = "Informações de paginação")
                                                                     Pageable pageable) {
        Page<PedidoResponseDTO> pedidos = pedidoService.listarPorCliente(pageable);
        PagedResponseWrapper<PedidoResponseDTO> response = new PagedResponseWrapper<>(pedidos);
        return ResponseEntity.ok(response);
    }
}

