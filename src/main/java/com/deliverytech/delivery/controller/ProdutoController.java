package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.service.ProdutoService;
import com.deliverytech.delivery.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private RestauranteService restauranteService;

    /**
     * Criar novo produto
     */
    @PostMapping("/restauranteId")
    public ResponseEntity<?> criarProduto(@Valid @RequestBody Produto produto, @RequestParam Long restauranteId) {
        Optional<Restaurante> restaurante = restauranteService.buscarPorId(restauranteId);
        try {
            Produto produtoSalvo = produtoService.cadastrar(produto, restauranteId);
            return ResponseEntity.status(HttpStatus.CREATED).body(produto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Buscar produto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Listar produtos por restaurante
     */
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<Produto>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<Produto> produtos = produtoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Buscar por categoria
     */
    @GetMapping("/categoria")
    public ResponseEntity<?> buscarPorCategoria(@RequestParam String categoria) {
        List<Produto> produto = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produto);
    }

    /**
     * Atualizar produto
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Alterar disponibilidade
     */
    @PutMapping("/{id}/disponibilidade")
    public ResponseEntity<Void> atualizarDisponibilidade(@PathVariable Long id,
                                             @RequestParam boolean disponibilidade) {
        produtoService.alterarDisponibilidade(id, disponibilidade);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar por faixa de preço
     */
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscaPorPreco(@RequestParam("precoMin") BigDecimal precoMin,
                                                       @RequestParam("precoMax") BigDecimal precoMax){
        List<Produto> produtos = produtoService.buscarPorFaixaPreco(precoMin,precoMax);
        return ResponseEntity.ok(produtos);
    }

}
