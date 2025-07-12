package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery.dto.response.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO cadastrarCliente(ClienteRequestDTO cliente);
    ClienteResponseDTO buscarClientePorId(Long id);
    ClienteResponseDTO buscarClientePorEmail(String email);
    List<ClienteResponseDTO> buscarClientePorNome(String nome);
    ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO clienteRequestDTO);
    ClienteResponseDTO ativarDesativarCliente(Long id);
    List<ClienteResponseDTO> listarClientesAtivos();
}