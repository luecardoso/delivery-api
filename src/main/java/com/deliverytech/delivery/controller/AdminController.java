package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.response.DashboardResponseDTO;
import com.deliverytech.delivery.dto.response.RelatorioVendasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
@Tags({
        @Tag(name = "Administração", description = "Endpoints exclusivos para administradores"),
        @Tag(name = "Relatórios", description = "Geração de relatórios e estatisticas")
})
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {


    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard administrativo",
            description = "Retorna estatisticas gerais do sistema para o dashboard",
            tags = {"Administração", "Relatórios"}
    )
    public ResponseEntity<DashboardResponseDTO> getDashboard() {
        //Implementação
        return null;
    }

    @GetMapping("/relatorio/vendas")
    @Operation(summary = "Relatório de vendas",
            description = "Gera relatório detalhado de vendas por período",
            tags = {"Relatórios"}
    )
    public ResponseEntity<RelatorioVendasResponseDTO> getRelatorioVendas(@Parameter(description = "Data inicial")
                                                                         @RequestParam LocalDate dataInicio,
                                                                         @Parameter(description = "Data final")
                                                                         @RequestParam LocalDate dataFim) {
        // Implementação
        return null;
    }
}