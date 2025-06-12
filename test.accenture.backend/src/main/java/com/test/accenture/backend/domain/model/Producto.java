package com.test.accenture.backend.domain.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "productos", uniqueConstraints = @UniqueConstraint(columnNames = {"nombre", "sucursal_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"sucursal", "historialStock"})
@ToString(exclude = {"sucursal", "historialStock"})
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @NotNull(message = "La sucursal es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", nullable = false)
    @JsonBackReference
    private Sucursal sucursal;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Max(value = 999999, message = "El stock no puede exceder 999,999 unidades")
    @Builder.Default
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Size(max = 50, message = "El código del producto no puede exceder 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9\\-_]*$", 
             message = "El código solo puede contener letras, números, guiones y guiones bajos")
    @Column(name = "codigo_producto", length = 50)
    private String codigoProducto;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Builder.Default
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HistorialStock> historialStock;
    
    @PrePersist
    protected void onCreate() {
        if (activo == null) {
            activo = true;
        }
        if (stock == null) {
            stock = 0;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        // Validación adicional antes de actualizar
        if (stock != null && stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
} 