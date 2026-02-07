package com.tiket.model;

public record Summary(
        int pass,
        int fail,
        int skip
) {
    public int total() {
        return pass + fail + skip;
    }
}

