package com.tiket.api.app.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.service.headers.CommonProdHeaders;
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
    private final String origin;
    private final String destination;
    private final String date;

    public FlightSrpDomestic4Api(String accessToken, String baseUrl, String origin, String destination, String date) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-search/v3/search";
        String query = "?cabinClass=ECONOMY&fullRefundReschedule=false&searchType=ONE_WAY&" +
                "origin=" + origin + "&dcVar=true&" +
                "destination=" + destination + "&infant=0&originType=CITY&async=true&enableVI=true&destinationType=CITY&flexiFare=true&departure" +
                "Date=" + date + "&adult=1&resultType=DEPARTURE&child=0";

        String url = baseUrl + endpoint + query;

        System.out.println("full url in " + getClass().getName() + ": " + url);

        var builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("searchtrackerid", "827cb106-0eb7-4a4b-82e6-0b6f4301411f")
                .header("x-app-sign", "CqcCARCnMGvjGFQS-HHXoaAfW-Ivm4TjsqACt6WM9_X1rzfZu-f-eY5QDDLrvEKIS-wCuj9zXOKvrnGKTF7_kpAQj4_9kQvOW1yFUFxwRsyB0C5rMRABO0AzeHuvrRxKFECalsT_ZI503NhcWG_jQh_FZgHrw0qv9hXIZ1WwP7o3V5n46R6w7NFVWOeLh9x2PD76M8-B9Un7p2eZ5-n2u7k81D7GmIWjgfbvS84kUhMe5KAJOddp4v43Uq-9C7yFi_vtEnneI9Unx8ujP0zetoW_3EHVg62Ch-rMWqK1I-bwLROTWCQLV028LAl9efPcoZC8ZuU9FfrESySypNjwcYj0OY_Ns90f4YKxVSMTnfdbWh-vT9WXRERWMAKzGp5ZmcoN3IqkMym4JBqAAQFT8SvtK6m8IBDdrGhGUWE8QqinLJTuC-3muWbmqeTJzu2wl2xnSoiBKvnKdQza4zCuzS7ZLTrEc-aDBEBKRHj2F8l64-vdOQTWy-4GaS73yXvLhcXPyUpxi1x6NZKVX0Tg9IjWwCEXE47l5yWBbmZ10YmuFDRCZUXfBOTVvv0o")
                .GET();

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Flight SRP Domestic4 API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + (data != null ? data.toPrettyString() : response.body()));
        }

        isSuccess(request, response, data);
        return new ApiResult(data, response.statusCode());
    }
}

