package com.tiket.api.dWeb.hotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HotelLandingPageApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HotelLandingPageApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-hotel-home-api/longstay?accommodationType=hotel";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("Cookie", "device_id=e2972c8e-c56d-445a-af65-242f36455ee2; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJzc1NMVjZDdl9VQ291aEJOMzVRUi1HU3JHYXJRd0FHbSJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMGZhYjUyZGIzYzJkNzNjZjZiN2QiLCJuYmYiOjE3Njk2NzI2MTksImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzMjYxOX0.YvrI9CMVUTB0lsbojQjU18T-BzcjS1Hc5K0PqHXJ6b4S7JxlwcEl6GaFKa9xwLFX; _gcl_au=1.1.2024533154.1769672621; _ga=GA1.1.191515909.1769672622; _fbp=fb.1.1769672622720.284665705305062351; _tt_enable_cookie=1; _ttp=01KG4BAGW1KE27BJ2EC3963AXZ_.tt.1; app_is_webview=false; app_is_desktop=false; cf_clearance=1.qZIFDm8P3oKQJFWRgz4MxjQSA0L85X2xPizlHgaNY-1769674091-1.2.1.1-IU2w3SsLUSsK4VuJOJFOAOX_CRBJjb_5Ec5XI7wlShy_60yvuxnsWk9tTY7sNTZzQMltDfj8288Y9IYm8_9rTOWl6nwzoKwe6.UEIrVfvuiJJJO5aALVNELfie38OrBjnKXjIGTMeZNVQWkhv20LJnjmpYT.xBCXfoTNbsrUr1NlcUl5Q1YuMj77PW3bU0kHOhchUafHgs.qrmIXlj8g9BhuBJQckqCotakSnj680dE; __cf_bm=oKzKN5lNGpW.zIyINPlDEHq2uB60A0g_BGFmQ54t5pQ-1769674095-1.0.1.1-m0t49Vso5_XtRpCblxXUMqDiJcw2ZBlY8d8g7a96V8brLJHOHJzWpMSjB4pu1BA7KqsEdXqCz000Nqjf_Zc9LNLXtJlMPRAOWv8BlKaC83uxlqyI80TTIUAVlXeNTJc0; _cfuvid=jh7A7CUxDmxpL1vJFIO.uPrQtw76IaUlxFeJXIVa4Ew-1769674095699-0.0.1.1-604800000")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("lang", "en")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/hotel")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("utm-external", "organic")
                .header("utm-medium", "none")
                .header("utm-source", "none")
                .header("x-audience", "tiket.com")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-request-id", "86eb58a5-be50-4d12-a0fb-caa026cc3cd3")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Hotel Landing Page API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        isSuccess(request, response, data);
        return new ApiResult(data, response.statusCode());
    }
}

