package com.devSenior.campusFlow.cursos.repository;

import com.devSenior.campusFlow.cursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}