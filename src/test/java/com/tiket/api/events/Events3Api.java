package com.tiket.api.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Events3Api implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public Events3Api(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-search/v3/product-cards";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?pageSize=8&sortField=popularityScore&sortDirection=DESC&ff-ttde-popularity-score-automation=true&ff-ttde-enter-search-improvement=true&excludeSubcategoryGroup=true";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("authorization", "Bearer " + this.accessToken)
                .header("Cookie", "__cf_bm=bHyc7V0.W428cHfATM5vNr6pbjUkku0bKEYILvm_TPM-1767873365-1.0.1.1-fKK8JJVihcLWHnbZReOVsUZhwO3WqR5sqf4nenioZw.xS4hGy_lLaCHRjfx5WMnH6LVkRcBLficdLwrUC4qXNtLfo2C4YqxT9sS4i3IW0rII9Dpw5.kD4rmUtWFSzrUS; _cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000")
                .header("useragent", "tiketcom/android-version (twh:20296642)")
                .header("accept", "application/json")
                .header("containername", "com.tiket.android.ttd.presentation.srpv2.SRPActivity")
                .header("screenname", "com.tiket.android.ttd.presentation.srpv2.fragment.SRPFragmentV2")
                .header("x-correlation-id", "f9df70e3-688b-4228-bf5a-3ca93420692a|1767874142770")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion", "5.9.1-uat-HEAD")
                .header("tiketsessionid", "f9df70e3-688b-4228-bf5a-3ca93420692a")
                .header("platform", "ANDROID")
                .header("user-agent", "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/143.0.7499.146 Mobile Safari/537.36")
                .header("tiket-user-agent", "tiketcom/android-version (twh:20296642) - v5.9.1-uat-HEAD")
                .header("lang", "en")
                .header("currency", "USD")
                .header("accept-language", "en")
                .header("x-currency", "USD")
                .header("x-country-code", "IDN")
                .header("language", "en")
                .header("content-type", "application/json")
                .header("channelid", "ANDROID")
                .header("if-modified-since", "Thu, 08 Jan 2026 12:07:23 GMT")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Events 3 API Response ===");
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
