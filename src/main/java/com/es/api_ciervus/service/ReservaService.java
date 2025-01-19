package com.es.api_ciervus.service;

import com.es.api_ciervus.dto.ReservaDTO;
import com.es.api_ciervus.error.exception.BadRequestException;
import com.es.api_ciervus.error.exception.ForbiddenException;
import com.es.api_ciervus.error.exception.ResourceNotFoundException;
import com.es.api_ciervus.error.exception.UnauthorizedException;
import com.es.api_ciervus.model.Producto;
import com.es.api_ciervus.model.Reserva;
import com.es.api_ciervus.model.Usuario;
import com.es.api_ciervus.repository.ProductoRepository;
import com.es.api_ciervus.repository.ReservaRepository;
import com.es.api_ciervus.repository.UsuarioRepository;
import com.es.api_ciervus.utils.Mapper;
import com.es.api_ciervus.utils.StringToLong;
import com.es.api_ciervus.utils.Validator;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private Mapper mapper;
   /* public ReservaDTO create(ReservaDTO reservaDTO) {
        // Validación inicial
        if (reservaDTO == null) {
            throw new BadRequestException("La reserva no puede estar vacía");
        }
        System.out.println("ReservaDTO recibido: Usuario ID = " + reservaDTO.getUsuario_id() + ", Producto ID = " + reservaDTO.getProducto_id());

        // Obtener usuario y producto
        Usuario usuario = usuarioRepository.findById(reservaDTO.getUsuario_id())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        System.out.println("Usuario encontrado: ID = " + usuario.getId());

        Producto producto = productoRepository.findById(reservaDTO.getProducto_id())
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));
        System.out.println("Producto encontrado: ID = " + producto.getId() + ", Propietario ID = " + producto.getPropietario().getId());

        // Validar que el propietario no pueda reservar su propio producto
        if (producto.getPropietario().getId().equals(usuario.getId())) {
            System.out.println("Intento de reservar el propio producto detectado para el usuario ID = " + usuario.getId());
            throw new BadRequestException("El propietario no puede reservar su propio producto");
        }

        // Obtener el usuario autenticado
        String usernameAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Nombre de usuario autenticado: " + usernameAutenticado);

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(usernameAutenticado)
                .orElseThrow(() -> new UnauthorizedException("El usuario no está autenticado"));
        System.out.println("Usuario autenticado encontrado: ID = " + usuarioAutenticado.getId() + ", Roles = " + usuarioAutenticado.getRoles());

        // Validar permisos del usuario autenticado
        if (!usuarioAutenticado.getRoles().contains("ADMIN")) {
            if (!usuarioAutenticado.getId().equals(reservaDTO.getUsuario_id())) {
                System.out.println("Validación fallida: El usuario autenticado no tiene permisos para realizar esta acción");
                throw new ForbiddenException("No puedes asignar la reserva a otro usuario");
            }
        } else {
            System.out.println("Usuario autenticado tiene rol ADMIN, se permite la acción");
        }

        // Validar reserva
        try {
            Validator.validateReserva(reservaDTO);
            System.out.println("Reserva validada correctamente");
        } catch (Exception e) {
            System.out.println("Error en la validación de la reserva: " + e.getMessage());
            throw e;
        }

        // Crear reserva
        Reserva reserva = mapper.mapToReserva(reservaDTO, usuario, producto);
        Date fechaInicio = new Date();
        reserva.setFecha_inicio(fechaInicio);
        System.out.println("Fecha de inicio de la reserva: " + fechaInicio);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date fechaFin = calendar.getTime();
        reserva.setFecha_fin(fechaFin);
        System.out.println("Fecha de fin de la reserva: " + fechaFin);

        reservaRepository.save(reserva);
        System.out.println("Reserva guardada en la base de datos: ID = " + reserva.getId());

        // Retornar resultado
        ReservaDTO resultado = mapper.mapToReservaDTO(reserva);
        System.out.println("ReservaDTO creada: " + resultado);
        return resultado;
    }*/

    public ReservaDTO create(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            throw new BadRequestException("La reserva no puede estar vacia");
        }

        Usuario usuario = usuarioRepository.findById(reservaDTO.getUsuario_id()).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        Producto producto = productoRepository.findById(reservaDTO.getProducto_id()).orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));
        // Validar que el propietario no pueda reservar su propio producto
        if (producto.getPropietario().getId().equals(usuario.getId())) {
            throw new BadRequestException("El propietario no puede reservar su propio producto");
        }


        // Obtener el usuario autenticado
        String usernameAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByUsername(usernameAutenticado).orElseThrow(() -> new UnauthorizedException("El usuario no esta autenticado"));
       // Validar que el usuario autenticado sea el propietario del producto
        if(!usuarioAutenticado.getRoles().contains("ADMIN")){
            if (!usuarioAutenticado.getId().equals(reservaDTO.getUsuario_id())) {
                throw new ForbiddenException("No puedes asignar la reservar un producto a otro usuario");
            }
        }

        Validator.validateReserva(reservaDTO);
        Reserva reserva = mapper.mapToReserva(reservaDTO, usuario, producto);
        Date fechaInicio = new Date();
        reserva.setFecha_inicio(fechaInicio);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date fechaFin = calendar.getTime();

        reserva.setFecha_fin(fechaFin);
        reservaRepository.save(reserva);
        return mapper.mapToReservaDTO(reserva);
    }

    public ReservaDTO getById(String id) {
        Long idLong = StringToLong.stringToLong(id);
        if (idLong == null || idLong <= 0) {
            throw new BadRequestException("El id no es valido");
        }
        Reserva reserva = reservaRepository.findById(idLong).orElseThrow(() -> new ResourceNotFoundException("La reserva no existe"));
        return mapper.mapToReservaDTO(reserva);

    }

    public List<ReservaDTO> getAll() {
        List<Reserva> reservas = reservaRepository.findAll();
        if (reservas.isEmpty()) {
            throw new BadRequestException("No hay reservas disponibles");
        }
        List<ReservaDTO> reservaDTOS = new ArrayList<>();
        reservas.forEach(reserva -> reservaDTOS.add(mapper.mapToReservaDTO(reserva)));
        return reservaDTOS;
    }

    public ReservaDTO update(String id, ReservaDTO reservaDTO) {
        Long idLong = StringToLong.stringToLong(id);
        if (idLong == null || idLong <= 0) {
            throw new BadRequestException("El id no es valido");
        }

        String usernameAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByUsername(usernameAutenticado).orElseThrow(() -> new UnauthorizedException("El usuario no esta autenticado"));

        if(!usuarioAutenticado.getRoles().contains("ADMIN")){
            if (usuarioAutenticado.getId().equals(reservaDTO.getUsuario_id())) {
                throw new ForbiddenException("El propietario del producto no coincide con su usuario");
            }
        }

        Usuario usuario = usuarioRepository.findById(reservaDTO.getUsuario_id()).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        Producto producto = productoRepository.findById(reservaDTO.getProducto_id()).orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));
        if (producto.getPropietario().getId().equals(usuario.getId())) {
            throw new BadRequestException("El propietario no puede reservar su propio producto");
        }
        Validator.validateReserva(reservaDTO);
        Reserva reserva = mapper.mapToReserva(reservaDTO, usuario, producto);
        reserva.setId(idLong);
        reservaRepository.save(reserva);
        return mapper.mapToReservaDTO(reserva);
    }

    public void delete(String id) {
        Long idLong = StringToLong.stringToLong(id);
        if (idLong == null || idLong <= 0) {
            throw new BadRequestException("El id no es valido");
        }
        reservaRepository.deleteById(idLong);
    }

}
