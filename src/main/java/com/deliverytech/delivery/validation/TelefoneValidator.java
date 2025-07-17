package com.deliverytech.delivery.validation;

import jakarta.validation.ConstraintValidator;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    @Override
    public void initialize(ValidTelefone constraintAnnotation) {
        // Inicialização se necessária
    }

    @Override
    public boolean isValid(String telefone, jakarta.validation.ConstraintValidatorContext context) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return false;
        }
        //Remove caracteres especiais e espaços
        String cleanTelefone = telefone.replaceAll("[^\\d]", "");
        //Verifica se tem 10 ou 11 dígitos
        return cleanTelefone.length() == 10 || cleanTelefone.length() == 11;
    }

}
