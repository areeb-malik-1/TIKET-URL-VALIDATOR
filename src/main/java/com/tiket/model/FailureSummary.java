package com.tiket.model;

public record FailureSummary(
        String env,
        String platform,
        String vertical,
        long total,
        long passed,
        long failed,
        long skipped,
        long timestamp
){}

