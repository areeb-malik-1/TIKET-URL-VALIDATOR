package com.tiket.api.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HomeApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HomeApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-home/v2/home-pages";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?availablePlatforms=ANDROID&isNotificationActive=true&pageModuleCode=HOME_V2&verticalIconVariant=control&variant=HOME_V2&vertical=HOME&headerVariant=newhome&platform=MOBILE";

        HttpRequest request = HttpRequest.newBuilder()
                .header("authorization", "Bearer " + this.accessToken)
                .headers("HOST", "api.tiket.com")
                .header("X-Country-Code", "IDN")
                .header("Accept", "application/json")
                .header("X-Account-Id", "asdad")
                .header("X-Channel-Id", "ANDROID")
                .header("X-Request-Id", "23123123")
                .header("currency", "IDR")
                .header("Accept-Language", "en")
                .header("User-Agent", "tiketcom/android-version (twh:20296642) - v5.4.0")
                .uri(URI.create(fullUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Home Pages API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        return new ApiResult(data, response.statusCode());
    }
}