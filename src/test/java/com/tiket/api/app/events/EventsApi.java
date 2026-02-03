package com.tiket.api.app.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.service.headers.CommonProdHeaders;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EventsApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public EventsApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-inventory/v1/products/additional-labels";
        String url = baseUrl.replace("www", "api") + endpoint;
        String fullUrl = url + "?ids=693fc3abc130446a1234257b&ids=6912a58a8f652a24e740b637&ids=6915928434e44e428f865117&ids=60794832fdf6074422b397b4&ids=6916a6854455e57413d86cf2&ids=607562c4d11a426d01c5e45f&ids=6914361a9e870c719931beeb&ids=6790700afe7e2d5c0462709c&funnel=SRP";

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET();

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        if(platform == Platform.ANDROID) {
            builder.header("useragent", "tiketcom/android-version (twh:20296642)");
        }
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Events API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        isSuccess(request, response, data);

        return new ApiResult(data, response.statusCode());
    }
}
