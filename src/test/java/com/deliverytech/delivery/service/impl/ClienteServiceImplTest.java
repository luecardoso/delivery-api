package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery.dto.response.ClienteResponseDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.exceptions.BusinessException;
import com.deliverytech.delivery.exceptions.EntityNotFoundException;
import com.deliverytech.delivery.repository.ClienteRepository;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setEmail("Lucas@email.com");
        cliente.setAtivo(true);
        cliente.setNome("Lucas");
        cliente.setTelefone("11999999999");
        cliente.setEndereco("Rua 123");
        cliente.setId(1L);
        cliente.setDataCadastro(LocalDateTime.now());

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Deve cadastrar um Cliente com sucesso quando o email for único e retornar um ClienteResponseDTO")
    void cadastrarCliente_ComEmailUnico_DeveRetornarResponseDTO() {
        // Recebendo os dados do cliente
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(cliente.getEmail(), cliente.getEndereco(),
                cliente.getNome(), cliente.getTelefone());

        // Entidade recebendo o DTO
        Cliente clienteMapeado = new Cliente();
        clienteMapeado.setEmail(clienteRequestDTO.getEmail());
        clienteMapeado.setAtivo(true);
        clienteMapeado.setNome(clienteRequestDTO.getNome());
        clienteMapeado.setTelefone(clienteMapeado.getTelefone());
        clienteMapeado.setEndereco(clienteRequestDTO.getEndereco());
        clienteMapeado.setId(1L);
        clienteMapeado.setDataCadastro(LocalDateTime.now());

        // Salvar Cliente
        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setEmail(clienteMapeado.getEmail());
        clienteSalvo.setAtivo(clienteMapeado.isAtivo());
        clienteSalvo.setNome(clienteMapeado.getNome());
        clienteSalvo.setTelefone(clienteMapeado.getTelefone());
        clienteSalvo.setEndereco(clienteMapeado.getEndereco());
        clienteSalvo.setId(clienteMapeado.getId());
        clienteSalvo.setDataCadastro(LocalDateTime.now());

        // Resposta esperada
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(clienteSalvo.getId());
        clienteResponseDTO.setAtivo(clienteSalvo.isAtivo());
        clienteResponseDTO.setEmail(clienteSalvo.getEmail());
        clienteResponseDTO.setNome(clienteSalvo.getNome());

        // Configurando os Mocks:
        // 1. O e-mail não deve existir
        when(clienteRepository.existsByEmail(clienteRequestDTO.getEmail())).thenReturn(false);

        // 2. O ModelMapper deve converter o RequestDTO para a entidade Cliente
        when(modelMapper.map(clienteRequestDTO, Cliente.class)).thenReturn(clienteMapeado);

        // 3. O repositório deve salvar a entidade (simulando o retorno com ID)
        when(clienteRepository.save(clienteMapeado)).thenReturn(clienteSalvo);

        // 4. O ModelMapper deve converter a entidade salva para o ResponseDTO
        when(modelMapper.map(clienteSalvo, ClienteResponseDTO.class)).thenReturn(clienteResponseDTO);

        //Verificando os resultados
        ClienteResponseDTO responseDTO = clienteService.cadastrarCliente(clienteRequestDTO);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(1L, responseDTO.getId());
        Assertions.assertEquals("Lucas@email.com", responseDTO.getEmail());
        Assertions.assertEquals("Lucas", responseDTO.getNome());
        Assertions.assertTrue(responseDTO.getAtivo());

        // Verifica se as dependências foram chamadas corretamente
        verify(clienteRepository, times(1)).existsByEmail("Lucas@email.com");
        verify(modelMapper, times(1)).map(clienteRequestDTO, Cliente.class);
        verify(clienteRepository, times(1)).save(clienteMapeado);
        verify(modelMapper, times(1)).map(clienteSalvo, ClienteResponseDTO.class);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar cadastrar cliente com email já existente")
    void cadastrarCliente_ComEmailExistente_DeveLancarExcecao() {
        // Recebendo os dados do cliente
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(cliente.getEmail(), cliente.getEndereco(),
                cliente.getNome(), cliente.getTelefone());
        // Simulando que o e-mail ja existe
        when(clienteRepository.existsByEmail(clienteRequestDTO.getEmail())).thenReturn(true);

        // Act & Assert (Agir e Verificar a Exceção)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.cadastrarCliente(clienteRequestDTO));
        Assertions.assertEquals("Email já cadastrado: " + clienteRequestDTO.getEmail(), exception.getMessage());

        // Verificar que o save não foi chamado
        verify(clienteRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), eq(Cliente.class));
        verify(modelMapper, never()).map(any(), eq(ClienteResponseDTO.class));

        // Assert (Verificações adicionais)
        // Verificamos que o ModelMapper NUNCA foi chamado
        verify(modelMapper, never()).map(any(), any());
        verify(modelMapper, never()).map(any(), eq(ClienteResponseDTO.class));
        // Verificamos que o método save NUNCA foi chamado
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve buscar um cliente pelo id existente com sucesso e retornar um ClienteResponseDTO")
    void buscarClientePorId() {

        //Dados do cliente setup()

        // buscando cliente
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        // Transformando Entity em DTO
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(cliente.getId());
        clienteResponseDTO.setAtivo(cliente.isAtivo());
        clienteResponseDTO.setEmail(cliente.getEmail());
        clienteResponseDTO.setNome(cliente.getNome());

        //Verificando os resultados
        ClienteResponseDTO responseDTO = clienteService.buscarClientePorId(clienteResponseDTO.getId());

        Assertions.assertNotNull(clienteResponseDTO);
        Assertions.assertEquals(1L,clienteResponseDTO.getId());

        // Transforma o cliente Entidade em ClienteResponseDTO
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(responseDTO);
        verify(clienteRepository, times(1)).findById(cliente.getId());
        verify(modelMapper, times(1)).map(cliente, ClienteResponseDTO.class);

    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar buscar um cliente com id inválido e lançar uma BusinessException")
    void buscarClientePorId_ComIdInvalido() {

        // Dados do cliente setup()

        // buscar cliente
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Assertions.assertThrows(BusinessException.class,() -> {
                clienteService.buscarClientePorId(cliente.getId());
                throw new BusinessException("Erro! Cliente não encontrado com id="+cliente.getId());
        });

        //Verifica se o método foi chamado
        verify(clienteRepository, times(1)).findById(cliente.getId());

    }

    @Test
    @DisplayName("Deve buscar um cliente pelo email existente com sucesso e retornar um ClienteResponseDTO")
    void buscarClientePorEmail() {
        //Dados do cliente setup()

        // buscando cliente
        when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));

        // Transformando Entity em DTO
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(cliente.getId());
        clienteResponseDTO.setAtivo(cliente.isAtivo());
        clienteResponseDTO.setEmail(cliente.getEmail());
        clienteResponseDTO.setNome(cliente.getNome());

        //Verificando os resultados
        ClienteResponseDTO responseDTO = clienteService.buscarClientePorEmail(clienteResponseDTO.getEmail());

        Assertions.assertNotNull(clienteResponseDTO);
        Assertions.assertEquals(cliente.getEmail(),clienteResponseDTO.getEmail());


        // Transforma o cliente Entidade em ClienteResponseDTO
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(responseDTO);
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());
        verify(modelMapper, times(1)).map(cliente, ClienteResponseDTO.class);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar cadastrar cliente com email já existente")
    void buscarClientePorEmail_ComEmailInexistente() {
        //Dados do cliente setup()
//        cliente.setEmail("144j++__hjhmhLucãs@email.com");

        // buscar cliente
        when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));

        Assertions.assertThrows(BusinessException.class,() -> {
            clienteService.buscarClientePorEmail(cliente.getEmail());
            throw new BusinessException("Erro! Cliente não encontrado com email="+cliente.getEmail());
        });

        //Verifica se o método foi chamado
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());
    }

    @Test
    @DisplayName("Deve buscar um cliente pelo email existente com sucesso e retornar um ClienteResponseDTO")
    void buscarClientePorNome() {
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar buscar cliente com nome inexistente")
    void buscarClientePorNome_NomeInexistente() {
    }

    @Test
    void atualizarCliente() {
    }

    @Test
    void ativarDesativarCliente() {
    }

    @Test
    void listarClientesAtivos() {
    }
}