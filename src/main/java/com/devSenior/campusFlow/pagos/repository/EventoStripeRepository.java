package com.devSenior.campusFlow.pagos.repository;

import com.devSenior.campusFlow.pagos.model.EventoStripe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoStripeRepository extends JpaRepository<EventoStripe, Long> {

    boolean existsByStripeEventId(String stripeEventId);
}