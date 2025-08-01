package com.deliverytech.delivery.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Endpoints para checagem da API")
public class HealthController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/health")
    @Operation(summary = "Checa saúde da API", description = "Retorna informações sobre a saúde da API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Map<String, String>> health() {
        // Usando Map.of() (Java 9+) para criar mapa imutável
        Map<String, String> healthInfo = Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().format(FORMATTER),
                "service", "Delivery API",
                "javaVersion", System.getProperty("java.version"),
                "springBootVersion", getClass().getPackage().getImplementationVersion() != null
                        ? getClass().getPackage().getImplementationVersion() : "3.x.x",
                "environment", "development"

        );
        return ResponseEntity.ok(healthInfo);
    }

    @GetMapping("/info")
    @Operation(summary = "Checa informações da API", description = "Retorna informações sobre a API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AppInfo> info() {
        AppInfo appInfo = new AppInfo(
                "Delivery Tech API",
                "1.0.0",
                "Lucas Reis",
                System.getProperty("java.version"),
                "Spring Boot 3.5.2",
                LocalDateTime.now().format(FORMATTER),
                "Sistema de delivery moderno desenvolvido com as mais recentes tecnologias Java"
        );
        return ResponseEntity.ok(appInfo);
    }

    // Record para demonstrar recurso do Java 14+ (disponível no JDK 21)
    public record AppInfo(
            String application,
            String version,
            String developer,
            String javaVersion,
            String framework,
            String timestamp,
            String description
    ) {
        public AppInfo {
            if (application == null || application.isBlank()) {
                throw new IllegalArgumentException("Application name cannot be null or blank");
            }

        }
    }
}


