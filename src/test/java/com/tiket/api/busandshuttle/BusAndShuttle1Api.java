package com.tiket.api.busandshuttle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BusAndShuttle1Api implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public BusAndShuttle1Api(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-bus-demand/v3/location";
        String url = baseUrl.replace("api.", "www.") + endpoint;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Cookie", "userlang=en; __cf_bm=bHyc7V0.W428cHfATM5vNr6pbjUkku0bKEYILvm_TPM-1767873365-1.0.1.1-fKK8JJVihcLWHnbZReOVsUZhwO3WqR5sqf4nenioZw.xS4hGy_lLaCHRjfx5WMnH6LVkRcBLficdLwrUC4qXNtLfo2C4YqxT9sS4i3IW0rII9Dpw5.kD4rmUtWFSzrUS; _cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000; tiket_currency=USD; device_type=Android; device_id=179dd086888c94ec; device_model=Xiaomi 23108RN04Y; os_version=14; app_version=5.9.1; tiket_session_id=f9df70e3-688b-4228-bf5a-3ca93420692a; Tiket-User-Agent=tiketcom/android-version/en (twh:20296642) - v5.9.1-uat-HEAD; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJEa1hiZS0zeVNLUTVPTTFBYjNVQ0FBQXZpUEhvY2IwWiJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTVmOWI1NjI1N2I4ZTEzNTdhZmJkNGIiLCJuYmYiOjE3Njc4NzMzNjYsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTc5OTQzMzM2Nn0.iJj8HY1OWuYuMoWl0hM3L6qb4xzxIrpb3IrfH8rvv7cPLZGrDtAldtdxUgHRC439; app_logger_correlation_id=8becde26-d051-4250-9008-cd7c9e16fb97; app_is_webview=true; app_is_desktop=false")
                .header("x-request-id", "8becde26-d051-4250-9008-cd7c9e16fb97")
                .header("sec-ch-ua-platform", "\"Android\"")
                .header("lang", "en")
                .header("sec-ch-ua", "\"Android WebView\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"")
                .header("sec-ch-ua-mobile", "?1")
                .header("x-currency", "USD")
                .header("deviceid", "179dd086888c94ec")
                .header("appversion", "5.9.1")
                .header("tiketsessionid", "f9df70e3-688b-4228-bf5a-3ca93420692a")
                .header("countrycode", "id")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
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
                .header("referer", "https://www.tiket.com/en-id/bus-travel?utm_page=home")
                .header("priority", "u=1, i")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Bus and Shuttle 1 API Response ===");
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
