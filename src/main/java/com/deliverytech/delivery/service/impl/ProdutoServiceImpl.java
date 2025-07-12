package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.request.ProdutoRequestDTO;
import com.deliverytech.delivery.dto.response.ProdutoResponseDTO;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.exceptions.BusinessException;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoRequestDTO) {
        // Converter DTO para entidade
        Produto produto = modelMapper.map(produtoRequestDTO, Produto.class);
        produto.setRestaurante(restauranteRepository.findById(produtoRequestDTO.getRestauranteId()).get());
        // Salvar cliente
        Produto produtoSalvo = produtoRepository.save(produto);
        // Retornar DTO de resposta
        return modelMapper.map(produtoSalvo, ProdutoResponseDTO.class);
    }

    @Override
    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        // Buscar produto por ID
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        // Converter entidade para DTO
        return modelMapper.map(produto, ProdutoResponseDTO.class);
    }

    @Override
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
        // Buscar produto existente
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        Optional<Restaurante> restaurante = restauranteRepository.findById(produtoRequestDTO.getRestauranteId());
        // Validar dados do produto
        if (produtoRequestDTO.getNome() == null || produtoRequestDTO.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (produtoRequestDTO.getDescricao() == null || produtoRequestDTO.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto é obrigatória");
        }
        if (produtoRequestDTO.getPreco() == null || produtoRequestDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero");
        }
        if (produtoRequestDTO.getCategoria() == null || produtoRequestDTO.getCategoria().isEmpty()) {
            throw new IllegalArgumentException("Categoria do produto é obrigatória");
        }
        // Atualizar dados do produto
        produtoExistente.setNome(produtoRequestDTO.getNome());
        produtoExistente.setDescricao(produtoRequestDTO.getDescricao());
        produtoExistente.setPreco(produtoRequestDTO.getPreco());
        produtoExistente.setCategoria(produtoRequestDTO.getCategoria());
        produtoExistente.setDisponivel(produtoRequestDTO.getDisponivel());
        produtoExistente.setRestaurante(restaurante.get());
        // Salvar produto atualizado
        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        // Retornar DTO de resposta
        return modelMapper.map(produtoAtualizado, ProdutoResponseDTO.class);
    }

    @Override
    public ProdutoResponseDTO ativarDesativarProduto(Long id) {
        // Buscar produto existente
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        // Inverter disponibilidade do produto
        produto.setDisponivel(!produto.getDisponivel());
        // Salvar produto atualizado
        Produto produtoAtualizado = produtoRepository.save(produto);
        // Retornar DTO de resposta
        return modelMapper.map(produtoAtualizado, ProdutoResponseDTO.class);
    }

    @Override
    public ProdutoResponseDTO alterarDisponibilidade(Long id, Boolean disponivel) {
        //Buscar produto por ID
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + id));

        // Inverter disponibilidade do produto
        produto.setDisponivel(!produto.getDisponivel());
        // Salvar produto atualizado
        Produto produtoAtualizado = produtoRepository.save(produto);
        // Retornar DTO de resposta
        return modelMapper.map(produtoAtualizado, ProdutoResponseDTO.class);

    }

    @Override
    public List<ProdutoResponseDTO> buscarProdutosPorNome(String nome) {
        // Buscar produtos por restaurante ID
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCaseAndDisponivelTrue(nome);
        if (produtos.isEmpty() || produtos.stream().noneMatch(Produto::getDisponivel)) {
            throw new BusinessException("Nenhum produto encontrado pcom o nome: " + nome);
        }
        // Converter lista de entidades para lista de DTOs
        return produtos.stream()
                .filter(Produto::getDisponivel) // Filtrar apenas produtos disponíveis
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> buscarProdutosPorRestaurante(Long restauranteId) {
        // Buscar produtos por restaurante ID
        List<Produto> produtos = produtoRepository.findByRestauranteIdAndDisponivelTrue(restauranteId);
        if (produtos.isEmpty() || produtos.stream().noneMatch(Produto::getDisponivel)) {
            throw new BusinessException("Nenhum produto encontrado para o restaurante ID: " + restauranteId);
        }
        // Converter lista de entidades para lista de DTOs
        return produtos.stream()
                .filter(Produto::getDisponivel) // Filtrar apenas produtos disponíveis
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(String categoria) {
        // Buscar produtos por categoria
        List<Produto> produtos = produtoRepository.findByCategoriaAndDisponivelTrue(categoria);
        if (produtos.isEmpty()) {
            throw new BusinessException("Nenhum produto encontrado para a categoria: " + categoria);
        }
        // Converter lista de entidades para lista de DTOs
        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> buscarProdutosPorFaixaPreco(BigDecimal precoMinimo,
                                                                BigDecimal precoMaximo) {
        // Buscar produtos por faixa de preço
        List<Produto> produtos = produtoRepository.findByPrecoLessThanEqualAndDisponivelTrue(precoMaximo);
        if (produtos.isEmpty()) {
            throw new BusinessException("Nenhum produto encontrado na faixa de preço: " + precoMinimo + " a " + precoMaximo);
        }
        // Converter lista de entidades para lista de DTOs
        return produtos.stream()
                .filter(produto -> produto.getPreco().compareTo(precoMinimo) >= 0)
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> buscarTodosProdutos() {
        // Buscar todos os produtos
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            throw new BusinessException("Nenhum produto encontrado");
        }
        // Converter lista de entidades para lista de DTOs
        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> buscarPorPrecoMenorOuIgual(BigDecimal valor) {
        // Buscar produtos com preço menor ou igual ao valor especificado
        List<Produto> produtos = produtoRepository.findByPrecoLessThanEqualAndDisponivelTrue(valor);
        if (produtos.isEmpty()) {
            throw new BusinessException("Nenhum produto encontrado com preço menor ou igual a: " + valor);
        }
        // Converter lista de entidades para lista de DTOs
        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }
}
