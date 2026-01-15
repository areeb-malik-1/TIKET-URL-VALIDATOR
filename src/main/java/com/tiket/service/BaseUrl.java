package com.tiket.service;

import com.tiket.model.Environment;

public class BaseUrl {
    public static String get(Environment environment) {
        return switch (environment) {
            case GK -> "https://gatotkaca.tiket.com";
            case PROD -> "https://www.tiket.com";
            case PREPROD -> "https://preprod.tiket.com";
        };
    }
}
