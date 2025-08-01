package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.config.TestDataConfiguration;
import com.deliverytech.delivery.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestDataConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Testes de Integração do ClienteController")
class ClienteControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Deve criar cliente com dados válidos")
    void should_CreateCliente_When_ValidData() throws Exception {
        // Given
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNome("Maria Silva");
        clienteRequestDTO.setEmail("mariacliente@email.com");
        clienteRequestDTO.setTelefone("11888888888");
        clienteRequestDTO.setEndereco("Rua das Flores, 123");
        clienteRequestDTO.setCpf("73788843080");
        // When & Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.nome", is("Maria Silva")))
                .andExpect(jsonPath("$.data.email", is("mariacliente@email.com")))
                .andExpect(jsonPath("$.data.id", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando dados inválidos")
    void should_ReturnBadRequest_When_InvalidData() throws Exception {
        // Given
        ClienteRequestDTO clienteInvalido = new ClienteRequestDTO();
        clienteInvalido.setNome(""); // Nome vazio
        clienteInvalido.setEmail("email-invalido"); // Email inválido
        clienteInvalido.setTelefone("");
        clienteInvalido.setEndereco("");
        // When & Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteInvalido)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").exists())
                //qualquer quantidade > 0
                .andExpect(jsonPath("$.details.*", hasSize(greaterThan(0))));
    }

    @Test
    @DisplayName("Deve buscar cliente por ID existente")
    @WithMockUser(roles = "ADMIN")
    void should_ReturnCliente_When_IdExists() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(0);
        // When & Then
        mockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(cliente.getId().intValue())))
                .andExpect(jsonPath("$.data.nome", is(cliente.getNome())))
                .andExpect(jsonPath("$.data.email", is(cliente.getEmail())));
    }

    @Test
    @DisplayName("Deve retornar 404 quando cliente não existe")
    @WithMockUser(roles = "ADMIN")
    void should_ReturnNotFound_When_ClienteNotExists() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/clientes/{id}", 999L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Cliente não encontrado")));
    }

    @Test
    @DisplayName("Deve listar clientes com paginação")
    @WithMockUser(roles = "ADMIN")
    void should_ReturnPagedClientes_When_RequestedWithPagination() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/clientes")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.page.totalElements", greaterThan(0)))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andExpect(jsonPath("$.page.size", is(10)));
    }

    @Test
    @DisplayName("Deve atualizar cliente existente")
    @WithMockUser(roles = "ADMIN")
    void should_UpdateCliente_When_ClienteExists() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(0);
        cliente.setNome("Nome Atualizado");
        cliente.setEmail("novoemail@email.com");
        cliente.setTelefone("11777777777");
        cliente.setEndereco("Rua das Flores, 123");
        cliente.setCpf("73788843080");

        // When & Then
        mockMvc.perform(put("/api/clientes/{id}", cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nome", is("Nome Atualizado")))
                .andExpect(jsonPath("$.data.telefone", is("11777777777")));
    }

    @Test
    @DisplayName("Deve deletar cliente existente")
    @WithMockUser(roles = "ADMIN")
    void should_DeleteCliente_When_ClienteExists() throws Exception {
        // Given
        Cliente cliente = clienteRepository.findAll().get(6);
        // When & Then
        mockMvc.perform(delete("/api/clientes/{id}", cliente.getId()))
                .andExpect(status().isNoContent());
        // Verificar se foi deletado
        mockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
                .andExpect(status().isNotFound());
    }
}
