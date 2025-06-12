package com.test.accenture.backend.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarSucursalDTO {
    
    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long id;
    
    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    private String nombre;
} 