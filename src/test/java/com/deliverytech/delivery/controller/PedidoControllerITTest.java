package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.config.TestDataConfiguration;
import com.deliverytech.delivery.dto.request.ItemPedidoRequestDTO;
import com.deliverytech.delivery.dto.request.PedidoRequestDTO;
import com.deliverytech.delivery.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.exceptions.EntityNotFoundException;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestDataConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Testes de Integração do PedidoController")
class PedidoControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    @DisplayName("Deve criar pedido com dados válidos")
    @WithMockUser(roles = "CLIENTE")
    void should_CreatePedido_When_ValidData() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(0);
        Produto produto = produtoRepository.findAll().get(0);
        produto.setEstoque(10);
        produtoRepository.save(produto);

        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(2);

        PedidoRequestDTO pedidoDTO = new PedidoRequestDTO();
        pedidoDTO.setNumeroPedido("PEDIDO20250729");
        pedidoDTO.setDataPedido(LocalDateTime.now());
        pedidoDTO.setValorTotal(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.getQuantidade())));
        pedidoDTO.setClienteId(cliente.getId());

        Restaurante restaurante = restauranteRepository.findById(produto.getRestaurante().getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado: " + produto.getRestaurante().getId()));

        pedidoDTO.setRestauranteId(restaurante.getId());
        pedidoDTO.setItens(Arrays.asList(itemDTO));
        pedidoDTO.setCep("78053580");
        pedidoDTO.setFormaPagamento("CARTAO_CREDITO");

        // When & Then
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.cliente.id", is(cliente.getId().intValue())))
                .andExpect(jsonPath("$.data.status", is("PENDENTE")))
                .andExpect(jsonPath("$.data.valorTotal", is(76.80))) // 2 x 35,90 + 5.0 (taxa entrega)
                .andExpect(jsonPath("$.data.itens", hasSize(1)));
    }

    @Test
    @DisplayName("Deve retornar erro quando produto não existe")
    @WithMockUser(roles = "CLIENTE")
    void should_ReturnError_When_ProductNotExists() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(0);
        Produto produto = produtoRepository.findAll().get(0);

        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setProdutoId(999L); // Produto inexistente
        itemDTO.setQuantidade(1);

        PedidoRequestDTO pedidoDTO = new PedidoRequestDTO();
        pedidoDTO.setNumeroPedido("PEDIDO20250729");
        pedidoDTO.setDataPedido(LocalDateTime.now());
        pedidoDTO.setValorTotal(new BigDecimal("0"));
        pedidoDTO.setClienteId(cliente.getId());

        Restaurante restaurante = restauranteRepository.findById(produto.getRestaurante().getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado: " + produto.getRestaurante().getId()));

        pedidoDTO.setRestauranteId(restaurante.getId());
        pedidoDTO.setItens(Arrays.asList(itemDTO));
        pedidoDTO.setCep("78053580");
        pedidoDTO.setFormaPagamento("CARTAO_CREDITO");

        // When & Then
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Produto não encontrado")));
    }

    @Test
    @DisplayName("Deve retornar erro quando estoque insuficiente")
    @WithMockUser(roles = "CLIENTE")
    void should_ReturnError_When_InsufficientStock() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(0);
        Produto produto = produtoRepository.findAll().get(0);

        produto.setEstoque(5);
        produtoRepository.save(produto);

        Restaurante restaurante = restauranteRepository.findById(produto.getRestaurante().getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado: " + produto.getRestaurante().getId()));

        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(10);//Quantidade maior que estoque

        PedidoRequestDTO pedidoDTO = new PedidoRequestDTO();
        pedidoDTO.setNumeroPedido("PEDIDO20250729");
        pedidoDTO.setDataPedido(LocalDateTime.now());
        pedidoDTO.setValorTotal(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.getQuantidade())));
        pedidoDTO.setClienteId(cliente.getId());
        pedidoDTO.setRestauranteId(restaurante.getId());
        pedidoDTO.setItens(Arrays.asList(itemDTO));
        pedidoDTO.setCep("78053580");
        pedidoDTO.setFormaPagamento("CARTAO_CREDITO");

        // When & Then
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Estoque insuficiente")));

        // Verifica que o estoque não foi alterado
        Produto produtoAtualizado = produtoRepository.findById(produto.getId()).orElseThrow();
        Assertions.assertEquals(produto.getEstoque(), produtoAtualizado.getEstoque(),
                "O estoque não deve ser alterado quando o pedido é rejeitado");
    }

    @Test
    @DisplayName("Deve buscar histórico de pedidos do cliente")
    @WithMockUser(roles = "CLIENTE")
    void should_ReturnClientePedidos_When_ClienteExists() throws Exception {
        // Given
        // Busca o cliente
        Cliente cliente = clienteRepository.findAll().get(0);
        // Busca o produto
        Produto produto = produtoRepository.findAll().get(0);

        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(1);

        PedidoResponseDTO pedidoDTO = new PedidoResponseDTO();
        pedidoDTO.setCliente(cliente);
        pedidoDTO.setItens(Arrays.asList(itemDTO));

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andDo(print());
        // When & Then
        mockMvc.perform(get("/api/pedidos/clientes/{clienteId}/pedidos", cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.data[*].cliente.id", everyItem(is(cliente.getId().intValue()))));
    }

    @Test
    @DisplayName("Deve atualizar status do pedido")
    @WithMockUser(roles = {"RESTAURANTE", "ADMIN", "CLIENTE"})
    void should_UpdatePedidoStatus_When_PedidoExists() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(0);
        Produto produto = produtoRepository.findAll().get(0);

        produto.setEstoque(10);
        produtoRepository.save(produto);

        // Criar pedido primeiro
        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(1);

        Restaurante restaurante = restauranteRepository.findById(produto.getRestaurante().getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado: " + produto.getRestaurante().getId()));

        PedidoRequestDTO pedidoDTO = new PedidoRequestDTO();
        pedidoDTO.setNumeroPedido("PEDIDO20250729");
        pedidoDTO.setDataPedido(LocalDateTime.now());
        pedidoDTO.setValorTotal(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.getQuantidade())));
        pedidoDTO.setClienteId(cliente.getId());
        pedidoDTO.setRestauranteId(restaurante.getId());
        pedidoDTO.setItens(Arrays.asList(itemDTO));
        pedidoDTO.setCep("78053580");
        pedidoDTO.setFormaPagamento("CARTAO_CREDITO");

        // POST Criar pedido
        // When & Then
        MvcResult result = mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // Extrai o corpo da resposta como string
        String response = result.getResponse().getContentAsString();
        // Converte a string JSON em um objeto JsonNode para manipulação
        JsonNode jsonResponse = objectMapper.readTree(response);
        // Extrai o ID do pedido do JSON (caminho: data.id)
        Long pedidoId = jsonResponse.path("data").path("id").asLong();
        // Faz uma requisição PATCH para atualizar o status
        mockMvc.perform(patch("/api/pedidos/{id}/status", pedidoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"CONFIRMADO\""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status", is("CONFIRMADO")));
    }

    @Test
    @DisplayName("Deve validar cálculo correto do valor total")
    @WithMockUser(roles = {"RESTAURANTE", "ADMIN", "CLIENTE"})
    void should_CalculateCorrectTotal_When_MultipleItems() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(0);
        Produto produto = produtoRepository.findAll().get(0);

        produto.setEstoque(10);
        produtoRepository.save(produto);

        ItemPedidoRequestDTO item1 = new ItemPedidoRequestDTO();
        item1.setProdutoId(produto.getId());
        item1.setQuantidade(2); // 2 x 35.90 = 71.80

        ItemPedidoRequestDTO item2 = new ItemPedidoRequestDTO();
        item2.setProdutoId(produto.getId());
        item2.setQuantidade(1); // 1 x 35.90 = 35.90

        Restaurante restaurante = restauranteRepository.findById(produto.getRestaurante().getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado: " + produto.getRestaurante().getId()));

        PedidoRequestDTO pedidoDTO = new PedidoRequestDTO();
        pedidoDTO.setNumeroPedido("PEDIDO20250729");
        pedidoDTO.setDataPedido(LocalDateTime.now());
        pedidoDTO.setValorTotal(produto.getPreco().multiply(BigDecimal.valueOf(item1.getQuantidade()))
                .add(produto.getRestaurante().getTaxaEntrega()));
        pedidoDTO.setClienteId(cliente.getId());
        pedidoDTO.setRestauranteId(restaurante.getId());
        pedidoDTO.setItens(Arrays.asList(item1,item2));
        pedidoDTO.setCep("78053580");
        pedidoDTO.setFormaPagamento("CARTAO_CREDITO");

        // When & Then
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                // Total: Produto 3 * 35.90 + frete 5.0
                .andExpect(jsonPath("$.data.valorTotal", is(112.70)));
    }
}
