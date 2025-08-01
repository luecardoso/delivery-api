package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.RestauranteRequestDTO;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class RestauranteControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private RestauranteRequestDTO restauranteRequestDTO;

    private Restaurante restauranteSalvo;

    @BeforeEach
    void setUp() {
        restauranteRequestDTO = new RestauranteRequestDTO();
        restauranteRequestDTO.setNome("Pizza Express 123");
        restauranteRequestDTO.setCategoria("Italiana");
        restauranteRequestDTO.setEndereco("Rua das Flores, 123");
        restauranteRequestDTO.setTelefone("11999999999");
        restauranteRequestDTO.setTaxaEntrega(new BigDecimal("5.50"));
        restauranteRequestDTO.setTempoEntregaMinutos(45);
        restauranteRequestDTO.setHorarioFuncionamento("08:00-22:00");
        restauranteRequestDTO.setAtivo(true);

        // Criar restaurante para testes de busca
        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Burger King");
        restaurante.setCategoria("Americana");
        restaurante.setEndereco("Av. Paulista, 1000");
        restaurante.setTelefone("11888888888");
        restaurante.setTaxaEntrega(new BigDecimal("4.00"));
        restaurante.setTempoEntregaMinutos(30);
        restaurante.setHorarioFuncionamento("10:00-23:00");
        restaurante.setAtivo(true);
        restauranteSalvo = restauranteRepository.save(restaurante);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveCadastrarRestauranteComSucesso() throws Exception {
        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nome").value("Pizza Express 123"))
                .andExpect(jsonPath("$.data.categoria").value("Italiana"))
                .andExpect(jsonPath("$.data.endereco").value("Rua das Flores, 123"))
                .andExpect(jsonPath("$.data.telefone").value("11999999999"))
                .andExpect(jsonPath("$.data.taxaEntrega").value(new BigDecimal("5.5")))
                .andExpect(jsonPath("$.data.ativo").value(true))
                .andExpect(jsonPath("$.data.tempoEntregaMinutos").value(45))
                .andExpect(jsonPath("$.message").value("Restaurante criado com sucesso"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveRejeitarRestauranteComDadosInvalidos() throws Exception {
        restauranteRequestDTO.setNome(""); // Nome inválido
        restauranteRequestDTO.setTelefone("123"); // Telefone inválido
        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequestDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.nome").exists())
                .andExpect(jsonPath("$.details.telefone").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveBuscarRestaurantePorId() throws Exception {
        mockMvc.perform(get("/api/restaurantes/{id}", restauranteSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(restauranteSalvo.getId()))
                .andExpect(jsonPath("$.data.nome").value("Burger King"))
                .andExpect(jsonPath("$.data.categoria").value("Americana"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveRetornar404ParaRestauranteInexistente() throws Exception {
        mockMvc.perform(get("/api/restaurantes/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("ENTITY_NOT_FOUND"));
    }

    @Test
    void deveListarRestaurantesComPaginacao() throws Exception {

        mockMvc.perform(get("/api/restaurantes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(7)))//3 do banco.sql + 3 DataLoader
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.size").value(10))
                .andExpect(jsonPath("$.page.totalElements").value(7));//3 do banco.sql + 3 DataLoader
    }

//TODO criar listagem com paginacao
    @Test
    void deveRetornarUmRestauranteNaPaginaZeroComTamanhoDez() throws Exception {
        mockMvc.perform(get("/api/restaurantes?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(7))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.size").value(10))
                .andExpect(jsonPath("$.page.totalElements").value(7));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveAtualizarRestauranteComSucesso() throws Exception {
        restauranteRequestDTO.setNome("Pizza Express Atualizada");
        mockMvc.perform(put("/api/restaurantes/{id}", restauranteSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nome").value("Pizza Express Atualizada"))
                .andExpect(jsonPath("$.message").value("Restaurante atualizado com sucesso"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void deveAlterarStatusRestaurante() throws Exception {
        mockMvc.perform(patch("/api/restaurantes/{id}/status", restauranteSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.ativo").value(false))
                .andExpect(jsonPath("$.message").value("Status alterado com sucesso"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN","CLIENTE"})
    void deveBuscarRestaurantesPorCategoria() throws Exception {
        mockMvc.perform(get("/api/restaurantes/categoria/{categoria}", "Americana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].categoria").value("Americana"));
    }

}
