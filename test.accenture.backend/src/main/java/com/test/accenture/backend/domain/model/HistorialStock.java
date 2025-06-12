package com.test.accenture.backend.domain.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"producto"})
@ToString(exclude = {"producto"})
public class HistorialStock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El producto es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference
    private Producto producto;
    
    @Min(value = 0, message = "El stock anterior no puede ser negativo")
    @Column(name = "stock_anterior", nullable = false)
    private Integer stockAnterior;
    
    @Min(value = 0, message = "El stock nuevo no puede ser negativo")
    @Column(name = "stock_nuevo", nullable = false)
    private Integer stockNuevo;
    
    @NotNull(message = "El tipo de movimiento es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;
    
    @Size(max = 255, message = "El motivo no puede exceder 255 caracteres")
    @Column(name = "motivo")
    private String motivo;
    
    @Size(max = 100, message = "El usuario no puede exceder 100 caracteres")
    @Column(name = "usuario", length = 100)
    private String usuario;
    
    @CreationTimestamp
    @Column(name = "fecha_movimiento", nullable = false, updatable = false)
    private LocalDateTime fechaMovimiento;
    
    // Enum para tipos de movimiento
    public enum TipoMovimiento {
        ENTRADA("Entrada de mercancía"),
        SALIDA("Salida de mercancía"),
        AJUSTE("Ajuste de inventario"),
        INICIAL("Stock inicial");
        
        private final String descripcion;
        
        TipoMovimiento(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
    }
} 