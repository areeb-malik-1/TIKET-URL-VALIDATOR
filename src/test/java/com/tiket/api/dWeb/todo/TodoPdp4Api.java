package com.tiket.api.dWeb.todo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TodoPdp4Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public TodoPdp4Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        // Updated to match provided curl target
        String endpoint = "/ms-gateway/tix-events-v2-search/v3/product-cards/nearest";
        String query = "?productAllCategoryCodes=ATTRACTION&pageSize=9&sortLongLat=106.9508355&sortLongLat=-6.7206492&ff-ttde-popularity-score-automation=true&ff-ttde-enter-search-improvement=true";
        String fullUrl = baseUrl + endpoint + query;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("channelid", "WEB")
                // Access token injected into cookie per curl
                .header("Cookie", "session_access_token=" + this.accessToken)
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("lang", "en")
                .header("language", "EN")
                .header("platform", "WEB")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                // Keep a general referer to To-Do page; PDP referer can be too specific
                .header("referer", baseUrl + "/en-id/to-do")
                .header("requestid", "NONE")
                .header("sec-ch-ua", "\"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"144\", \"Google Chrome\";v=\"144\")")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("serviceid", "GATEWAY")
                .header("storeid", "TIKETCOM")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .header("useragent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .header("x-audience", "tiket.com")
                .header("x-channel-id-v2", "WEB")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-platform-v2", "WEB")
                .header("x-request-id", "f880c95b-2410-4413-9f16-cfa14624ced3")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb TodoPdp4 API Response ===");
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
