package com.tiket.api.airporttransfer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AirportTransfer3Api implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public AirportTransfer3Api(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-airport-transfer-catalogue/v3/landing-page";
        String url = baseUrl.replace("api.", "www.") + endpoint;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Host", "www.tiket.com")
                .header("Cookie", "userlang=en; __cf_bm=bHyc7V0.W428cHfATM5vNr6pbjUkku0bKEYILvm_TPM-1767873365-1.0.1.1-fKK8JJVihcLWHnbZReOVsUZhwO3WqR5sqf4nenioZw.xS4hGy_lLaCHRjfx5WMnH6LVkRcBLficdLwrUC4qXNtLfo2C4YqxT9sS4i3IW0rII9Dpw5.kD4rmUtWFSzrUS; _cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000; tiket_currency=USD; device_type=Android; device_id=179dd086888c94ec; device_model=Xiaomi 23108RN04Y; os_version=14; app_version=5.9.1; tiket_session_id=f9df70e3-688b-4228-bf5a-3ca93420692a; Tiket-User-Agent=tiketcom/android-version/en (twh:20296642) - v5.9.1-uat-HEAD; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJEa1hiZS0zeVNLUTVPTTFBYjNVQ0FBQXZpUEhvY2IwWiJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTVmOWI1NjI1N2I4ZTEzNTdhZmJkNGIiLCJuYmYiOjE3Njc4NzMzNjYsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTc5OTQzMzM2Nn0.iJj8HY1OWuYuMoWl0hM3L6qb4xzxIrpb3IrfH8rvv7cPLZGrDtAldtdxUgHRC439; app_is_webview=true; app_is_desktop=false; _gcl_au=1.1.338692086.1767873314; _fbp=fb.1.1767873316357.518422402200143819; _ga_7H6ZDP2ZXG=GS2.1.s1767873483$o1$g0$t1767873483$j60$l0$h0; _ga=GA1.1.593203389.1767873483; app_logger_correlation_id=bef6a4ac-adf0-415a-beb3-c25b2b7afe79; country_code=us")
                .header("x-request-id", "bef6a4ac-adf0-415a-beb3-c25b2b7afe79")
                .header("sec-ch-ua-platform", "\"Android\"")
                .header("lang", "en")
                .header("sec-ch-ua", "\"Android WebView\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"")
                .header("sec-ch-ua-mobile", "?1")
                .header("x-currency", "USD")
                .header("deviceid", "179dd086888c94ec")
                .header("appversion", "5.9.1")
                .header("tiketsessionid", "f9df70e3-688b-4228-bf5a-3ca93420692a")
                .header("countrycode", "IDN")
                .header("x-country-code", "IDN")
                .header("x-country-id", "us")
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
                .header("referer", "https://www.tiket.com/airport-transfer?utm_page=home")
                .header("priority", "u=1, i")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Airport Transfer 3 API Response ===");
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
