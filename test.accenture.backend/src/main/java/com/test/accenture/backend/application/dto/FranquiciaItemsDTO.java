package com.test.accenture.backend.application.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaItemsDTO {
    
    @NotNull
    private Long franquiciaId;
    
    @NotBlank
    private String franquiciaNombre;
    
    @NotNull
    private Long sucursalId;
    
    @NotBlank
    private String sucursalNombre;
    
    @NotNull
    private Long productoId;
    
    @NotBlank
    private String productoNombre;
    
    @Min(0)
    private Integer stock;
    
    @DecimalMin("0.0")
    private BigDecimal precio;
    
    private String descripcion;
    
    private String codigoProducto;
    
    private Boolean activo;
} 