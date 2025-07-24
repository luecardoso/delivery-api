package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.RestauranteRequestDTO;
import com.deliverytech.delivery.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery.dto.response.PagedResponseWrapper;
import com.deliverytech.delivery.dto.response.RestauranteResponseDTO;
import com.deliverytech.delivery.repository.projection.RelatorioVendas;
import com.deliverytech.delivery.service.ProdutoService;
import com.deliverytech.delivery.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "Restaurantes", description = "Operações relacionadas aos restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar restaurante",
            description = "Cria um novo restaurante no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Restaurante já existe")
    })
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> cadastrarRestaurante(@Valid @RequestBody
                                                                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                                                   description = "Dados do restaurante a ser criado"
                                                                                           )
                                                                                           RestauranteRequestDTO restauranteRequestDTO) {
        RestauranteResponseDTO restaurante = restauranteService.cadastrarRestaurante(restauranteRequestDTO);
        ApiResponseWrapper<RestauranteResponseDTO> response =
                new ApiResponseWrapper<>(true, restaurante, "Restaurante criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID",
            description = "Recupera um restaurante especíﬁco pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> buscarPorId(
            @Parameter(description = "ID do restaurante") @Positive(message = "ID deve ser positivo")
            @PathVariable Long id) {

        RestauranteResponseDTO restaurante = restauranteService.buscarRestaurantePorId(id);
        ApiResponseWrapper<RestauranteResponseDTO> response =
                new ApiResponseWrapper<>(true, restaurante, "Restaurante encontrado");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes",
            description = "Lista restaurantes com filtros opcionais e paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    public ResponseEntity<PagedResponseWrapper<RestauranteResponseDTO>> listarRestaurantesComFiltro(@Parameter(description = "Categoria do restaurante")
                                                                                                    @RequestParam(required = false) String categoria,
                                                                                                    @Parameter(description = "Status ativo do restaurante")
                                                                                                    @RequestParam(required = false) Boolean ativo,
                                                                                                    @Parameter(description = "Parâmetros de paginação")
                                                                                                    Pageable pageable) {
        Page<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesComPaginacao(categoria, ativo, pageable);
        PagedResponseWrapper<RestauranteResponseDTO> response = new PagedResponseWrapper<>(restaurantes);
        return ResponseEntity.ok(response);
    }

//    @GetMapping
//    @Operation(summary = "Listar todos os restaurantes",
//            description = "Retorna uma lista paginada de todos os restaurantes disponíveis",
//            tags = {"Restaurantes"})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = RestauranteResponseDTO.class)))
//    })
//    public ResponseEntity<Page<RestauranteResponseDTO>> listar(@Parameter(description = "Informações de paginação") Pageable pageable,
//                                                               @Parameter(description = "Filtro por categoria")
//                                                               @RequestParam(required = false) String categoria) {
//        Page<RestauranteResponseDTO> restaurantes = restauranteService.listarTodos(pageable, categoria);
//        return ResponseEntity.ok(restaurantes);
//    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar Por Categoria",
            description = "Lista restaurantes de uma categoria especíﬁca")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurantes encontrados")
    })
    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> buscarRestaurantePorCategoria
            (@Parameter(description = "Categoria do restaurante") @PathVariable String categoria) {

        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantePorCategoria(categoria);
        ApiResponseWrapper<List<RestauranteResponseDTO>> response =
                new ApiResponseWrapper<>(true, restaurantes, "Restaurantes encontrados");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/buscar/{nome}")
    @Operation(summary = "Buscar restaurante por nome",
            description = "Recupera os detalhes de um restaurante específico pelo nome")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> buscarRestaurantePorNome(@PathVariable String nome) {
        RestauranteResponseDTO restaurante = restauranteService.buscarRestaurantePorNome(nome);
        ApiResponseWrapper<RestauranteResponseDTO> response =
                new ApiResponseWrapper<>(true, restaurante, "Restaurante encontrado");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{precoMinimo}/{precoMaximo}")
    @Operation(summary = "Buscar restaurantes por faixa de preço",
            description = "Lista todos os restaurantes dentro de uma faixa de preço específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurantes encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum restaurante encontrado dentro da faixa de preço")
    })
    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> buscarRestaurantePorPreco(@PathVariable BigDecimal precoMinimo,
                                                                                                      @PathVariable BigDecimal precoMaximo) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantePorPreco(precoMinimo, precoMaximo);
        ApiResponseWrapper<List<RestauranteResponseDTO>> response =
                new ApiResponseWrapper<>(true, restaurantes, "Restaurantes encontrados");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/taxa")
    @Operation(summary = "Buscar restaurantes por taxa de entrega",
            description = "Lista todos os restaurantes com uma taxa de entrega específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurantes encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum restaurante encontrado com a taxa de entrega especificada")
    })
    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> buscarPorTaxaEntrega(@RequestParam BigDecimal taxa) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarPorTaxaEntrega(taxa);
        ApiResponseWrapper<List<RestauranteResponseDTO>> response =
                new ApiResponseWrapper<>(true, restaurantes, "Restaurantes encontrados");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('RESTAURANTE') && @restauranteService.isOwner(#id)")
    @Operation(summary = "Atualizar restaurante",
            description = "Atualiza os dados de um restaurante existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> atualizarRestaurante(@Parameter(description = "ID do restaurante")
                                                                                           @PathVariable @Positive(message = "ID deve ser positivo") Long id,
                                                                                           @Valid @RequestBody
                                                                                           RestauranteRequestDTO restauranteRequestDTO) {
        RestauranteResponseDTO restauranteAtualizado = restauranteService.atualizarRestaurante(id, restauranteRequestDTO);
        ApiResponseWrapper<RestauranteResponseDTO> response =
                new ApiResponseWrapper<>(true, restauranteAtualizado, "Restaurante atualizado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Ativar/Desativar restaurante",
            description = "Alterna o status aꢀvo/inaꢀvo do restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> ativarDesativarRestaurante
            (@Parameter(description = "ID do restaurante") @Positive(message = "ID deve ser positivo")
             @PathVariable Long id) {
        RestauranteResponseDTO restauranteAtualizado = restauranteService.ativarDesativarRestaurante(id);
        ApiResponseWrapper<RestauranteResponseDTO> response =
                new ApiResponseWrapper<>(true, restauranteAtualizado, "Status alterado com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}/taxa-entrega/{cep}")
    @Operation(summary = "Calcular Taxa de entrega",
            description = "Calcula a taxa de entrega para um CEP especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Taxa calculada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<BigDecimal>> calcularTaxaEntrega(@Parameter(description = "ID do restaurante")
                                                                              @PathVariable @Positive(message = "ID deve ser positivo") Long id,
                                                                              @Parameter(description = "CEP de destino") @PathVariable String cep) {
        BigDecimal taxaEntrega = restauranteService.calcularTaxaEntrega(id, cep);
        ApiResponseWrapper<BigDecimal> response =
                new ApiResponseWrapper<>(true, taxaEntrega, "Taxa calculada com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top5")
    @Operation(summary = "Listar os 5 restaurantes mais populares por nome",
            description = "Retorna os 5 restaurantes mais populares ordenados por nome")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista dos 5 restaurantes mais populares retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum restaurante encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> listarTop5PorNome() {
        List<RestauranteResponseDTO> top5Restaurantes = restauranteService.buscarTop5RestaurantesPorNome();
        ApiResponseWrapper<List<RestauranteResponseDTO>> response =
                new ApiResponseWrapper<>(true, top5Restaurantes, "Restaurantes encontrados");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendas")
    @Operation(summary = "Gerar relatório de vendas por restaurante",
            description = "Gera um relatório de vendas agrupado por restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório de vendas gerado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum dado de vendas encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<List<RelatorioVendas>>> relatorioVendasPorRestaurante() {
        List<RelatorioVendas> relatorio = restauranteService.relatorioVendasPorRestaurante();
        ApiResponseWrapper<List<RelatorioVendas>> response =
                new ApiResponseWrapper<>(true, relatorio, "Restaurantes encontrados");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/proximos/{cep}")
    @Operation(summary = "Restaurantes próximos",
            description = "Lista restaurantes próximos a um CEP")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurantes próximos encontrados")
    })
    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> buscarProximos(@Parameter(description = "CEP de referência")
                                                                                           @PathVariable String cep,
                                                                                           @Parameter(description = "Raio em km")
                                                                                           @RequestParam(defaultValue = "10") Integer raio) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesProximos(cep, raio);
        ApiResponseWrapper<List<RestauranteResponseDTO>> response =
                new ApiResponseWrapper<>(true, restaurantes, "Restaurantes próximos encontrados");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar restaurante",
            description = "Inativa um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> inativarRestaurante(@PathVariable
                                                                                          @Positive(message = "ID deve ser positivo")
                                                                                          Long id) {
        RestauranteResponseDTO restauranteInativado = restauranteService.ativarDesativarRestaurante(id);
        ApiResponseWrapper<RestauranteResponseDTO> response =
                new ApiResponseWrapper<>(true, restauranteInativado, "Status alterado com sucesso");
        return ResponseEntity.ok(response);
    }

//    @GetMapping("{id}/produtos")
//    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> buscarprodutosDisponiveis(@PathVariable Long id) {

//    @GetMapping("/{restauranteId}/produtos")
//    @Operation(summary = "Produtos do restaurante",
//            description = "Lista todos os produtos de um restaurante")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
//            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
//    })
//    public ResponseEntity<ApiResponseWrapper<List<ProdutoResponseDTO>>> buscarProdutosDoRestaurante(@Parameter(description = "Id do Restaurante")
//                                                                                                    @PathVariable Long restauranteId,
//                                                                                                    @Parameter(description = "Filtrar apenas disponíveis")
//                                                                                                    @RequestParam(required = false) Boolean disponivel) {
//        //Verificar método que falta
////        List<ProdutoResponseDTO> produtos =
////                produtoService.buscarProdutosPorRestaurante(restauranteId, disponivel);
//        List<ProdutoResponseDTO> produtos =
//                produtoService.buscarProdutosPorRestaurante(restauranteId);
//        ApiResponseWrapper<List<ProdutoResponseDTO>> response =
//                new ApiResponseWrapper<>(true, produtos, "Produtos encontrados");
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    @Operation(summary = "Listar pedidos",
//            description = "Lista pedidos com filtros opcionais e paginação")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
//    })
//    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> listarTodos(
//            @RequestParam(required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
//            @Parameter(description = "Parâmetros de paginação")
//            Pageable pageable) {
//        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesAtivos();
//        ApiResponseWrapper<List<RestauranteResponseDTO>> response = new ApiResponseWrapper<>(true, restaurantes, "Busca Realizada com sucesso");
//        return ResponseEntity.ok(response);
//    }


}
