package com.tiket.api.dWeb.hotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        String endpoint = "/ms-gateway/tix-home/v2/page-modules-full?pageModuleCode=HOTEL_SRP&referenceId=bali-108001534490276212&accommPriceBeforeTax=true&deferredCostVariant=control";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + accessToken)
                .header("X-Request-Id", "62ff8b23-96dc-4f1b-93be-aedd6e54de03")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("lang", "en")
                .header("sec-ch-ua", "\"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"144\", \"Google Chrome\";v=\"144\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("utm-external", "organic")
                .header("X-Currency", "IDR")
                .header("deviceId", "b1910503-47c3-46b8-bb99-8f664cc4ad87")
                .header("utm-source", "none")
                .header("countryCode", "IDN")
                .header("X-Country-Code", "IDN")
                .header("X-Country-Id", "id")
                .header("X-Cookie-Session-V2", "true")
                .header("Referer", baseUrl + "/en-id/hotel/search?room=1&adult=2&id=bali-108001534490276212&type=REGION&q=Bali&checkin=2026-02-07&checkout=2026-02-08")
                .header("Accept-Language", "en")
                .header("X-Audience", "tiket.com")
                .header("currency", "IDR")
                .header("X-Channel-Id", "DESKTOP")
                .header("utm-medium", "none")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Hotel Page Module API Response ===");
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

