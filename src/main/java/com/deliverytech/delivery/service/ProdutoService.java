package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.request.ProdutoRequestDTO;
import com.deliverytech.delivery.dto.response.ProdutoResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoService {

    ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoRequestDTO);
    ProdutoResponseDTO buscarProdutoPorId(Long id);
    ProdutoResponseDTO  atualizarProduto(Long id, ProdutoRequestDTO  produtoRequestDTO);
    ProdutoResponseDTO ativarDesativarProduto(Long id);
    ProdutoResponseDTO alterarDisponibilidade(Long id, Boolean disponivel);
    List<ProdutoResponseDTO > buscarProdutosPorNome(String nome);
    List<ProdutoResponseDTO > buscarProdutosPorRestaurante(Long restauranteId);
    List<ProdutoResponseDTO > buscarProdutosPorCategoria(String categoria);
    List<ProdutoResponseDTO > buscarProdutosPorFaixaPreco(BigDecimal precoMinimo,
                                                          BigDecimal precoMaximo);
    List<ProdutoResponseDTO> buscarTodosProdutos();
    List<ProdutoResponseDTO> buscarPorPrecoMenorOuIgual(BigDecimal valor);
}

