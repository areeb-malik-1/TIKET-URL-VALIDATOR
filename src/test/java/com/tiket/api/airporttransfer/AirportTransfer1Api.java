package com.tiket.api.airporttransfer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AirportTransfer1Api implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public AirportTransfer1Api(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-airport-transfer-location/v3/suggestions";
        String url = baseUrl.replace("api.", "www.") + endpoint;
        String fullUrl = url + "?locationType=AIRPORT&type=OUT&pos=&keyword=";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .header("x-request-id", "bef6a4ac-adf0-415a-beb3-c25b2b7afe79")
                .header("sec-ch-ua-platform", "\"Android\"")
                .header("lang", "en")
                .header("sec-ch-ua", "\"Android WebView\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"")
                .header("sec-ch-ua-mobile", "?1")
                .header("x-currency", "USD")
                .header("deviceid", "179dd086888c94ec")
                .header("appversion", "5.9.1")
                .header("tiketsessionid", "f9df70e3-688b-4228-bf5a-3ca93420692a")
                .header("countrycode", "IDN")
                .header("x-country-code", "IDN")
                .header("x-country-id", "us")
                .header("platform", "Android")
                .header("x-cookie-session-v2", "true")
                .header("accept-language", "en")
                .header("osversion", "14")
                .header("currency", "USD")
                .header("x-audience", "tiket.com")
                .header("user-agent", "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/143.0.7499.146 Mobile Safari/537.36")
                .header("devicemodel", "Xiaomi 23108RN04Y")
                .header("accept", "*/*")
                .header("x-requested-with", "com.tiket.gits")
                .header("sec-fetch-site", "same-origin")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-dest", "empty")
                .header("referer", "https://www.tiket.com/airport-transfer?utm_page=home")
                .header("priority", "u=1, i")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Airport Transfer 1 API Response ===");
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
