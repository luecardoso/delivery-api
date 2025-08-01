package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery.dto.response.ClienteResponseDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.exceptions.EntityNotFoundException;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.impl.ClienteServiceImpl;
import com.deliverytech.delivery.validation.CPFValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteRequestDTO clienteRequest;
    private ClienteResponseDTO clienteResponse;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;


    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@example.com");
        cliente.setTelefone("(11)99999-9999");
        cliente.setEndereco("Rua ABC, 123");
        cliente.setAtivo(true);
        cliente.setCpf("65858206816");

        clienteRequest = new ClienteRequestDTO();
        clienteRequest.setNome("João Silva");
        clienteRequest.setEmail("joao@example.com");
        clienteRequest.setTelefone("(11)99999-9999");
        clienteRequest.setEndereco("Rua ABC, 123");
        clienteRequest.setCpf("65858206816");

        clienteResponse = new ClienteResponseDTO();
        clienteResponse.setId(1L);
        clienteResponse.setNome("João Silva");
        clienteResponse.setEmail("joao@example.com");
        clienteResponse.setTelefone("(11)99999-9999");
        clienteResponse.setAtivo(true);
    }

    @Test
    @DisplayName("Deve salvar cliente com dados válidos")
    void should_SaveCliente_When_ValidData() {
        // Given
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        //O ModelMapper deve converter o RequestDTO para a entidade Cliente
        when(modelMapper.map(clienteRequest, Cliente.class)).thenReturn(cliente);
        // O repositório deve salvar a entidade (simulando o retorno com ID)
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        // O ModelMapper deve converter a entidade salva para o ResponseDTO
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(clienteResponse);
        // When
        ClienteResponseDTO resultado = clienteService.cadastrarCliente(clienteRequest);
        // Then
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());

        verify(clienteRepository).save(cliente);
        verify(clienteRepository).existsByEmail("joao@example.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe")
    void should_ThrowException_When_EmailAlreadyExists() {
        // Given
        when(clienteRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.cadastrarCliente(clienteRequest)
        );
        assertEquals("Email já cadastrado: " + clienteRequest.getEmail(), exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existe")
    void should_ThrowException_When_CpfAlreadyExists() {
        // Given
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.existsByCpf(anyString())).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.cadastrarCliente(clienteRequest)
        );
        assertEquals("CPF já cadastrado " + clienteRequest.getCpf(), exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }


    @Test
    @DisplayName("Deve buscar cliente por ID existente")
    void should_ReturnCliente_When_IdExists() {
        // Given

        // When
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(clienteResponse);

        ClienteResponseDTO clienteResponse = clienteService.buscarClientePorId(1L);

        // Then
        assertNotNull(clienteResponse);
        assertEquals("João Silva", clienteResponse.getNome());
        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar EntityNotFound quando ID não existe")
    void should_ReturnException_When_IdNotExists() {
        // Given
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());
        // When & Then
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> clienteService.buscarClientePorId(999L)
        );
        verify(clienteRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve listar clientes com paginação")
    void should_ReturnPagedClientes_When_RequestedWithPagination() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> page = new PageImpl<>(Arrays.asList(cliente), pageable, 1);

        when(clienteRepository.findByAtivoTrue(pageable)).thenReturn(page);
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(clienteResponse);
        // When
        Page<ClienteResponseDTO> resultado = clienteService.listarAtivosPaginado(pageable);
        // Then
        assertNotNull(resultado, "O resultado da paginação não deve ser nulo");
        assertEquals(1, resultado.getTotalElements());
        assertEquals("João Silva", resultado.getContent().get(0).getNome());
        verify(clienteRepository).findByAtivoTrue(pageable);
    }

    @Test
    @DisplayName("Deve atualizar cliente existente")
    void should_UpdateCliente_When_ClienteExists() {
        // Given
        ClienteRequestDTO clienteAtualizado = new ClienteRequestDTO();
        clienteAtualizado.setNome("João Santos");
        clienteAtualizado.setEmail("joao.santos@email.com");
        clienteAtualizado.setTelefone("11989898888");
        clienteAtualizado.setEndereco("Jardim botanico, 123");
        clienteAtualizado.setCpf("61688389024");

        Cliente clienteMapeado = new Cliente();
        clienteMapeado.setNome(clienteAtualizado.getNome());
        clienteMapeado.setEmail(clienteAtualizado.getEmail());
        clienteMapeado.setTelefone(clienteAtualizado.getTelefone());
        clienteMapeado.setEndereco(clienteAtualizado.getEndereco());
        clienteMapeado.setCpf(clienteAtualizado.getCpf());

        //When
        when(modelMapper.map(any(Cliente.class), eq(ClienteResponseDTO.class)))
                .thenReturn(new ClienteResponseDTO() {{
                    setNome("João Santos");
                    setEmail("joao.santos@email.com");
                    setTelefone("11989898888");
//                    setCpf("12345678901");
//                    setEndereco("Rua Exemplo, 123, São Paulo, SP");
                    setAtivo(true);
                }});

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMapeado));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMapeado);


        ClienteResponseDTO clienteResponseDTO = clienteService.atualizarCliente(1L, clienteAtualizado);

        // Then
        assertEquals("João Santos", clienteResponseDTO.getNome());
        assertNotNull(clienteResponseDTO);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve validar CPF corretamente")
    void should_ValidateCpf_When_ValidFormat() {
        CPFValidator cpfValidator = new CPFValidator();
        assertTrue(cpfValidator.isValid("61688389024", constraintValidatorContext));
        assertFalse(cpfValidator.isValid("123", constraintValidatorContext));
        assertFalse(cpfValidator.isValid("abc", constraintValidatorContext));
    }

    // Teste para construtores
    @Test
    @DisplayName("Deve criar cliente com construtor padrão")
    void should_CreateCliente_When_DefaultConstructor() {
        Cliente cliente = new Cliente();
        assertNotNull(cliente);
        assertNull(cliente.getId());
    }

    // Teste para getters/setters
    @Test
    @DisplayName("Deve definir e obter propriedades corretamente")
    void should_SetAndGetProperties_When_ValidValues() {
        Cliente cliente = new Cliente();
        cliente.setNome("Teste");
        cliente.setEmail("teste@email.com");
        assertEquals("Teste", cliente.getNome());
        assertEquals("teste@email.com", cliente.getEmail());
    }

    // Teste para equals e hashCode
    @Test
    @DisplayName("Deve comparar clientes corretamente")
    void should_CompareClientes_When_SameId() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(1L);
        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    // Teste para toString
    @Test
    @DisplayName("Deve gerar string representation corretamente")
    void should_GenerateToString_When_Called() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");
        String result = cliente.toString();
        assertTrue(result.contains("João"));
        assertTrue(result.contains("1"));
    }
}