package com.tiket.api.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HomePageModuledWebApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HomePageModuledWebApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-home/v2/page-modules/68faf618cd4ab978ad1554ab";
        String fullUrl = baseUrl + endpoint + "?";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("referer", baseUrl + "/en-id")
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                // cookies from curl (for web flows)
                .header("Cookie", "__cf_bm=NJ.jNNU2Sja1SsAS9Nr4SQhC7PO_DbOCuSRvn8aPPvA-1769672619-1.0.1.1-eYgKSLylk0YYo_XOaDc5ODU.bTu7hwICYmWdW2P_LlArMibyqW4uTNR0EYJJn5qKvyfKgkOjic1SSaDdYOqNDad32gVuzGteBgoBIlBXyIPasmRL5XjDzFKM8g4u8tZd; _cfuvid=u1cZyfq.qrI98zneEA8pBrnXTTlCyRxbL_b5wHGkHhM-1769672619820-0.0.1.1-604800000; app_logger_correlation_id=21f4dbf0-b3ec-44b4-9635-b52f4f7ddf76; device_id=e2972c8e-c56d-445a-af65-242f36455ee2; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJzc1NMVjZDdl9VQ291aEJOMzVRUi1HU3JHYXJRd0FHbSJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMGZhYjUyZGIzYzJkNzNjZjZiN2QiLCJuYmYiOjE3Njk2NzI2MTksImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzMjYxOX0.YvrI9CMVUTB0lsbojQjU18T-BzcjS1Hc5K0PqHXJ6b4S7JxlwcEl6GaFKa9xwLFX")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("lang", "en")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("x-account-id", "0")
                .header("x-audience", "tiket.com")
                .header("x-channel-id", "DESKTOP")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-loyalty-level", "LV0")
                .header("x-request-id", "63672a80-0993-4dc1-91fa-076bf9dd8695")
                .header("x-service-id", "gateway")
                .header("x-store-id", "TIKETCOM")
                .header("x-username", "")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
        }

        isSuccess(response, data);
        return new ApiResult(data, response.statusCode());
    }
}

