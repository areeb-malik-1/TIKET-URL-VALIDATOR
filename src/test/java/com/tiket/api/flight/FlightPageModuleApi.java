package com.tiket.api.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightPageModuleApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightPageModuleApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-home/v2/page-modules-full";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?pageModuleCode=FLIGHT_PAGE"
                + "&isNotificationActive=false"
                + "&accommPriceBeforeTax=true"
                + "&recommendationVersion=";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .header("authorization", "Bearer " + this.accessToken)
                .header("containername", "com.tiket.android.flight.presentation.landing.FlightLandingActivity")
                .header("screenname", "com.tiket.android.flight.presentation.landing.FlightLandingActivity")
                .header("x-correlation-id", "c5f0154e-aa0b-481a-a5c7-ad9d5b3cc3ca|1767868706883")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion", "5.9.1-debug-NCT-20474/play_integrity")
                .header("tiketsessionid", "c5f0154e-aa0b-481a-a5c7-ad9d5b3cc3ca")
                .header("platform", "ANDROID")
                .header("tiket-user-agent", "tiketcom/android-version (twh:20296642) - v5.9.1-debug-NCT-20474/play_integrity")
                .header("lang", "en")
                .header("currency", "SGD")
                .header("accept-language", "en")
                .header("x-currency", "SGD")
                .header("x-country-code", "IDN")
                .header("language", "en")
                .header("content-type", "application/json")
                .header("channelid", "ANDROID")
                .header("if-modified-since", "Thu, 08 Jan 2026 10:25:41 GMT")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== FlightPageModule API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }
        isSuccess(response.statusCode());
        return new ApiResult(data, response.statusCode());
    }
}
