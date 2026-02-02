package com.tiket.api.dWeb.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Events2Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public Events2Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-inventory/v1/productSubcategories?productCategoryCode=EVENT";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + accessToken)
                .header("language", "EN")
                .header("X-Request-Id", "bb93f9c0-6b9d-44e9-ba67-6747a72c6780")
                .header("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("lang", "en")
                .header("X-Platform-V2", "WEB")
                .header("storeId", "TIKETCOM")
                .header("X-Currency", "IDR")
                .header("deviceId", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("countryCode", "id")
                .header("requestId", "NONE")
                .header("X-Country-Code", "id")
                .header("X-Country-Id", "id")
                .header("platform", "WEB")
                .header("X-Cookie-Session-V2", "true")
                .header("Referer", baseUrl + "/en-id/to-do/search?title=&utm_source=INTERNAL&utm_medium=home&productCategoryCodes=EVENT&productAllCategoryCodes=EVENT")
                .header("Accept-Language", "en")
                .header("X-Audience", "tiket.com")
                .header("currency", "IDR")
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("channelId", "WEB")
                .header("X-Channel-Id-V2", "WEB")
                .header("serviceId", "GATEWAY")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Events2 API Response ===");
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

