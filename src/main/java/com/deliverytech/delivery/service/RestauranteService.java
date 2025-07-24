package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.request.RestauranteRequestDTO;
import com.deliverytech.delivery.dto.response.RestauranteResponseDTO;
import com.deliverytech.delivery.repository.projection.RelatorioVendas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteService {

    RestauranteResponseDTO cadastrarRestaurante(RestauranteRequestDTO restauranteRequestDTO);
    RestauranteResponseDTO buscarRestaurantePorId(Long id);
    RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteRequestDTO restauranteRequestDTO);
    RestauranteResponseDTO ativarDesativarRestaurante(Long id);
    RestauranteResponseDTO buscarRestaurantePorNome(String nome);
    List<RestauranteResponseDTO> buscarRestaurantePorCategoria(String categoria);
    List<RestauranteResponseDTO> buscarRestaurantePorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo);
//    Page<RestauranteResponseDTO> buscarRestaurantesPaginado(String categoria, Boolean ativo, Pageable pageable);
    List<RestauranteResponseDTO> buscarRestaurantesAtivos();
    List<RestauranteResponseDTO> buscarTop5RestaurantesPorNome();
    List<RelatorioVendas> relatorioVendasPorRestaurante();
    List<RestauranteResponseDTO> buscarPorTaxaEntrega(BigDecimal taxaEntrega);
    BigDecimal calcularTaxaEntrega(Long restauranteId, String cep);

    Page<RestauranteResponseDTO> buscarRestaurantesComPaginacao(String categoria, Boolean ativo, Pageable pageable);
    List<RestauranteResponseDTO> buscarRestaurantesProximos(String cep, Integer raio);

    boolean isOwner(Long restauranteId);
}
