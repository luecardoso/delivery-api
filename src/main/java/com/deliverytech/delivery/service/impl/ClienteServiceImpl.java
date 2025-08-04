package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery.dto.response.ClienteResponseDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.exceptions.BusinessException;
import com.deliverytech.delivery.exceptions.EntityNotFoundException;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
         //Validar email único
        if (clienteRepository.existsByEmail(clienteRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + clienteRequestDTO.getEmail());
        }
        if(clienteRepository.existsByCpf(clienteRequestDTO.getCpf())){
            throw new IllegalArgumentException("CPF já cadastrado " + clienteRequestDTO.getCpf());
        }
        // Converter DTO para entidade
        Cliente cliente = modelMapper.map(clienteRequestDTO, Cliente.class);
        cliente.setAtivo(true);
        // Salvar cliente
        Cliente clienteSalvo = clienteRepository.save(cliente);
        // Retornar DTO de resposta
        return modelMapper.map(clienteSalvo, ClienteResponseDTO.class);
    }

    @Override
    @Cacheable(value = "cliente")
    public ClienteResponseDTO buscarClientePorId(Long id) {
        // Buscar cliente por ID
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        // Converter entidade para DTO
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    @Cacheable(value = "cliente")
    public ClienteResponseDTO buscarClientePorEmail(String email) {
        // Buscar cliente por email
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com email: " + email));
        // Converter entidade para DTO
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    @Cacheable(value = "cliente")
    public List<ClienteResponseDTO> buscarClientePorNome(String nome) {
        // Buscar clientes por nome
        List<Cliente> clientes = clienteRepository.findByNomeContainingIgnoreCase(nome);
        // Converter lista de entidades para lista de DTOs
        return clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
                .toList();
    }

    @Override
    public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO clienteRequestDTO) {
        // Buscar cliente existente
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        // Validar dados do cliente
        if (clienteRequestDTO.getNome() == null || clienteRequestDTO.getNome().isEmpty()) {
            throw new EntityNotFoundException("Nome do cliente é obrigatório");
        }
        if (clienteRequestDTO.getEmail() == null || clienteRequestDTO.getEmail().isEmpty()) {
            throw new EntityNotFoundException("Email do cliente é obrigatório");
        }
        // Atualizar dados do cliente
        clienteExistente.setNome(clienteRequestDTO.getNome());
        clienteExistente.setEmail(clienteRequestDTO.getEmail());
        clienteExistente.setTelefone(clienteRequestDTO.getTelefone());
        clienteExistente.setEndereco(clienteRequestDTO.getEndereco());
        // Salvar cliente atualizado
        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        // Retornar DTO de resposta
        return modelMapper.map(clienteAtualizado, ClienteResponseDTO.class);
    }

    @Override
    public ClienteResponseDTO ativarDesativarCliente(Long id) {
        // Buscar cliente existente
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        // Inverter status de ativo
        clienteExistente.setAtivo(!clienteExistente.isAtivo());
        // Salvar cliente atualizado
        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        // Retornar DTO de resposta
        return modelMapper.map(clienteAtualizado, ClienteResponseDTO.class);
    }

    @Override
    @Cacheable(value = "cliente")
    public List<ClienteResponseDTO> listarClientesAtivos() {
        // Buscar clientes ativos
        List<Cliente> clientesAtivos = clienteRepository.findByAtivoTrue();
        // Converter lista de entidades para lista de DTOs
        return clientesAtivos.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
                .toList();
    }

    @Override
    @Cacheable(value = "cliente")
    public Page<ClienteResponseDTO> listarAtivosPaginado(Pageable pageable) {
        Page<Cliente> clientePage = clienteRepository.findByAtivoTrue(pageable);
        return clientePage.map(clientes -> modelMapper.map(clientes, ClienteResponseDTO.class));

    }

    @Override
    public ResponseEntity<Void> deletarCliente(Long id) {
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Cacheable(value = "cliente")
    public ClienteResponseDTO buscarPorCPF(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("CPF já cadastrado"));
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }
}
