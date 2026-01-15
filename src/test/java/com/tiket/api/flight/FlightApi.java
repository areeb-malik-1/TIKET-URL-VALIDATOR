package com.tiket.api.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightApi implements BaseApi {
    private static final String URL_PROD = "https://api.tiket.com/ms-gateway/tix-home/v2/page-modules-full";
    private static final String URL_PREPROD = "https://preprod.tiket.com/ms-gateway/tix-home/v2/page-modules-full";

    private final String accessToken;
    private final String env;
    private final String platform;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightApi(String accessToken, String env, String platform) {
        this.accessToken = accessToken;
        this.env = env;
        this.platform = platform;
    }

    public record FlightResult(JsonNode data, int status) {}

    public ApiResult hitApi() throws Exception {
        String baseUrl = "prod".equals(env) ? URL_PROD : URL_PREPROD;
        String fullUrl = baseUrl + "?availablePlatforms=ANDROID&isNotificationActive=true&pageModuleCode=HOME_V2&verticalIconVariant=control&variant=HOME_V2&vertical=HOME&headerVariant=newhome&platform=MOBILE";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .header("x-correlation-id", "c5f0154e-aa0b-481a-a5c7-ad9d5b3cc3ca")
                .header("platform", "ANDROID")
                .header("lang", "en")
                .header("currency", "SGD")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Flight Pages API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        return new ApiResult(data, response.statusCode());
    }
}