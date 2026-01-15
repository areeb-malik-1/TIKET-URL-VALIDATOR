package com.tiket.page.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class VerifyPage {
    private static final String URL_PREPROD = "https://sandbox.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/code/verify";
    private static final String URL_PROD = "https://account.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/code/verify";

    private final String authCode;
    private final String env;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public VerifyPage(String authCode, String env) {
        this.authCode = authCode;
        this.env = env;
    }

    public record VerifyResult(JsonNode data, int status, String serviceTicket) {}

    public VerifyResult hitApi() throws Exception {
        String baseUrl = "prod".equals(env) ? URL_PROD : URL_PREPROD;

        if (authCode == null) {
            System.err.println("No auth code provided");
            return null;
        }

        String verifyUrl = baseUrl + "?authCode=" + URLEncoder.encode(authCode, StandardCharsets.UTF_8);
        System.out.println("\nurl: " + verifyUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(verifyUrl))
                .header("Accept-Language", "en")
                .header("accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Verify API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        } else {
            throw new RuntimeException("Expected JSON but received " + response.headers().firstValue("content-type"));
        }

        String serviceTicket = extractServiceTicket(data);
        if (serviceTicket != null) {
            System.out.println("\n=== Service Ticket Extracted ===\nService Ticket: " + serviceTicket);
        } else {
            System.out.println("\n=== No Service Ticket Found ===");
        }

        return new VerifyResult(data, response.statusCode(), serviceTicket);
    }

    private String extractServiceTicket(JsonNode data) {
        if (data != null && data.has("data") && data.get("data").has("serviceTicket")) {
            return data.get("data").get("serviceTicket").asText();
        }
        return null;
    }
}