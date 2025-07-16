package com.deliverytech.delivery.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
@Tag(name = "Relatorios", description = "Operações relacionadas aos relatórios")
public class RelatorioController {
//            GET /api/relatorios/vendas-por-restaurante - Vendas por restaurante
//
//         GET /api/relatorios/produtos-mais-vendidos - Top produtos
//
//         GET /api/relatorios/clientes-ativos - Clientes mais ativos
//
//        GET /api/relatorios/pedidos-por-periodo - Pedidos por período
}
