package com.test.accenture.backend.application.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    private String nombre;
    
    @NotNull(message = "La sucursal es obligatoria")
    private Long sucursalId;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Max(value = 999999, message = "El stock no puede exceder 999,999 unidades")
    private Integer stock;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
    private BigDecimal precio;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;
    
    @Size(max = 50, message = "El código del producto no puede exceder 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9\\-_]*$", 
             message = "El código solo puede contener letras, números, guiones y guiones bajos")
    private String codigoProducto;
    
    private Boolean activo;
} 