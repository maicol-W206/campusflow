package com.devSenior.campusFlow.pagos.dto;

import com.devSenior.campusFlow.pagos.model.PlanSuscripcion;
import jakarta.validation.constraints.NotNull;

public class CrearSuscripcionRequest {

    @NotNull
    private PlanSuscripcion plan;

    public PlanSuscripcion getPlan() {
        return plan;
    }

    public void setPlan(PlanSuscripcion plan) {
        this.plan = plan;
    }
}