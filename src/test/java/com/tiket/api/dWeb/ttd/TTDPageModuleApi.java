package com.tiket.api.dWeb.ttd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Web API client for TTD (To-Do) Destination Page Module using page-modules-full.
 * Built to mirror the provided curl while following existing project conventions.
 */
public class TTDPageModuleApi implements BaseApi {

    private final String accessToken; // session_access_token value
    private final String baseUrl;     // e.g., https://www.tiket.com
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public TTDPageModuleApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-home/v2/page-modules-full";
        String query = "?pageModuleCode=&pageModuleType=DESTINATION_PAGE&level=FALLBACK";
        String fullUrl = baseUrl + endpoint + query;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("channelid", "WEB")
                // Inject session access token via Cookie header like the curl
                .header("Cookie", "session_access_token=" + this.accessToken)
                .header("countrycode", "sg")
                .header("currency", "SGD")
                .header("deviceid", "b9711c95-7906-45c8-8d50-4d6a5bf83673")
                .header("lang", "en")
                .header("language", "EN")
                .header("platform", "WEB")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-sg/to-do")
                .header("requestid", "NONE")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("serviceid", "GATEWAY")
                .header("storeid", "TIKETCOM")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("useragent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("x-account-id", "0")
                .header("x-audience", "tiket.com")
                .header("x-channel-id", "DESKTOP")
                .header("x-channel-id-v2", "WEB")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "sg")
                .header("x-country-id", "sg")
                .header("x-currency", "SGD")
                .header("x-loyalty-level", "LV0")
                .header("x-platform-v2", "WEB")
                .header("x-request-id", "9255d6d3-22f7-4ff2-b884-edf3f724fcfc")
                .header("x-service-id", "gateway")
                .header("x-store-id", "TIKETCOM")
                .header("x-username", "GUEST")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb TTD Page Module API Response ===");
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

