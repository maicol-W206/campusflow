package com.devSenior.campusFlow.usuarios.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.devSenior.campusFlow.usuarios.model.Usuario;
import com.devSenior.campusFlow.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}