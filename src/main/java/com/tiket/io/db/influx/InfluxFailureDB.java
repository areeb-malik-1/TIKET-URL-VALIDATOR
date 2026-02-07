package com.tiket.io.db.influx;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;

public final class InfluxFailureDB {

    private static final String DEFAULT_BUCKET = "failure_summary";

    private final InfluxDBClient client;
    private final WriteApi writeApi;

    // ---- Singleton Holder (lazy + thread-safe) ----
    private static class Holder {
        private static final InfluxFailureDB INSTANCE = new InfluxFailureDB();
    }

    public static InfluxFailureDB getInstance() {
        return Holder.INSTANCE;
    }

    // ---- Constructor (private) ----
    private InfluxFailureDB() {
        String url = getenvOrThrow("INFLUX_URL");
        String token = getenvOrThrow("INFLUX_TOKEN");
        String org = getenvOrThrow("INFLUX_ORG");

        this.client = InfluxDBClientFactory.create(url, token.toCharArray(), org, DEFAULT_BUCKET);
        this.writeApi = client.getWriteApi();  // async, thread-safe
    }

    public WriteApi getWriteApi() {
        return writeApi;
    }

    public InfluxDBClient getClient() {
        return client;
    }

    // ---- Cleanup (optional for Jenkins shutdown hooks) ----
    public void close() {
        try {
            writeApi.flush();
        } catch (Exception ignored) { }
        client.close();
    }

    // ---- Helper ----
    private static String getenvOrThrow(String key) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("Missing required env variable: " + key);
        }
        return v;
    }
}
