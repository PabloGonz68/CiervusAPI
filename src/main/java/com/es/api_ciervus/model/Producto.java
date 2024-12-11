package com.es.api_ciervus.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    @Column(nullable = false)
    private double precio;
    @Column(nullable = false)
    private Date fecha_publicacion;
    @ManyToOne
    @JoinColumn(name = "propietario_id", nullable = false)
    private Usuario propietario_id;

    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, double precio, Date fecha_publicacion, Usuario propietario_id) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha_publicacion = fecha_publicacion;
        this.propietario_id = propietario_id;
    }

    public Producto(String nombre, String descripcion, double precio, Date fecha_publicacion, Usuario propietario_id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha_publicacion = fecha_publicacion;
        this.propietario_id = propietario_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public Usuario getPropietario_id() {
        return propietario_id;
    }

    public void setPropietario_id(Usuario propietario_id) {
        this.propietario_id = propietario_id;
    }
}
