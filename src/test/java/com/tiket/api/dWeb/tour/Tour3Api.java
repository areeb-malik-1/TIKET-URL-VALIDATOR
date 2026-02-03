package com.tiket.api.dWeb.tour;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Tour3Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public Tour3Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-inventory/v1/productSubcategories?pageNumber=1&pageSize=100";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("channelid", "WEB")
                .header("Cookie", "__cf_bm=sN1wim_PkdF2AKYxYSXTUAgLLgy8.RqY6KlAmpE9t8Y-1769677847-1.0.1.1-_yYZTjTjLvsBA_vylv_oLhiMVOtnbqT0qUR9rFLKBekhCClW3EOm3bhOVmHlAkwWGkwiANf3H0O3fWtxzUIp6IdVx5FENuRAMzErM5fDc9Uz0uuYRZsA8Hbuhvm9CChK; _cfuvid=aN5sUE.u3Nz.L3E123PM1vIJS6FTTZul_V0ZQ6SkOn8-1769677847842-0.0.1.1-604800000; device_id=e23b8523-8f5f-41cb-b52c-7a2854237483; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJDQ19nUkRiLXJINGZ2cnB5Q3EzSDg3dVgwdm91bDZieiJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMjQxNzViYzE1ZTQwZDE1Y2UwNmQiLCJuYmYiOjE3Njk2Nzc4NDcsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzNzg0N30.EvyrlC2SGh1JYNXT5OIoZlXKJg8mGr_uamNH4xiDO7xMupRNzMZ0BTZncpo4iQnj; cf_clearance=d7LHoyjWAZTrUaua5hRwzBtEk4lCI5VlKYIY.UkyTPw-1769677848-1.2.1.1-Res.Em2BYNJT5myWdXFJBcOfsyActDXojmMGIJ7uYOYUKYnFS_b1Liy1tT_383muX5OmFKLCO8XZD7tn9CvNMYo2HR8YBfQNqiGrIBamg2nHqAvbDVyPlp.twSW7usGbfBnBWqxpsnVJax.s8vQcaDxV4BRWHVcKrYG5tkCuzQGVHaGPfBduouRVepPhNfo0EanyvMfDb1Y9MT4bGxARKKhysAqNZWyYlIwMLANxd4g")
                .header("countrycode", "sg")
                .header("currency", "SGD")
                .header("deviceid", "e23b8523-8f5f-41cb-b52c-7a2854237483")
                .header("lang", "en")
                .header("language", "EN")
                .header("platform", "WEB")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-sg/to-do/search?productAllCategoryCodes=TOUR&searchEntry=ALL_CATEGORY_DIALOG&utm_page=toDoCategoryList")
                .header("requestid", "NONE")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("serviceid", "GATEWAY")
                .header("storeid", "TIKETCOM")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("useragent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("x-audience", "tiket.com")
                .header("x-channel-id-v2", "WEB")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "sg")
                .header("x-country-id", "sg")
                .header("x-currency", "SGD")
                .header("x-platform-v2", "WEB")
                .header("x-request-id", "096f94af-225e-40b0-8b17-df14e408c62d")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Tour3 API Response ===");
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

