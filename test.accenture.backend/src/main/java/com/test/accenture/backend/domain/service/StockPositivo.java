package com.test.accenture.backend.domain.service;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StockPositivo {
    String message() default "El stock debe ser un número positivo o cero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 