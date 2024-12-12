package com.es.api_ciervus.service;

import com.es.api_ciervus.dto.ProductoDTO;
import com.es.api_ciervus.error.exception.BadRequestException;
import com.es.api_ciervus.error.exception.ResourceNotFoundException;
import com.es.api_ciervus.error.exception.ValidationException;
import com.es.api_ciervus.model.Producto;
import com.es.api_ciervus.model.Usuario;
import com.es.api_ciervus.repository.ProductoRepository;
import com.es.api_ciervus.repository.UsuarioRepository;
import com.es.api_ciervus.utils.Mapper;
import com.es.api_ciervus.utils.StringToLong;
import com.es.api_ciervus.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    @Autowired
    private Mapper mapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public List<ProductoDTO> getAll() {
        try {
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()) {
                throw new BadRequestException("No hay productos disponibles");
            }
            List<ProductoDTO> productoDTOS = new ArrayList<>();
            productos.forEach(producto -> productoDTOS.add(mapper.mapToProductoDTO(producto)));
            return productoDTOS;
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
    }catch (BadRequestException e) {
        throw new BadRequestException(e.getMessage());
    }
    }

    public ProductoDTO create(ProductoDTO productoDTO) {
        try {
            if (productoDTO == null) {
                throw new BadRequestException("El producto no puede estar vacio");
            }
            Usuario propietario = usuarioRepository.findById(productoDTO.getPropietario_id()).orElse(null);
            Validator.validateProducto(productoDTO);
            Producto producto = mapper.mapToProducto(productoDTO, propietario);
            productoRepository.save(producto);
            return mapper.mapToProductoDTO(producto);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
    }catch (BadRequestException e) {
        throw new BadRequestException(e.getMessage());
    }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public ProductoDTO update(String id, ProductoDTO productoDTO) {
        try {
            Long idLong = StringToLong.stringToLong(id);
          Usuario propietario = usuarioRepository.findById(productoDTO.getPropietario_id()).orElse(null);
          if(productoDTO == null || idLong == null || idLong <= 0) {
              throw new BadRequestException("El id no es valido");
          }
          Validator.validateProducto(productoDTO);
          Producto producto = mapper.mapToProducto(productoDTO, propietario);
          producto.setId(idLong);
          productoRepository.save(producto);
          return mapper.mapToProductoDTO(producto);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void delete(String id) {
        try {
            Long idLong = StringToLong.stringToLong(id);
            if (idLong == null || idLong <= 0) {
                throw new BadRequestException("El id no es valido");
            }
            productoRepository.deleteById(idLong);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
