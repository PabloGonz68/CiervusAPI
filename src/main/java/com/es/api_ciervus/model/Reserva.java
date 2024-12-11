package com.es.api_ciervus.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
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


    public Reserva() {
    }

    public Reserva(Long id, Date fecha_inicio, Date fecha_fin, Producto producto_id, Usuario usuario_id) {
        this.id = id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.producto_id = producto_id;
        this.usuario_id = usuario_id;
    }

    public Reserva(Date fecha_inicio, Date fecha_fin, Producto producto_id, Usuario usuario_id) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.producto_id = producto_id;
        this.usuario_id = usuario_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Producto getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Producto producto_id) {
        this.producto_id = producto_id;
    }

    public Usuario getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Usuario usuario_id) {
        this.usuario_id = usuario_id;
    }
}
