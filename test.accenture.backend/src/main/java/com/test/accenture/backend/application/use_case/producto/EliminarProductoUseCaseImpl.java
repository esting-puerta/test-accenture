package com.test.accenture.backend.application.use_case.producto;

import com.test.accenture.backend.domain.repository.ProductoRepository;
import com.test.accenture.backend.domain.service.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EliminarProductoUseCaseImpl implements EliminarProductoUseCase {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public void ejecutar(Long productoId) {
        productoRepository.findById(productoId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Producto no encontrado con ID: " + productoId));
        productoRepository.deleteById(productoId);
    }
} 