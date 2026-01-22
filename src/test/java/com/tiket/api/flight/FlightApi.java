package com.tiket.api.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-master-discovery/general-content-search/v3/find";
        String url = baseUrl + endpoint;
        String fullUrl = url + "";
        String jsonBody = """
            {
              "page": "LANDING_PAGE"
            }
            """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .header("containername",
                        "com.tiket.android.flight.presentation.landing.FlightLandingActivity")
                .header("screenname",
                        "com.tiket.android.flight.presentation.landing.FlightLandingActivity")
                .header("x-correlation-id",
                        "c5f0154e-aa0b-481a-a5c7-ad9d5b3cc3ca|1767870859160")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion",
                        "5.9.1-debug-NCT-20474/play_integrity")
                .header("tiketsessionid",
                        "c5f0154e-aa0b-481a-a5c7-ad9d5b3cc3ca")
                .header("platform", "ANDROID")
                .header("tiket-user-agent",
                        "tiketcom/android-version (twh:20296642) - v5.9.1-debug-NCT-20474/play_integrity")
                .header("lang", "en")
                .header("currency", "SGD")
                .header("accept-language", "en")
                .header("x-currency", "SGD")
                .header("x-country-code", "IDN")
                .header("language", "en")
                .header("channelid", "ANDROID")
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Accept-Encoding", "gzip")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Flight API Response ===");
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