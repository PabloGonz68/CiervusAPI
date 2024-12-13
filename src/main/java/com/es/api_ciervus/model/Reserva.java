package com.es.api_ciervus.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_inicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_fin;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "estado", nullable = false)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false, unique = true)
    private Producto producto_id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario_id;


    public Reserva(Date fecha_inicio, Date fecha_fin, Producto producto_id, Usuario usuario_id) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.producto_id = producto_id;
        this.usuario_id = usuario_id;
    }

}
