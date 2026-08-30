package com.devSenior.campusFlow.pagos.service;

import java.time.LocalDateTime;
import com.devSenior.campusFlow.common.exception.ResourceNotFoundException;
import com.devSenior.campusFlow.pagos.model.EstadoSuscripcion;
import com.devSenior.campusFlow.pagos.model.PlanSuscripcion;
import com.devSenior.campusFlow.pagos.model.Suscripcion;
import com.devSenior.campusFlow.pagos.repository.SuscripcionRepository;
import com.devSenior.campusFlow.usuarios.model.Usuario;
import com.devSenior.campusFlow.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final UsuarioRepository usuarioRepository;

    public SuscripcionService(SuscripcionRepository suscripcionRepository,
                              UsuarioRepository usuarioRepository) {
        this.suscripcionRepository = suscripcionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void activar(Long usuarioId, PlanSuscripcion plan,
                        String customerId, String subscriptionId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado"));

        Suscripcion suscripcion = suscripcionRepository
                .findByUsuarioId(usuarioId)
                .orElse(new Suscripcion());

        suscripcion.setUsuario(usuario);
        suscripcion.setPlan(plan);
        suscripcion.setEstado(EstadoSuscripcion.ACTIVA);
        suscripcion.setStripeCustomerId(customerId);
        suscripcion.setStripeSubscriptionId(subscriptionId);
        suscripcion.setFechaInicio(LocalDateTime.now());

        suscripcionRepository.save(suscripcion);
    }
}