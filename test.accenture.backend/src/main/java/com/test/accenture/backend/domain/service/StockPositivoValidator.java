package com.test.accenture.backend.domain.service;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StockPositivoValidator implements ConstraintValidator<StockPositivo, Integer> {
    
    @Override
    public void initialize(StockPositivo constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(Integer stock, ConstraintValidatorContext context) {
        if (stock == null) {
            return true; // Permitir null, la validación @NotNull se encarga de esto
        }
        return stock >= 0;
    }
} 