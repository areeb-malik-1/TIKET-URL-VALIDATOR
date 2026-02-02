package com.tiket.model;

public record Url(
        String gk,
        String preprod,
        String prod
) {
    public String get(Environment environment) {
        return switch (environment) {
            case GK -> gk;
            case PREPROD -> preprod;
            case PROD -> prod;
        };
    }
}
