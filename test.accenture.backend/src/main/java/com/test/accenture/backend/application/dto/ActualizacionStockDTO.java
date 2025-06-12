package com.test.accenture.backend.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import com.test.accenture.backend.domain.model.HistorialStock.TipoMovimiento;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizacionStockDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;
    
    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimiento tipoMovimiento;
    
    @Size(max = 255, message = "El motivo no puede exceder 255 caracteres")
    private String motivo;
    
    @Size(max = 100, message = "El usuario no puede exceder 100 caracteres")
    private String usuario;
} 