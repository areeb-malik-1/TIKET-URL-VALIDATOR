package com.tiket.api.dWeb.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightLandingPageApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightLandingPageApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-master-discovery/general-content-search/v3/find";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in web flight landing page module api: " + fullUrl);

        String body = """
                {
                "page":"LANDING_PAGE",
                "origin":"Jakarta",
                "destination":"Bali",
                "departureDate":"2026-02-09"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("origin", baseUrl)
                .header("referer", baseUrl + "/en-id/pesawat")
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("content-type", "text/plain;charset=UTF-8")
                .header("Cookie", "device_id=e2972c8e-c56d-445a-af65-242f36455ee2; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJzc1NMVjZDdl9VQ291aEJOMzVRUi1HU3JHYXJRd0FHbSJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMGZhYjUyZGIzYzJkNzNjZjZiN2QiLCJuYmYiOjE3Njk2NzI2MTksImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzMjYxOX0.YvrI9CMVUTB0lsbojQjU18T-BzcjS1Hc5K0PqHXJ6b4S7JxlwcEl6GaFKa9xwLFX; cf_clearance=w.477cT.NBlVGD0GlKjmKNa9gvOIdnb0pglqIsPa4Zs-1769672621-1.2.1.1-lqM2_JeL7BT06VJW5Qu870rj.t0igcA8bq82FNw_IKbBmORRnd7EznE79gQ5_7o3FB2mvqon4txcSb5aTPg.frUSzOc8mmNAlvkebmPw4Sz1_I8MfuU6stMA6lbdGPmo_AcniuZgauXfC4EyYqzKFNa04WUnNGNwbr936Bo6oIahLEbjRnKo0RfQBrDsn7iUYXFcy5q6CN5BxAlvQkBGqMEfveWtOp4hUhEop48ye9A; _gcl_au=1.1.2024533154.1769672621; _ga=GA1.1.191515909.1769672622; __cf_bm=FIHB_ZPdPRR24Nq6M0HOgDLvNxoHvZXNfDI1twQnx7I-1769672621-1.0.1.1-MM7I0auTmQk2PpMZ.kn5J6qPDHgO9FCgtOexAe4yUrhEYcgbyCvKvmQEV1joa4XbxjIrEsPIODRNMx0AdjDFxxRcecJbyupGM2ULr7BKLPKDMo_F6c8A.pQWGwf8nSFu; _cfuvid=SjLKuKJiekfbjnqVUMetF5vAQnl_YnLVqid65xrWy4w-1769672621816-0.0.1.1-604800000; _fbp=fb.1.1769672622720.284665705305062351; _tt_enable_cookie=1; _ttp=01KG4BAGW1KE27BJ2EC3963AXZ_.tt.1; app_logger_correlation_id=bddb8c3d-41c2-40e8-a2b3-90eba5c71b6a; app_is_webview=false; app_is_desktop=false")
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
                .header("x-audience", "tiket.com")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-request-id", "bddb8c3d-41c2-40e8-a2b3-90eba5c71b6a")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Web Flight Landing Page API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        isSuccess(request, response, data, body);
        return new ApiResult(data, response.statusCode());
    }
}

