package com.es.api_ciervus.service;

import com.es.api_ciervus.dto.UserRegisterDTO;
import com.es.api_ciervus.error.exception.BadRequestException;
import com.es.api_ciervus.error.exception.ConflictException;
import com.es.api_ciervus.model.Usuario;
import com.es.api_ciervus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario No encontrado"));

        List<GrantedAuthority> authorities = Arrays.stream(usuario.getRoles().split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                .collect(Collectors.toList());

        UserDetails userDetails = User // User pertenece a SpringSecurity
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(authorities)
                .build();

        return userDetails;
    }

    public UserRegisterDTO register(UserRegisterDTO user){
        if (usuarioRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("El usuario ya existe");
        }
        if (!user.getPassword1().equals(user.getPassword2())) {
            throw new BadRequestException("Las contraseñas no coinciden");
        }
        if (!user.getRoles().equals("ADMIN") && !user.getRoles().equals("USER")) {
            throw new BadRequestException("El rol debe ser ADMIN o USER");
        }
        Usuario newUser = new Usuario();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword1()));
        newUser.setRoles(user.getRoles());
        usuarioRepository.save(newUser);
        return user;
    }
}
