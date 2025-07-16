package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.request.RestauranteRequestDTO;
import com.deliverytech.delivery.dto.response.RestauranteResponseDTO;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.exceptions.BusinessException;
import com.deliverytech.delivery.exceptions.EntityNotFoundException;
import com.deliverytech.delivery.projection.RelatorioVendas;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.RestauranteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RestauranteResponseDTO cadastrarRestaurante(RestauranteRequestDTO restauranteRequestDTO) {
        // Validar nome único
        Optional<Restaurante> nomeRestaurante = restauranteRepository.findByNome(restauranteRequestDTO.getNome());
        if (nomeRestaurante.equals(restauranteRequestDTO.getNome())) {
            throw new BusinessException("Restaurante já cadastrado: " + restauranteRequestDTO.getNome());
        }
        // Converter DTO para entidade
        Restaurante restaurante = modelMapper.map(restauranteRequestDTO, Restaurante.class);
        // Salvar cliente
        Restaurante restauranteSalvo = restauranteRepository.save(restaurante);
        // Retornar DTO de resposta
        return modelMapper.map(restauranteSalvo, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO buscarRestaurantePorId(Long id) {
        // Buscar restaurante por ID
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com ID: " + id));
        // Converter entidade para DTO
        return modelMapper.map(restaurante, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteRequestDTO restauranteRequestDTO) {
        // Buscar restaurante existente
        Restaurante restauranteExistente = restauranteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: " + id));

        // Atualizar campos do restaurante
        restauranteExistente.setNome(restauranteRequestDTO.getNome());
        restauranteExistente.setCategoria(restauranteRequestDTO.getCategoria());
        restauranteExistente.setTelefone(restauranteRequestDTO.getTelefone());
        restauranteExistente.setAvaliacao(restauranteRequestDTO.getAvaliacao());
        restauranteExistente.setEndereco(restauranteRequestDTO.getEndereco());
        restauranteExistente.setTelefone(restauranteRequestDTO.getTelefone());

        // Salvar as alterações
        Restaurante restauranteAtualizado = restauranteRepository.save(restauranteExistente);

        // Retornar DTO atualizado
        return modelMapper.map(restauranteAtualizado, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO ativarDesativarRestaurante(Long id) {
        // Buscar restaurante por ID
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: " + id));

        // Verificar se o restaurante já está inativo
        if (!restaurante.isAtivo()) {
            throw new BusinessException("Restaurante já está inativo: " + restaurante.getNome());
        }
        // Alternar status de ativo/desativado
        restaurante.setAtivo(!restaurante.isAtivo());
        // Salvar as alterações
        Restaurante restauranteAtualizado = restauranteRepository.save(restaurante);
        // Retornar DTO atualizado
        return modelMapper.map(restauranteAtualizado, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO buscarRestaurantePorNome(String nome) {
        // Buscar restaurante por nome
        Restaurante restaurante = restauranteRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
        if (!restaurante.getNome().equalsIgnoreCase(nome)) {
            throw new BusinessException("Restaurante não encontrado com nome: " + nome);
        }
        else if (!restaurante.isAtivo()) {
            throw new BusinessException("Restaurante está desativado: " + nome);
        }
        // Converter entidade para DTO
        return modelMapper.map(restaurante, RestauranteResponseDTO.class);
    }

    @Override
    public List<RestauranteResponseDTO> buscarRestaurantePorCategoria(String categoria) {
        // Buscar restaurantes por categoria
        List<Restaurante> restaurantes = restauranteRepository.findByCategoriaAndAtivoTrue(categoria);
        if (restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado na categoria: " + categoria);
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> buscarRestaurantePorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        // Buscar restaurantes por taxa de entrega dentro do intervalo
        List<Restaurante> restaurantes = restauranteRepository.findByTaxaEntregaBetween(precoMinimo, precoMaximo);
        if (restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado com taxa de entrega entre " + precoMinimo + " e " + precoMaximo);
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> buscarRestaurantesAtivos() {
        // Buscar todos os restaurantes ativos
        List<Restaurante> restaurantesAtivos = restauranteRepository.findByAtivoTrue();
        if (restaurantesAtivos.isEmpty()) {
            throw new BusinessException("Nenhum restaurante ativo encontrado.");
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantesAtivos.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> buscarTop5RestaurantesPorNome() {
        // Buscar os 5 primeiros restaurantes por nome
        List<Restaurante> top5Restaurantes = restauranteRepository.findTop5ByOrderByNomeAsc();
        if (top5Restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado.");
        }
        // Converter lista de entidades para lista de DTOs
        return top5Restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public List<RelatorioVendas> relatorioVendasPorRestaurante() {
        // Buscar relatório de vendas por restaurante
        List<RelatorioVendas> relatorio = restauranteRepository.relatorioVendasPorRestaurante();
        if (relatorio.isEmpty()) {
            throw new BusinessException("Nenhum dado de vendas encontrado.");
        }
        // Converter lista de entidades para lista de DTOs
        return relatorio.stream()
                .map(restaurante -> modelMapper.map(restaurante, RelatorioVendas.class))
                .toList();
    }

    @Override
    public List<RestauranteResponseDTO> buscarPorTaxaEntrega(BigDecimal taxaEntrega) {
        // Buscar restaurantes por taxa de entrega
        List<Restaurante> restaurantes = restauranteRepository.findByTaxaEntregaLessThanEqual(taxaEntrega);
        if (restaurantes.isEmpty()) {
            throw new BusinessException("Nenhum restaurante encontrado com taxa de entrega menor ou igual a: " + taxaEntrega);
        }
        // Converter lista de entidades para lista de DTOs
        return restaurantes.stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .toList();
    }

    @Override
    public BigDecimal calcularTaxaEntrega(Long restauranteId, String cep) {
        return null;
    }

    @Override
    public Page<RestauranteResponseDTO> buscarRestaurantesComPaginacao(String categoria, Boolean ativo, Pageable pageable) {
        Page<Restaurante> restaurantes = restauranteRepository.findByFilters(categoria, ativo, pageable);
        return restaurantes.map(this::converterParaDTO);
    }

    @Override
    public List<RestauranteResponseDTO> buscarRestaurantesProximos(String cep, Integer raio) {
        return List.of();
    }

    private RestauranteResponseDTO converterParaDTO(Restaurante restaurante) {
        RestauranteResponseDTO dto = new RestauranteResponseDTO();
        dto.setId(restaurante.getId());
        dto.setNome(restaurante.getNome());
        dto.setCategoria(restaurante.getCategoria());
        dto.setAtivo(restaurante.isAtivo());
        return dto;
    }
}
