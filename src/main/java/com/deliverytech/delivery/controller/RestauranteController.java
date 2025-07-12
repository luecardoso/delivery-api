package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.RestauranteRequestDTO;
import com.deliverytech.delivery.dto.response.RestauranteResponseDTO;
import com.deliverytech.delivery.projection.RelatorioVendas;
import com.deliverytech.delivery.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    /**
     * Cadastrar novo restaurante
     */
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@Valid @RequestBody
                                                                           RestauranteRequestDTO restauranteRequestDTO) {
        RestauranteResponseDTO restaurante = restauranteService.cadastrarRestaurante(restauranteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    }

    /**
     * Buscar restaurante por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteResponseDTO restaurante = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(restaurante);
    }

    /**
     * Listar todos os restaurantes ativos
     */
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listarRestaurantesAtivos() {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesAtivos();
        return ResponseEntity.ok(restaurantes);
    }

    /**
     * Buscar por categoria
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarRestaurantePorCategoria(@PathVariable String categoria) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantePorCategoria(categoria);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorNome(@PathVariable String nome) {
        RestauranteResponseDTO restaurante = restauranteService.buscarRestaurantePorNome(nome);
        return ResponseEntity.ok(restaurante);
    }

    @GetMapping("/buscar/{precoMinimo}/{precoMaximo}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarRestaurantePorPreco(@PathVariable BigDecimal precoMinimo,
                                                                       @PathVariable BigDecimal precoMaximo) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantePorPreco(precoMinimo, precoMaximo);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/buscar/taxa")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorTaxaEntrega(@RequestParam BigDecimal taxa) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarPorTaxaEntrega(taxa);
        return ResponseEntity.ok(restaurantes);
    }

    /**
     * Atualizar restaurante
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarRestaurante(@PathVariable Long id,
                                       @Valid @RequestBody RestauranteRequestDTO restauranteRequestDTO) {
        RestauranteResponseDTO restauranteAtualizado = restauranteService.atualizarRestaurante(id, restauranteRequestDTO);
        return ResponseEntity.ok(restauranteAtualizado);
    }

    /**
     * Inativar restaurante
     */
    @PatchMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> ativarDesativarRestaurante(@PathVariable Long id) {
        RestauranteResponseDTO restauranteAtualizado = restauranteService.ativarDesativarRestaurante(id);
        return ResponseEntity.ok(restauranteAtualizado);
    }

    /**
     *  calcular a taxa de entrega
     */
    @GetMapping("{id}/taxa-entrega/{cep}")
    public ResponseEntity<RestauranteResponseDTO> calcularTaxaEntrega(@PathVariable Long id,
                                                 @PathVariable String cep){
        BigDecimal taxaEntrega = restauranteService.calcularTaxaEntrega(id,cep);
        RestauranteResponseDTO responseDTO= restauranteService.buscarRestaurantePorId(id);
        responseDTO.setTaxaEntrega(taxaEntrega);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<RestauranteResponseDTO>> listarTop5PorNome() {
        List<RestauranteResponseDTO> top5Restaurantes = restauranteService.buscarTop5RestaurantesPorNome();
        return ResponseEntity.ok(top5Restaurantes);
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<RelatorioVendas>> relatorioVendasPorRestaurante() {
        List<RelatorioVendas> relatorio = restauranteService.relatorioVendasPorRestaurante();
        return ResponseEntity.ok(relatorio);
    }

}
