package com.test.accenture.backend.domain.service;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StockPositivoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StockPositivo {
    String message() default "El stock debe ser un número positivo o cero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 