package com.es.api_ciervus.utils;

import com.es.api_ciervus.dto.ProductoDTO;
import com.es.api_ciervus.dto.ReservaDTO;
import com.es.api_ciervus.error.exception.ValidationException;

import java.util.Date;

public class Validator {
    public static  void validateProducto(ProductoDTO productoDTO) throws Exception {
         if (productoDTO.getNombre().isEmpty() || productoDTO.getNombre() == null) {
             throw new ValidationException("El nombre del producto no puede estar vacio");
         }
         if (productoDTO.getDescripcion().isEmpty() || productoDTO.getDescripcion() == null) {
             throw new ValidationException("La descripcion del producto no puede estar vacia");
         }
         if (productoDTO.getPrecio() <= 0) {
             throw new ValidationException("El precio del producto debe ser mayor a 0");
         }
         if (productoDTO.getFecha_publicacion() == null) {
             throw new ValidationException("La fecha de publicacion del producto no puede estar vacia");
         }
        if (productoDTO.getFecha_publicacion().after(new Date())) {
            throw new ValidationException("La fecha de publicación no puede ser una fecha futura.");
        }
         if (productoDTO.getPropietario_id() == null) {
             throw new ValidationException("El propietario del producto no puede estar vacio");
         }

    }

    public static  void validateReserva(ReservaDTO reservaDTO) throws Exception {
        if (reservaDTO.getFecha_inicio() == null) {
            throw new ValidationException("La fecha de inicio de la reserva no puede estar vacia");
        }
        if (reservaDTO.getFecha_fin() == null) {
            throw new ValidationException("La fecha de fin de la reserva no puede estar vacia");
        }
        if (reservaDTO.getFecha_inicio().after(reservaDTO.getFecha_fin())) {
            throw new ValidationException("La fecha de inicio debe ser anterior a la fecha de fin.");
        }
        if (reservaDTO.getFecha_inicio().before(new Date())) {
            throw new ValidationException("La fecha de inicio de la reserva no puede estar en el pasado.");
        }
        if (reservaDTO.getProducto_id() == null) {
            throw new ValidationException("El producto de la reserva no puede estar vacio");
        }
        if (reservaDTO.getUsuario_id() == null) {
            throw new ValidationException("El usuario de la reserva no puede estar vacio");
        }


    }
}