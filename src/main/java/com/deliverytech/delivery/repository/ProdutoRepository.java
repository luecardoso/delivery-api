package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos por restaurante
    List<Produto> findByRestauranteAndDisponivelTrue(Restaurante restaurante);

    // Buscar produtos por restaurante ID
    List<Produto> findByRestauranteIdAndDisponivelTrue(Long restauranteId);

    // Buscar por categoria
    List<Produto> findByCategoriaAndDisponivelTrue(String categoria);

    // Buscar por nome contendo
    List<Produto> findByNomeContainingIgnoreCaseAndDisponivelTrue(String nome);

    // Buscar por faixa de preço
    List<Produto> findByPrecoBetweenAndDisponivelTrue(BigDecimal precoMin, BigDecimal precoMax);

    // Buscar produtos mais baratos que um valor
    List<Produto> findByPrecoLessThanEqualAndDisponivelTrue(BigDecimal preco);

    // Ordenar por preço
    List<Produto> findByDisponivelTrueOrderByPrecoAsc();
    List<Produto> findByDisponivelTrueOrderByPrecoDesc();

    // Query customizada - produtos mais vendidos
    @Query(value = "SELECT p.nome, COUNT(ip.produto_id) as quantidade_vendida " +
            "FROM produto p " +
            "LEFT JOIN item_pedido ip ON p.id = ip.produto_id " +
            "GROUP BY p.id, p.nome " +
            "ORDER BY quantidade_vendida DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> produtosMaisVendidos();

    // Buscar por restaurante e categoria
    @Query("SELECT p FROM Produto p WHERE p.restaurante.id = :restauranteId " +
            "AND p.categoria = :categoria AND p.disponivel = true")
    List<Produto> findByRestauranteAndCategoria(@Param("restauranteId") Long restauranteId,
                                                @Param("categoria") String categoria);

    // Contar produtos por restaurante
    @Query("SELECT COUNT(p) FROM Produto p WHERE p.restaurante.id = :restauranteId AND p.disponivel = true")
    Long countByRestauranteId(@Param("restauranteId") Long restauranteId);

    @Override
    void deleteById(Long id);

    @Query("SELECT p FROM Produto p " +
            "WHERE " +
            "(:restauranteId IS NULL OR p.restaurante.id = :restauranteId  ) AND " +
            "(:categoria IS NULL OR p.categoria = :categoria) AND " +
            "(:disponivel IS NULL OR p.disponivel = :disponivel)")
    Page<Produto> listarProdutosComPaginacao(Pageable pageable, @Param("restauranteId") Long restauranteId,
                                             @Param("categoria") String categoria, @Param("disponivel") Boolean disponivel);
}
