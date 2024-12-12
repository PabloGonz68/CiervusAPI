package com.es.api_ciervus.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(nullable = false)
    private Date fecha_inicio;
    @Column(nullable = false)
    private Date fecha_fin;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
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
