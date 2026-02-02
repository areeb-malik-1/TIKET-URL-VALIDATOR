package com.tiket.api.dWeb.trainandwoosh;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrainAndWooshPageModuleApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public TrainAndWooshPageModuleApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-home/v2/page-modules-full?pageModuleCode=KCIC_LANDING_PAGE";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("X-Request-Id", "92f5935f-28a5-4b09-81bc-b00a439db55c")
                .header("lang", "en")
                .header("X-Currency", "IDR")
                .header("deviceId", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("countryCode", "id")
                .header("X-Country-Code", "IDN")
                .header("X-Loyalty-Level", "LV0")
                .header("X-Country-Id", "id")
                .header("X-Cookie-Session-V2", "true")
                .header("Referer", baseUrl + "/en-id/kereta-api")
                .header("Accept-Language", "en")
                .header("X-Audience", "tiket.com")
                .header("currency", "IDR")
                .header("X-Username", "GUEST")
                .header("X-Account-Id", "0")
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb TrainAndWoosh Page Module API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        isSuccess(response, data);
        return new ApiResult(data, response.statusCode());
    }
}

