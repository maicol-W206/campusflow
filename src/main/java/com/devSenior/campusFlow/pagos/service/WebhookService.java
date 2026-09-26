package com.devSenior.campusFlow.pagos.service;

import java.time.LocalDateTime;
import com.stripe.StripeClient;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.devSenior.campusFlow.pagos.model.EventoStripe;
import com.devSenior.campusFlow.pagos.model.PlanSuscripcion;
import com.devSenior.campusFlow.pagos.repository.EventoStripeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WebhookService {

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    private final StripeClient stripeClient;
    private final EventoStripeRepository eventoStripeRepository;
    private final SuscripcionService suscripcionService;

    public WebhookService(StripeClient stripeClient,
                          EventoStripeRepository eventoStripeRepository,
                          SuscripcionService suscripcionService) {
        this.stripeClient = stripeClient;
        this.eventoStripeRepository = eventoStripeRepository;
        this.suscripcionService = suscripcionService;
    }

    @Transactional
    public void procesar(String payload, String firma)
            throws SignatureVerificationException {
        Event event = stripeClient.constructEvent(payload, firma, webhookSecret);

        if (eventoStripeRepository.existsByStripeEventId(event.getId())) {
            return;
        }

        guardarEvento(event);

        if ("checkout.session.completed".equals(event.getType())) {
            activarDesdeSesion(event);
        }
    }

    private void guardarEvento(Event event) {
        EventoStripe evento = new EventoStripe();
        evento.setStripeEventId(event.getId());
        evento.setTipo(event.getType());
        evento.setRecibidoEn(LocalDateTime.now());
        eventoStripeRepository.saveAndFlush(evento);
    }

    private void activarDesdeSesion(Event event) {
        StripeObject objeto = event.getDataObjectDeserializer().getObject()
                .orElseThrow(() -> new IllegalStateException(
                        "No se pudo leer el objeto del evento de Stripe"));

        if (!(objeto instanceof Session session)) {
            return;
        }

        if (!"paid".equals(session.getPaymentStatus())) {
            return;
        }

        String planTexto = session.getMetadata().get("plan");
        if (planTexto == null) {
            return;
        }

        Long usuarioId = Long.valueOf(
                session.getMetadata().get("usuarioId"));
        PlanSuscripcion plan = PlanSuscripcion.valueOf(planTexto);
        suscripcionService.activar(
                usuarioId,
                plan,
                session.getCustomer(),
                session.getSubscription());
    }
}