package com.tiket.model;

public record Endpoint(
        String gk,
        String preprod,
        String prod
) {
    public Endpoint(String url) {
        this(url, url, url);
    }

    public String get(Environment environment) {
        return switch (environment) {
            case GK -> gk;
            case PREPROD -> preprod;
            case PROD -> prod;
        };
    }
}
