package com.test.accenture.backend.application.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SucursalDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    private String nombre;
    
    @NotNull(message = "La franquicia es obligatoria")
    private Long franquiciaId;
    
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String direccion;
    
    @Pattern(regexp = "^[\\d\\s\\-\\+\\(\\)]{7,20}$", 
             message = "El teléfono debe tener un formato válido")
    private String telefono;
    
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    private Boolean activo;
} 