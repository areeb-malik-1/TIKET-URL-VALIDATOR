package com.tiket.page.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.Environment;
import com.tiket.service.BaseUrl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage {
    private static final String URL_PREPROD = "https://sandbox.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";
    private static final String URL_PROD = "https://account.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";

    private final String identity;
    private final String secret;
    private final Environment env;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public LoginPage(String identity, String secret, Environment env) {
        this.identity = identity;
        this.secret = secret;
        this.env = env;
    }

    public record LoginResult(HttpResponse<String> response, Map<String, Object> data) {}

    public LoginResult hitApi() throws Exception {

        String baseUrl = BaseUrl.get(env);
        String url = switch (env) {
            case GK -> null;
            case PROD -> "https://account.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";
            case PREPROD -> "https://sandbox.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";
        };

        // Headers setup
        var headersBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        headersBuilder.header("accept", "application/json");
        headersBuilder.header("Content-Type", "application/json");
        headersBuilder.header("X-Client-Id", "9dc79e3916a042abc86c2aa525bff009");
        headersBuilder.header("Accept-Language", "en");
        headersBuilder.header("X-Country-Code", "ID");
        headersBuilder.header("True-Client-Ip", "127.0.0.1");
        headersBuilder.header("Cookie", "device_id=53401112-03e1-461d-8143-4d60d76d1262-dont-change; Path=/; Domain=staging.bliblitiket.com; HttpOnly; Secure");

        Map<String, String> body = Map.of(
                "ref", baseUrl,
                "identity", this.identity,
                "secret", this.secret,
                "type", "EMAIL_PASSWORD"
        );

        System.out.println("url: " + baseUrl);

        HttpRequest request = headersBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());

        Map<String, Object> data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readValue(response.body(), Map.class);
            System.out.println("Response: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
        } else {
            System.out.println("Response (non-JSON): " + response.body().substring(0, Math.min(response.body().length(), 500)));
        }

        return new LoginResult(response, data);
    }

    public String extractAuthCode(HttpResponse<String> response) {

        Pattern TOKEN_PATTERN = Pattern.compile("(?:token|auth|access_token)=([^;]+)", Pattern.CASE_INSENSITIVE);

        if (response == null || response.headers() == null) {
            return null;
        }

        List<String> setCookies = response.headers().allValues("Set-Cookie");
        if (setCookies == null || setCookies.isEmpty()) {
            return null;
        }

        for (String cookie : setCookies) {
            Matcher matcher = TOKEN_PATTERN.matcher(cookie);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }
}
