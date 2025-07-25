package com.deliverytech.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "DTO para requisição de produtos", title = "Produto Request DTO")
public class ProdutoRequestDTO {

    @Schema(description = "Nome do produto", example = "Pizza Margherita", required = true)
    @NotNull(message = "O Nome do produto é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Deliciosa pizza com molho de tomate, mussarela e manjericão", required = true)
    @NotNull(message = "A descrição do produto é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    private String descricao;

    @Schema(description = "Preço do produto", example = "29.90", required = true)
    @NotNull(message = "O preço do produto é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @DecimalMax(value = "500.00", message = "Preço não pode exceder R$ 500,00")
    private BigDecimal preco;

    @Schema(description = "Categoria do produto", example = "Pizzas", required = true)
    @NotNull(message = "A categoria do produto é obrigatória")
    private String categoria;

    @Schema(description = "Disponibilidade do produto", example = "true", required = true)
    @NotNull(message = "A disponibilidade do produto é obrigatória")
    @AssertTrue(message = "Produto deve estar disponível por padrão")
    private Boolean disponivel;

    @Schema(description = "ID do restaurante", example = "1", required = true)
    @NotNull(message = "O restaurante é obrigatório")
    @Positive(message = "Restaurante ID deve ser positivo")
    private Long restauranteId;

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif)$",
            message = "URL da imagem deve ser válida e ter formato JPG, JPEG, PNG ou GIF")
    private String imagemUrl;

    public ProdutoRequestDTO() {
    }

    public ProdutoRequestDTO(String categoria, String descricao, Boolean disponivel, String nome, BigDecimal preco, Long restauranteId) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.nome = nome;
        this.preco = preco;
        this.restauranteId = restauranteId;
    }

    public @NotNull(message = "A categoria do produto é obrigatória") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotNull(message = "A categoria do produto é obrigatória") String categoria) {
        this.categoria = categoria;
    }

    public @NotNull(message = "A descrição do produto é obrigatória") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull(message = "A descrição do produto é obrigatória") String descricao) {
        this.descricao = descricao;
    }

    public @NotNull(message = "A disponibilidade do produto é obrigatória") Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(@NotNull(message = "A disponibilidade do produto é obrigatória") Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public @NotNull(message = "O Nome do produto é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@NotNull(message = "O Nome do produto é obrigatório") String nome) {
        this.nome = nome;
    }

    public @NotNull(message = "O preço do produto é obrigatório") BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(@NotNull(message = "O preço do produto é obrigatório") BigDecimal preco) {
        this.preco = preco;
    }

    public @NotNull(message = "O restaurante é obrigatório") Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(@NotNull(message = "O restaurante é obrigatório") Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif)$",
            message = "URL da imagem deve ser válida e ter formato JPG, JPEG, PNG ou GIF") String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(@Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif)$",
            message = "URL da imagem deve ser válida e ter formato JPG, JPEG, PNG ou GIF") String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
