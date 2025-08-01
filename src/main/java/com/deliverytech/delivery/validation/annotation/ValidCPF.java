package com.deliverytech.delivery.validation.annotation;

import com.deliverytech.delivery.validation.CPFValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CPFValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCPF {
    String message() default "CPF deve ter formato válido 12345678909 ou 123.456.789-09)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
