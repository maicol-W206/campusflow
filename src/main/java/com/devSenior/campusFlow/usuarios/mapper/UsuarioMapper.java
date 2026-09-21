package com.devSenior.campusFlow.usuarios.mapper;

import com.devSenior.campusFlow.usuarios.dto.UsuarioResponse;
import com.devSenior.campusFlow.usuarios.model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol());
        response.setTema(usuario.getPreferencias().getTema());
        response.setNotificacionesActivas(usuario.getPreferencias().isNotificacionesActivas());
        return response;
    }
}