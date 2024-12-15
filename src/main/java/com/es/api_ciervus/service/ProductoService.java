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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private Mapper mapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;


    public ProductoDTO getById(String id) {
            Long idLong = StringToLong.stringToLong(id);
            if (idLong == null || idLong <= 0) {
                throw  new BadRequestException("El id no es valido");
            }
            Producto producto = productoRepository.findById(idLong).orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));
            return mapper.mapToProductoDTO(producto);
    }

    public List<ProductoDTO> getAll() {
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()) {
                throw new BadRequestException("No hay productos disponibles");
            }
            List<ProductoDTO> productoDTOS = new ArrayList<>();
            productos.forEach(producto -> productoDTOS.add(mapper.mapToProductoDTO(producto)));
            return productoDTOS;

    }

    public ProductoDTO create(ProductoDTO productoDTO) {
            if (productoDTO == null) {
                throw new BadRequestException("El producto no puede estar vacio");
            }
            Usuario propietario = usuarioRepository.findById(productoDTO.getPropietario_id()).orElseThrow(() -> new ResourceNotFoundException("El propietario no existe"));             Validator.validateProducto(productoDTO);
            Producto producto = mapper.mapToProducto(productoDTO, propietario);
            productoRepository.save(producto);
            return mapper.mapToProductoDTO(producto);
        }

    public ProductoDTO update(String id, ProductoDTO productoDTO) {
            Long idLong = StringToLong.stringToLong(id);
          Usuario propietario = usuarioRepository.findById(productoDTO.getPropietario_id()).orElseThrow(() -> new ResourceNotFoundException("El propietario no existe"));
          if(productoDTO == null || idLong == null || idLong <= 0) {
              throw new BadRequestException("El id no es valido");
          }
          Validator.validateProducto(productoDTO);
          Producto producto = mapper.mapToProducto(productoDTO, propietario);
          producto.setId(idLong);
          productoRepository.save(producto);
          return mapper.mapToProductoDTO(producto);
    }

    public void delete(String id) {
            Long idLong = StringToLong.stringToLong(id);
            if (idLong == null || idLong <= 0) {
                throw new BadRequestException("El id no es valido");
            }
            productoRepository.deleteById(idLong);

    }
}
