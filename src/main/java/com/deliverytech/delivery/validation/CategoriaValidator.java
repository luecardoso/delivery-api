package com.deliverytech.delivery.validation;

import jakarta.validation.ConstraintValidator;

import java.util.Arrays;
import java.util.List;

public class CategoriaValidator implements ConstraintValidator<ValidCategoria, String> {

    private static final List<String> CATEGORIAS_VALIDAS = Arrays.asList(
            "BRASILEIRA", "ITALIANA", "JAPONESA", "CHINESA", "MEXICANA",
            "FAST_FOOD", "PIZZA", "HAMBURGUER", "SAUDAVEL",
            "VEGETARIANA",
            "VEGANA", "DOCES", "BEBIDAS", "LANCHES", "ACAI"
    );

    @Override
    public void initialize(ValidCategoria constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(String categoria, jakarta.validation.ConstraintValidatorContext context) {
        if (categoria == null || categoria.trim().isEmpty()) {
            return false;
        }
        return CATEGORIAS_VALIDAS.contains(categoria.toUpperCase());
    }

}
