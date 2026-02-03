package com.tiket.api.app.todo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.service.headers.CommonProdHeaders;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TodoPdp5Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public TodoPdp5Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-search/v3/product-cards/nearest";
        String query = "?productAllCategoryCodes=ATTRACTION&pageSize=9&sortLongLat=106.9508355&sortLongLat=-6.7206492&ff-ttde-popularity-score-automation=true&ff-ttde-enter-search-improvement=true";
        String fullUrl = baseUrl.replace("www", "m") + endpoint + query;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        var builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("referer", "https://m.tiket.com/to-do/taman-safari-indonesia-bogor?utm_page=toDoHome")
                .header("priority", "u=1, i")
                .GET();

        CommonProdHeaders.getWebviewHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Todo PDP5 API Response ===");
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

