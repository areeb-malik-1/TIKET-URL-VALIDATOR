package com.tiket.model;

public enum VerticalEnum {
    HOMEPAGE("homepage"),
    ACCOMMODATION("accommodation"),
    FLIGHT("flight"),
    TTD("ttd"),
    BUS_SHUTTLE("bus_shuttle"),
    CAR_RENTAL("car_rental"),
    AIRPORT_TRANSFER("airport_transfer"),
    TRAIN_WHOOSH("train_whoosh");

    private String name;

    VerticalEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
