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
import java.nio.file.Path;

public class HotelPageModuleApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HotelPageModuleApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-home/v2/page-modules-full";
        String url = baseUrl.replace("www", "api") + endpoint;
        String fullUrl = url + "?pageModuleCode=hotel_LP"
                + "&variant=hotel_LP"
                + "&taxDisplay=abt"
                + "&ABTest%5BdeferredCostVariant%5D=control"
                + "&isNotificationActive=false"
                + "&accommPriceBeforeTax=true"
                + "&recommendationVersion=";

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET();

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== HotelPageModule API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
            Path path = Path.of("./log/hotel_page_module_response.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), data);
        }

        isSuccess(response, data);

        return new ApiResult(data, response.statusCode());
    }
}