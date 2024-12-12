package com.es.api_ciervus.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String roles;
    @Column(nullable = false)
    private Date fecha_registro;
    @OneToMany(mappedBy = "propietario_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;
    @OneToMany(mappedBy = "usuario_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;



    public Usuario(String username, String email, String password, String roles, Date fecha_registro) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.fecha_registro = fecha_registro;
    }

}
