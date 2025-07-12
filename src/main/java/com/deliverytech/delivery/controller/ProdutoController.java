package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.ProdutoRequestDTO;
import com.deliverytech.delivery.dto.response.ProdutoResponseDTO;
import com.deliverytech.delivery.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Criar novo produto
     */
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO ) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.cadastrarProduto(produtoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponseDTO);
    }

    /**
     * Buscar produto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    /**
     * Listar produtos por restaurante
     */
    @GetMapping("/restaurantes/{restauranteId}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorRestaurante(restauranteId);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Buscar por categoria
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorCategoria(@PathVariable String categoria) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutoPorNome(@PathVariable String nome) {
        List<ProdutoResponseDTO> produto = produtoService.buscarProdutosPorNome(nome);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarTodosProdutos() {
        List<ProdutoResponseDTO> produtos = produtoService.buscarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }

    /**
     * Atualizar produto
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id,
                                       @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoRequestDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    /**
     * Alterar disponibilidade
     */
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Void> atualizarDisponibilidade(@PathVariable Long id,
                                             @RequestParam boolean disponibilidade) {
        produtoService.alterarDisponibilidade(id, disponibilidade);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar por faixa de preço
     */
    @GetMapping("/buscar/preco")
    public ResponseEntity<List<ProdutoResponseDTO>> buscaPorPreco(@RequestParam("precoMinimo") BigDecimal precoMinimo,
                                                       @RequestParam("precoMaximo") BigDecimal precoMaximo){
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorFaixaPreco(precoMinimo, precoMaximo);
        return ResponseEntity.ok(produtos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> ativarDesativarProduto(@PathVariable Long id) {
        ProdutoResponseDTO produtoAtualizado = produtoService.ativarDesativarProduto(id);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @GetMapping("/buscar/preco/{valor}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorPrecoMenorOuIgual(@PathVariable BigDecimal valor) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorPrecoMenorOuIgual(valor);
        return ResponseEntity.ok(produtos);
    }

}
