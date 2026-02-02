package com.tiket.api.dWeb.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightSrpDomestic4Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightSrpDomestic4Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-search/v3/search" +
                "?origin=JKTC&originType=CITY&destination=DPSC&destinationType=CITY&adult=1&child=0&infant=0&cabinClass=ECONOMY&departureDate=2026-02-09&flexiFare=true&resultType=DEPARTURE&searchType=ONE_WAY&enableVI=true&dcVar=true";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + accessToken)
                .header("Referer", baseUrl + "/en-id/pesawat/search?d=JKTC&a=DPSC&date=2026-02-09&adult=1&child=0&infant=0&class=economy&dType=CITY&aType=CITY&dLabel=Jakarta&aLabel=Denpasar-Bali&type=depart&flexiFare=true")
                .header("X-Request-Id", "57ea5483-8edc-4b2e-9876-221be07c3d05")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("lang", "en")
                .header("sec-ch-ua", "\"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"144\", \"Google Chrome\";v=\"144\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("X-Currency", "IDR")
                .header("deviceId", "b1910503-47c3-46b8-bb99-8f664cc4ad87")
                .header("countryCode", "id")
                .header("X-Country-Code", "id")
                .header("searchTrackerId", "48988e79-e34a-43eb-994c-9d12ce13d777")
                .header("X-Country-Id", "id")
                .header("X-Cookie-Session-V2", "true")
                .header("Accept-Language", "en")
                .header("X-Audience", "tiket.com")
                .header("currency", "IDR")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Flight SRP Domestic 4 API Response ===");
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

