package com.es.api_ciervus.service;

import com.es.api_ciervus.dto.ProductoDTO;
import com.es.api_ciervus.error.exception.BadRequestException;
import com.es.api_ciervus.error.exception.ResourceNotFoundException;
import com.es.api_ciervus.model.Producto;
import com.es.api_ciervus.repository.ProductoRepository;
import com.es.api_ciervus.utils.Mapper;
import com.es.api_ciervus.utils.StringToLong;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    @Autowired
    private Mapper mapper;

    @Autowired
    private ProductoRepository productoRepository;


    public ProductoDTO getById(String id) {
        try {
            Long idLong = StringToLong.stringToLong(id);
            if (idLong == null || idLong <= 0) {
                throw  new BadRequestException("El id no es valido");
            }
            Producto producto = productoRepository.findById(idLong).orElse(null);
            if (producto == null) {
                throw new BadRequestException("El producto no existe");
            }
            return mapper.mapToProductoDTO(producto);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());


    }catch (BadRequestException e) {
        throw new BadRequestException(e.getMessage());
    }
    }

   /* public List<ProductoDTO> getAll() {
        try {
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()) {
                throw new BadRequestException("No hay productos disponibles");
            }
            List<ProductoDTO> productoDTOS = new ArrayList<>();
            productos.forEach(producto -> productoDTOS.add(mapper.mapToProductoDTO(producto)));

        }
    }*/
}
