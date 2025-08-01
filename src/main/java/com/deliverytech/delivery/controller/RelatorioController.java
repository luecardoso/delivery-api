package com.deliverytech.delivery.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
@Tag(name = "Relatorios", description = "Operações relacionadas aos relatórios")
public class RelatorioController {

    @GetMapping("/vendas-po-restaurante")
    public ResponseEntity<?> vendasPorRestaurante() {
        return null;
    }

    @GetMapping("produtos-mais-vendidos")
    public ResponseEntity<?> topProdutos() {
        return null;
    }

    @GetMapping("/clientes-ativos")
    public ResponseEntity<?> clientesMaisAtivos() {
        return null;
    }

    @GetMapping("/pedidos-por-periodo")
    public ResponseEntity<?> pedidosPorPeriodo() {
        return null;
    }

}
