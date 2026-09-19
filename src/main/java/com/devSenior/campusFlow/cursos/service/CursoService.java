package com.devSenior.campusFlow.cursos.service;

import java.util.List;
import java.util.stream.Collectors;
import com.devSenior.campusFlow.common.exception.ResourceNotFoundException;
import com.devSenior.campusFlow.cursos.dto.CrearCursoRequest;
import com.devSenior.campusFlow.cursos.dto.CursoResponse;
import com.devSenior.campusFlow.cursos.mapper.CursoMapper;
import com.devSenior.campusFlow.cursos.model.Curso;
import com.devSenior.campusFlow.cursos.repository.CursoRepository;
import com.devSenior.campusFlow.usuarios.model.Usuario;
import com.devSenior.campusFlow.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public CursoService(CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CursoResponse crear(CrearCursoRequest request) {
        Usuario instructor = usuarioRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un usuario con id " + request.getInstructorId()));

        Curso curso = new Curso();
        curso.setNombre(request.getNombre());
        curso.setInstructor(instructor);

        Curso guardado = cursoRepository.save(curso);
        return CursoMapper.toResponse(guardado);
    }

    public List<CursoResponse> listarCursos() {
        return cursoRepository.findAll()
                .stream()
                .map(CursoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CursoResponse inscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un curso con id " + cursoId));

        Usuario estudiante = usuarioRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con id " + estudianteId));

        curso.getEstudiantes().add(estudiante);
        Curso actualizado = cursoRepository.save(curso);
        return CursoMapper.toResponse(actualizado);
    }
}