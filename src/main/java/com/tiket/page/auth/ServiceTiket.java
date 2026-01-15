package com.tiket.page.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ServiceTiket {
    private static final String URL_PROD = "https://api.tiket.com/ms-gateway/tix-member-core/v3/auth/unm/service-ticket";
    private static final String URL_PREPROD = "https://member-core-v2-be-svc.preprod-platform-cluster.tiket.com/tix-member-core/v3/auth/unm/service-ticket";

    private final String serviceTicket;
    private final String env;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public ServiceTiket(String serviceTicket, String env) {
        this.serviceTicket = serviceTicket;
        this.env = env;
    }

    public record ServiceResult(JsonNode data, int status, String accessToken) {}

    public ServiceResult hitApi() throws Exception {
        String url = "prod".equals(env) ? URL_PROD : URL_PREPROD;

        Map<String, String> body = Map.of("serviceTicket", this.serviceTicket);

        HttpRequest.Builder reqBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))

                .header("Content-Type", "application/json")
                .header("Accept-Language", "id")
                .header("accept", "*/*")
                .header("X-Store-Id", "TIKETCOM")
                .header("X-Channel-Id", "WEB")
                .header("X-Service-Id", "gateway")
                .header("X-Request-Id", "6ba7b89e-c641-418c-a1a6-f3408cb59082")
                .header("X-Username", "username")
                .header("X-Account-Id", "0");


        HttpRequest request = reqBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Service Ticket API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        String accessToken = extractAccessToken(data, response);
        if (accessToken != null) {
            System.out.println("\n=== Access Token Extracted ===\nAccess Token: " + accessToken);
        } else {
            System.out.println("\n=== No Access Token Found ===");
        }

        return new ServiceResult(data, response.statusCode(), accessToken);
    }

    private String extractAccessToken(JsonNode data, HttpResponse<String> response) {
        // Check body fields
        if (data != null) {
            if (data.has("accessToken")) return data.get("accessToken").asText();
            if (data.has("data") && data.get("data").has("accessToken")) return data.get("data").get("accessToken").asText();
            if (data.has("result") && data.get("result").has("accessToken")) return data.get("result").get("accessToken").asText();
        }

        // Check Headers
        String authHeader = response.headers().firstValue("authorization").orElse(null);
        if (authHeader != null) {
            return authHeader.replaceAll("(?i)^Bearer\\s+", "");
        }
        return null;
    }
}