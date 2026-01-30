package com.tiket.api.app.tour;

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

public class Tour1Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public Tour1Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-inventory/v1/products/additional-labels";
        String url = baseUrl.replace("www", "api") + endpoint;
        String fullUrl = url + "?ids=67bc2770bb6b7428a270f12c&ids=67fde17c183e666db4aa19b8&ids=635a6fd22e11343f14f98a70&ids=5e5d09130dcd8875a5216988&ids=673f607a5b68bb65a67283d9&ids=6045dbaacf770e5b39b14829&ids=66bb13f76b2c4d6a0d1a2c38&ids=60ff6bb2688c674dc5696caf&funnel=SRP";

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET();

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        if(platform == Platform.ANDROID) {
            builder.header("useragent", "tiketcom/android-version (twh:20296642)");
        }
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Tour 1 API Response ===");
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
