package com.test.accenture.backend.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarProductoDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private Long id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    private String nombre;
} 