package com.tiket.api.app.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.service.headers.CommonProdHeaders;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightSrpDomestic1Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightSrpDomestic1Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-master-discovery/general-content-search/v3/find";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        String jsonBody = "{" +
                "\"departureDate\":\"2026-02-09\"," +
                "\"destination\":\"DPSC\"," +
                "\"origin\":\"JKTC\"," +
                "\"page\":\"SEARCH_LIST\"" +
                "}";

        var builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Flight SRP Domestic1 API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + (data != null ? data.toPrettyString() : response.body()));
        }

        isSuccess(response, data);
        return new ApiResult(data, response.statusCode());
    }
}

