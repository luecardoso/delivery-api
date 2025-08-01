package com.deliverytech.delivery.validation;

import com.deliverytech.delivery.validation.annotation.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    private static final Pattern CPF_PATTERN = Pattern
            .compile("^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$");


    @Override
    public void initialize(ValidCPF constraintAnnotation) {
        // Inicialização se necessária
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.trim().isEmpty()) {
            return false;  // CPF nulo ou vazio é inválido
        }

        // Verifica se o valor corresponde ao formato regex (opcional, mas útil para filtragem inicial)
        Matcher matcher = CPF_PATTERN.matcher(value);
        if (!matcher.matches()) {
            return false;  // Não corresponde ao formato esperado
        }

        // Remove todos os caracteres não numéricos
        String cpf = value.replaceAll("[^0-9]", "");  // Mantém apenas dígitos

        if (cpf.length() != 11) {
            return false;  // Deve ter exatamente 11 dígitos
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11 é inválido)
        boolean allEqual = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            return false;  // Todos dígitos iguais são inválidos
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int remainder = sum % 11;
        int digit1 = (remainder < 2) ? 0 : 11 - remainder;

        if (Character.getNumericValue(cpf.charAt(9)) != digit1) {
            return false;  // Primeiro dígito verificador inválido
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        remainder = sum % 11;
        int digit2 = (remainder < 2) ? 0 : 11 - remainder;

        if (Character.getNumericValue(cpf.charAt(10)) != digit2) {
            return false;  // Segundo dígito verificador inválido
        }

        return true;  // CPF é válido
    }
}
