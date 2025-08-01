package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery.dto.response.ClienteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO cadastrarCliente(ClienteRequestDTO cliente);
    ClienteResponseDTO buscarClientePorId(Long id);
    ClienteResponseDTO buscarClientePorEmail(String email);
    List<ClienteResponseDTO> buscarClientePorNome(String nome);
    ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO clienteRequestDTO);
    ClienteResponseDTO ativarDesativarCliente(Long id);
    List<ClienteResponseDTO> listarClientesAtivos();

    // Buscar clientes ativos com paginação
    Page<ClienteResponseDTO> listarAtivosPaginado(Pageable pageable);

    ResponseEntity<Void> deletarCliente(Long id);

    ClienteResponseDTO buscarPorCPF(String cpf);
}