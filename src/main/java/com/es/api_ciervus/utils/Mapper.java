package com.es.api_ciervus.utils;

import com.es.api_ciervus.dto.ProductoDTO;
import com.es.api_ciervus.dto.ReservaDTO;
import com.es.api_ciervus.dto.UsuarioDTO;
import com.es.api_ciervus.model.Producto;
import com.es.api_ciervus.model.Reserva;
import com.es.api_ciervus.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UsuarioDTO mapToUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setUsername(usuario.getUsername());
        return usuarioDTO;
    }

    public Usuario mapToUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUsername(usuarioDTO.getUsername());
        return usuario;
    }

    public ProductoDTO mapToProductoDTO(Producto producto) {
       ProductoDTO productoDTO = new ProductoDTO();
       productoDTO.setNombre(producto.getNombre());
       productoDTO.setDescripcion(producto.getDescripcion());
       productoDTO.setPrecio(producto.getPrecio());
       productoDTO.setFecha_publicacion(producto.getFecha_publicacion());
       productoDTO.setPropietario_id(producto.getPropietario_id().getId());
       return productoDTO;
    }

    public Producto mapToProducto(ProductoDTO productoDTO, Usuario usuario) {
       Producto producto = new Producto();
       producto.setNombre(productoDTO.getNombre());
       producto.setDescripcion(productoDTO.getDescripcion());
       producto.setPrecio(productoDTO.getPrecio());
       producto.setFecha_publicacion(productoDTO.getFecha_publicacion());
       producto.setPropietario_id(usuario);
       return producto;
    }

    public ReservaDTO mapToReservaDTO(Reserva reserva) {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setNombre(reserva.getNombre());
        reservaDTO.setEstado(reserva.getEstado());
        reservaDTO.setPrecio(reserva.getPrecio());
        reservaDTO.setProducto_id(reserva.getProducto_id().getId());
        reservaDTO.setUsuario_id(reserva.getUsuario_id().getId());
        return reservaDTO;
    }

    public Reserva mapToReserva(ReservaDTO reservaDTO, Usuario usuario, Producto producto) {
        Reserva reserva = new Reserva();
        reserva.setNombre(reservaDTO.getNombre());
        reserva.setEstado(reservaDTO.getEstado());
        reserva.setPrecio(reservaDTO.getPrecio());
        reserva.setProducto_id(producto);
        reserva.setUsuario_id(usuario);
        return reserva;
    }
}
