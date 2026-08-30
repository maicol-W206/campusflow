package com.devSenior.campusFlow.usuarios.repository;

import com.devSenior.campusFlow.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}