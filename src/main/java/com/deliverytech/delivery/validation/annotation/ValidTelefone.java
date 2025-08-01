package com.deliverytech.delivery.validation.annotation;

import com.deliverytech.delivery.validation.TelefoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTelefone {
    String message() default "Telefone deve ter formato válido (10 ou 11 digitos)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
