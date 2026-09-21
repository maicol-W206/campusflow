package com.devSenior.campusFlow.cursos.mapper;

import com.devSenior.campusFlow.cursos.dto.CursoResponse;
import com.devSenior.campusFlow.cursos.model.Curso;

public class CursoMapper {

    public static CursoResponse toResponse(Curso curso) {
        CursoResponse response = new CursoResponse();
        response.setId(curso.getId());
        response.setNombre(curso.getNombre());
        response.setInstructorNombre(
                curso.getInstructor() != null ? curso.getInstructor().getNombre() : null);
        response.setCantidadEstudiantes(curso.getEstudiantes().size());
        return response;
    }
}