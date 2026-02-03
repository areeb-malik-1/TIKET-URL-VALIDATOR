package com.tiket.api.app.hotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.service.headers.CommonProdHeaders;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HotelSrp1Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HotelSrp1Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-hotel-search/v4/search";
        String fullUrl = baseUrl + endpoint;

        String jsonBody = "{\"ABTest\":{\"newNudgesLogic\":\"old\",\"deferredCostVariant\":\"control\"},\"accommodationType\":\"hotel\",\"adult\":2,\"childAges\":[],\"night\":1,\"page\":1,\"room\":1,\"searchType\":\"REGION\",\"searchValue\":\"bali-108001534490276212\",\"sort\":\"popularity\",\"startDate\":\"2026-02-07\"}";

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        var builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("version", "1")
                .header("x-search-session-id", "8B9D3286-D490-484F-821C-1E5F1756617B")
                .header("utm-source", "none")
                .header("utm_medium", "none")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Hotel SRP1 API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + (data != null ? data.toPrettyString() : response.body()));
        }

        isSuccess(request, response, data);
        return new ApiResult(data, response.statusCode());
    }
}

