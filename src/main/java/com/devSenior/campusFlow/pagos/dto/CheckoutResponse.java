package com.devSenior.campusFlow.pagos.dto;

public class CheckoutResponse {

    private String url;

    public CheckoutResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}