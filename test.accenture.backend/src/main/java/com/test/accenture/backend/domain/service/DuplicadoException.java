package com.test.accenture.backend.domain.service;

public class DuplicadoException extends RuntimeException {
    public DuplicadoException(String mensaje) {
        super(mensaje);
    }
} 