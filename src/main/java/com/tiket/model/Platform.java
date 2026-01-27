package com.tiket.model;

import org.apache.commons.lang3.NotImplementedException;

public enum Platform {

    IOS("IOS"), ANDROID("ANDROID"), DWEB("WEB"), AMWEB("WEB"), IMWEB("WEB");

    private String name;

    Platform(String name) {
        this.name = name;
    }

    public static Platform parse(String platform) {
        switch (platform.toLowerCase()) {
            case "android" -> {
                return Platform.ANDROID;
            }
            case "ios" -> {
                return Platform.IOS;
            }
            case "dweb", "panel" -> {
                return Platform.DWEB;
            }
            case "amweb" -> {
                return Platform.AMWEB;
            }
            case "imweb" -> {
                return Platform.IMWEB;
            }
            default -> throw new NotImplementedException(String.format("Given platform: %s is not supported", platform));
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
