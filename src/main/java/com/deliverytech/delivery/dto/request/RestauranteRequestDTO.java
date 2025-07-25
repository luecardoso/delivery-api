package com.deliverytech.delivery.dto.request;

import com.deliverytech.delivery.validation.ValidCategoria;
import com.deliverytech.delivery.validation.ValidTelefone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Dados necessários para criar ou atualizar um restaurante", title = "Restaurante Request DTO")
public class RestauranteRequestDTO {

    @Schema(description = "Nome do restaurante", example = "Pizzaria Express", required = true)
    @NotBlank(message = "O nome do restaurante é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do restaurante deve ter entre 3 e 100 caracteres")
    private String nome;

    //allowableValues = {"Italiana", "Brasileira", "Japonesa", "Mexicana", "Árabe"})
    @Schema(description = "Descrição do restaurante", example = "Melhores pizzas da cidade", required = true)
    @NotBlank(message = "A categoria do restaurante é obrigatória")
    @ValidCategoria
    private String categoria;

    @Schema(description = "Endereço completo do restaurante", example = "Rua das Flores, 123 - Centro", required = true)
    @NotBlank(message = "O endereço do restaurante é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @Schema(description = "Telefone do restaurante", example = "11999999999", required = true)
    @NotBlank(message = "telefone é obrigatório")
    @Pattern(regexp = "^\\+?[0-9]{10,11}$", message = "O telefone deve ser um número válido com 10 a 11 dígitos")
    @ValidTelefone
    private String telefone;

    @Schema(description = "Taxa de entrega do restaurante", example = "5.00", minimum = "0", required = true)
    @NotNull(message = "A taxa de entrega do restaurante é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false,message = "Taxa de entrega deve ser posiꢀva")
    @DecimalMax(value = "50.0", message = "Taxa de entrega não pode exceder R$ 50,00")
    private BigDecimal taxaEntrega;

    @Schema(description = "Avaliação do restaurante", example = "4.5")
    private BigDecimal avaliacao;

    @Schema(description = "Status do restaurante", example = "true", required = true)
    @NotNull(message = "O status do restaurante é obrigatório")
    private Boolean ativo = true;

    @Schema(description = "Tempo esꢀmado de entrega em minutos", example = "45", minimum = "10", maximum = "120")
    @NotNull(message = "Tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    private Integer tempoEntregaMinutos;

    @Schema(description = "Horário de funcionamento", example = "08:00-22:00")
    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String horarioFuncionamento;

//    @Schema(description = "CEP do restaurante", example = "12345-678", required = true)
//    @NotNull(message = "O CEP do restaurante é obrigatório")
//    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "O CEP deve estar no formato 12345-678 ou 12345678")
//    private String cep;

    public RestauranteRequestDTO() {
    }

    public RestauranteRequestDTO(Boolean ativo,  String nome,String categoria,String endereco,String telefone,
                                 BigDecimal taxaEntrega, Integer tempoEntregaMinutos,String horarioFuncionamento) {
        this.ativo = ativo;
        this.categoria = categoria;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.nome = nome;
        this.taxaEntrega = taxaEntrega;
        this.telefone = telefone;
        this.tempoEntregaMinutos = tempoEntregaMinutos;
    }

    public @NotNull(message = "O status do restaurante é obrigatório") Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(@NotNull(message = "O status do restaurante é obrigatório") Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        this.avaliacao = avaliacao;
    }

    public @NotBlank(message = "A categoria do restaurante é obrigatória") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotBlank(message = "A categoria do restaurante é obrigatória") String categoria) {
        this.categoria = categoria;
    }

    public @NotBlank(message = "O endereço do restaurante é obrigatório") @Size(max = 200,
            message = "Endereço deve ter no máximo 200 caracteres") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank(message = "O endereço do restaurante é obrigatório")
                            @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres") String endereco) {
        this.endereco = endereco;
    }

    public @NotBlank(message = "Horário de funcionamento é obrigatório") String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(@NotBlank(message = "Horário de funcionamento é obrigatório") String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public @NotBlank(message = "O nome do restaurante é obrigatório") @Size(min = 3, max = 100,
            message = "O nome do restaurante deve ter entre 3 e 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome do restaurante é obrigatório")
                        @Size(min = 3, max = 100, message = "O nome do restaurante deve ter entre 3 e 100 caracteres") String nome) {
        this.nome = nome;
    }

    public @NotNull(message = "A taxa de entrega do restaurante é obrigatória")
    @DecimalMin(value = "0.0", message = "Taxa de entrega deve ser posiꢀva") BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(@NotNull(message = "A taxa de entrega do restaurante é obrigatória")
                               @DecimalMin(value = "0.0", message = "Taxa de entrega deve ser posiꢀva") BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public @NotBlank(message = "telefone é obrigatório") @Pattern(regexp = "^\\+?[0-9]{10,11}$",
            message = "O telefone deve ser um número válido com 10 a 11 dígitos") String getTelefone() {
        return telefone;
    }

    public void setTelefone(@NotBlank(message = "telefone é obrigatório")
                            @Pattern(regexp = "^\\+?[0-9]{10,11}$", message = "O telefone deve ser um número válido com 10 a 11 dígitos") String telefone) {
        this.telefone = telefone;
    }

    public @NotNull(message = "Tempo de entrega é obrigatório") @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    Integer getTempoEntregaMinutos() {
        return tempoEntregaMinutos;
    }

    public void setTempoEntregaMinutos(@NotNull(message = "Tempo de entrega é obrigatório")
                                       @Min(value = 10, message = "Tempo mínimo é 10 minutos")
                                       @Max(value = 120, message = "Tempo máximo é 120 minutos") Integer tempoEntregaMinutos) {
        this.tempoEntregaMinutos = tempoEntregaMinutos;
    }

    @Override
    public String toString() {
        return "RestauranteRequestDTO{" +
                "ativo=" + getAtivo() +
                ", nome='" + getNome() + '\'' +
                ", categoria='" + getCategoria() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", taxaEntrega=" + getTaxaEntrega() +
                ", avaliacao=" + getAvaliacao() +
                ", tempoEntregaMinutos=" + getTempoEntregaMinutos() +
//                ", cep='" + getCep() + '\'' +
                ", horarioFuncionamento='" + getHorarioFuncionamento() + '\'' +
                '}';
    }
}
