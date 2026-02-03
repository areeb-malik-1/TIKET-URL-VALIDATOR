package com.tiket.api.dWeb.vilasandapt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class VilasAndAptApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public VilasAndAptApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-hotel-home-api/longstay?accommodationType=nha";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("Cookie", "device_id=e2972c8e-c56d-445a-af65-242f36455ee2; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJzc1NMVjZDdl9VQ291aEJOMzVRUi1HU3JHYXJRd0FHbSJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMGZhYjUyZGIzYzJkNzNjZjZiN2QiLCJuYmYiOjE3Njk2NzI2MTksImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzMjYxOX0.YvrI9CMVUTB0lsbojQjU18T-BzcjS1Hc5K0PqHXJ6b4S7JxlwcEl6GaFKa9xwLFX; _gcl_au=1.1.2024533154.1769672621; _ga=GA1.1.191515909.1769672622; _fbp=fb.1.1769672622720.284665705305062351; _tt_enable_cookie=1; _ttp=01KG4BAGW1KE27BJ2EC3963AXZ_.tt.1; app_is_webview=false; app_is_desktop=false; __cf_bm=kVIQb0CDKIRC9J0JBAJuHw76zlS6HPbDaXR939ra.R0-1769674846-1.0.1.1-7aZJsE_ip13PeT8JHPuhSkqynrtelyXtWN_BLttKhRdUDnyA.lXDrebIIXY4V1td3cLlW_eY3P1vFVbztT17pY7PCbE67oJxQ.6xZmG22GduZ9QihwhWFSwFK5xh.Rvs; _cfuvid=BL2NarTK5voSjr7WgwaIEVtz8NvXnEBJVpKY6cdVjaE-1769674846376-0.0.1.1-604800000; cf_clearance=hPzEM9CS3SYhRVee.n_a3MWtljPj9Q0v3mpcFJAkZs4-1769674847-1.2.1.1-FOWo8OCu1_5MccmyZEl3GEYdpFGR7xyyYNM79Ss_R2lupjMnDIMDBZCWnkVmY1Yaw3DGrujBmNTVTTJhJlPFSlpfl0DQNnaMidM9HhoU2XjNnblocu73q7VnA0nYWwxDtLq8.I89P3QtLwkKKM9nD._._SpIwLgbZKOFEGQBZyMOvO1Pt.E.g4hiCU4iQee.MAvbNB0XlA149nCA5YY8nc6fxx8DWWZ5Hugwr1.Ax9Y; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJlMjk3MmM4ZS1jNTZkLTQ0NWEtYWY2NS0yNDJmMzY0NTVlZTIlMjIlMkMlMjJ1c2VySWQlMjIlM0ElMjIlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzY5NjcyNjIxNDAyJTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTc2OTY3NDg0NzEyMCUyQyUyMmxhc3RFdmVudElkJTIyJTNBNjglN0Q=; _ga_7H6ZDP2ZXG=GS2.1.s1769672621$o1$g1$t1769674848$j58$l0$h0; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769672622980::jOHCCelONu4yfv91ufgu.1.1769674848765.1; ttcsid=1769672622980::PiVvf58S_SXY4YaTMyVN.1.1769674848765.0; app_logger_correlation_id=c25433c9-9788-4ab1-91ec-1922cc060d70")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("lang", "en")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/homes")
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
                .header("x-request-id", "c25433c9-9788-4ab1-91ec-1922cc060d70")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb VilasAndApt API Response ===");
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

