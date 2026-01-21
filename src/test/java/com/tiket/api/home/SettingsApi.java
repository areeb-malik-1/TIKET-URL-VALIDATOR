package com.tiket.api.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SettingsApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public SettingsApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-general-config/settings";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?language=en";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .header("containername", "com.tiket.gits.HomeSplashActivity")
                .header("screenname", "com.tiket.gits.HomeSplashActivity")
                .header("x-correlation-id", "33988b61-52f8-43ad-af66-447ff2ef4642|1768377027833")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion", "5.9.1-uat-HEAD")
                .header("tiketsessionid", "33988b61-52f8-43ad-af66-447ff2ef4642")
                .header("platform", "ANDROID")
                .header("tiket-user-agent", "tiketcom/android-version (twh:20296642) - v5.9.1-uat-HEAD")
                .header("lang", "en")
                .header("currency", "USD")
                .header("accept-language", "en")
                .header("x-currency", "USD")
                .header("x-country-code", "IDN")
                .header("language", "en")
                .header("content-type", "application/json")
                .header("channelid", "ANDROID")
                .header("Accept-Encoding", "gzip")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Settings API Response ===");
        System.out.println("Status: " + response.statusCode());
        System.out.println("Raw Response: " + response.body());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }
        isSuccess(response.statusCode());
        return new ApiResult(data, response.statusCode());
    }
}