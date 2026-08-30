package com.devSenior.campusFlow.usuarios.service;

import java.util.List;
import java.util.stream.Collectors;
import com.devSenior.campusFlow.common.exception.EmailDuplicadoException;
import com.devSenior.campusFlow.common.exception.ResourceNotFoundException;
import com.devSenior.campusFlow.usuarios.dto.ActualizarUsuarioRequest;
import com.devSenior.campusFlow.usuarios.dto.CrearUsuarioRequest;
import com.devSenior.campusFlow.usuarios.dto.UsuarioResponse;
import com.devSenior.campusFlow.usuarios.mapper.UsuarioMapper;
import com.devSenior.campusFlow.usuarios.model.Usuario;
import com.devSenior.campusFlow.usuarios.model.PreferenciasUsuario;
import com.devSenior.campusFlow.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse crear(CrearUsuarioRequest request) {
        usuarioRepository.findByEmail(request.getEmail()).ifPresent(usuarioExistente -> {
            throw new EmailDuplicadoException("Ya existe un usuario registrado con ese email");
        });

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());

        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(guardado);
    }

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con id " + id));
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse actualizar(Long id, ActualizarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con id " + id));

        usuario.setNombre(request.getNombre());
        if (usuario.getPreferencias() == null) {
            usuario.setPreferencias(new PreferenciasUsuario());
        }
        if (request.getTema() != null) {
            usuario.getPreferencias().setTema(request.getTema());
        }
        if (request.getNotificacionesActivas() != null) {
            usuario.getPreferencias().setNotificacionesActivas(request.getNotificacionesActivas());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(actualizado);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe un usuario con id " + id);
        }
        usuarioRepository.deleteById(id);
    }
}