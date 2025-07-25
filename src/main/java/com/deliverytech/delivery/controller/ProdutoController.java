package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.ProdutoRequestDTO;
import com.deliverytech.delivery.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery.dto.response.PagedResponseWrapper;
import com.deliverytech.delivery.dto.response.ProdutoResponseDTO;
import com.deliverytech.delivery.service.ProdutoService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @PreAuthorize("hasRole('RESTAURANTE') or hasRole('ADMIN')")
    @Operation(summary = "Cadastrar produto",
            description = "Cria um novo produto no sistema",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            tags = {"Produtos"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Produto não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<ProdutoResponseDTO>> cadastrarProduto(@Valid @RequestBody
                                                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                                           description = "Dados do produto a ser criado"
                                                                                   )
                                                                                   ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produto = produtoService.cadastrarProduto(produtoRequestDTO);
        ApiResponseWrapper<ProdutoResponseDTO> response =
                new ApiResponseWrapper<>(true, produto, "Produto criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping
//    @Operation(summary = "Listar produtos",
//            description = "Retorna uma lista paginada de produtos. Pode ser filtrada por restaurante, categoria ou disponibilidade.",
//            tags = {"Produtos"}
//    )
//    public ResponseEntity<PagedResponseWrapper<ProdutoResponseDTO>> listar(@Parameter(description = "Informações de paginação") Pageable pageable,
//                                                                           @Parameter(description = "Filtro por ID do restaurante")
//                                                                           @RequestParam(required = false) Long restauranteId,
//                                                                           @Parameter(description = "Filtro por categoria")
//                                                                           @RequestParam(required = false) String categoria,
//                                                                           @Parameter(description = "Filtro por disponibilidade")
//                                                                           @RequestParam(required = false) Boolean disponivel) {
//
//        Page<ProdutoResponseDTO> produtos = produtoService.listarProdutosComPaginacao(pageable, restauranteId, categoria, disponivel);
//        return ResponseEntity.ok(produtos);
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID",
            description = "Recupera um produto específico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<ProdutoResponseDTO>> buscarProdutoPorId(@Parameter(description = "ID do produto")
                                                                                     @PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarProdutoPorId(id);
        ApiResponseWrapper<ProdutoResponseDTO> response =
                new ApiResponseWrapper<>(true, produto, "Produto encontrado");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurantes/{restauranteId}/produtos")
    @Operation(summary = "Buscar produtos por restaurante",
            description = "Lista todos os produtos de um restaurante específico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<List<ProdutoResponseDTO>>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorRestaurante(restauranteId);
        ApiResponseWrapper<List<ProdutoResponseDTO>> response =
                new ApiResponseWrapper<>(true, produtos, "Produto encontrado");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar por categoria",
            description = "Lista produtos de uma categoria específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontrados")
    })
    public ResponseEntity<ApiResponseWrapper<List<ProdutoResponseDTO>>> buscarProdutosPorCategoria(@Parameter(description = "Categoria do produto")
                                                                                                   @PathVariable String categoria) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorCategoria(categoria);
        ApiResponseWrapper<List<ProdutoResponseDTO>> response =
                new ApiResponseWrapper<>(true, produtos, "Produtos encontrados");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{nome}")
    @Operation(summary = "Buscar produto por nome",
            description = "Recupera os detalhes de um produto específico pelo nome")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<List<ProdutoResponseDTO>>> buscarProdutoPorNome(@Parameter(description = "Nome do produto")
                                                                                             @RequestParam String nome) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorNome(nome);
        ApiResponseWrapper<List<ProdutoResponseDTO>> response =
                new ApiResponseWrapper<>(true, produtos, "Busca realizada com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos",
            description = "Lista todos os produtos disponíveis no sistema",
            tags = {"Produtos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<List<ProdutoResponseDTO>>> buscarTodosProdutos() {
        List<ProdutoResponseDTO> produtos = produtoService.buscarTodosProdutos();
        ApiResponseWrapper<List<ProdutoResponseDTO>> response =
                new ApiResponseWrapper<>(true, produtos, "Produtos encontrados");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || @produtoService.isOwner(#id)")
    @Operation(summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<ProdutoResponseDTO>> atualizarProduto(@Parameter(description = "ID do produto")
                                                                                   @PathVariable Long id,
                                                                                   @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoRequestDTO);
        ApiResponseWrapper<ProdutoResponseDTO> response =
                new ApiResponseWrapper<>(true, produtoAtualizado, "Produto encontrado");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/disponibilidade")
    @Operation(summary = "Alterar disponibilidade", description = "Alterna a disponibilidade do produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Disponibilidade alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<ProdutoResponseDTO>> atualizarDisponibilidade(@Parameter(description = "ID do produto")
                                                                                           @PathVariable Long id) {
        //Verificar métodos inexistente
        ProdutoResponseDTO produtoResponseDTO = produtoService.alterarDisponibilidade(id);
        produtoService.alterarDisponibilidade(id);
        ApiResponseWrapper<ProdutoResponseDTO> response =
                new ApiResponseWrapper<>(true, produtoResponseDTO, "Produto encontrado");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/preco")
    @Operation(summary = "Buscar produtos por faixa de preço",
            description = "Lista todos os produtos dentro de uma faixa de preço específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado na faixa de preço")
    })
    public ResponseEntity<ApiResponseWrapper<List<ProdutoResponseDTO>>> buscaPorPreco(@RequestParam("precoMinimo") BigDecimal precoMinimo,
                                                                                      @RequestParam("precoMaximo") BigDecimal precoMaximo) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorFaixaPreco(precoMinimo, precoMaximo);
        ApiResponseWrapper<List<ProdutoResponseDTO>> response =
                new ApiResponseWrapper<>(true, produtos, "Produto encontrado");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Ativar/Desativar produto",
            description = "Ativa ou desativa um produto pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto ativado/desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<ProdutoResponseDTO>> ativarDesativarProduto(@PathVariable Long id) {
        ProdutoResponseDTO produtoAtualizado = produtoService.ativarDesativarProduto(id);
        ApiResponseWrapper<ProdutoResponseDTO> response =
                new ApiResponseWrapper<>(true, produtoAtualizado, "Produto encontrado");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/preco/{valor}")
    @Operation(summary = "Buscar produtos por preço menor ou igual",
            description = "Lista todos os produtos com preço menor ou igual ao valor especificado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado com preço menor ou igual ao valor especificado")
    })
    public ResponseEntity<ApiResponseWrapper<List<ProdutoResponseDTO>>> buscarPorPrecoMenorOuIgual(@PathVariable BigDecimal valor) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorPrecoMenorOuIgual(valor);
        ApiResponseWrapper<List<ProdutoResponseDTO>> response =
                new ApiResponseWrapper<>(true, produtos, "Produto encontrado");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || @produtoService.isOwner(#id)")
    @Operation(summary = "Remover produto", description = "Remove um produto do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Void> removerProduto(@Parameter(description = "ID do produto")
                                               @PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.noContent().build();
    }
}
