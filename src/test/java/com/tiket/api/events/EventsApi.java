package com.tiket.api.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EventsApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public EventsApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-inventory/v1/products/additional-labels";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?ids=693fc3abc130446a1234257b&ids=6912a58a8f652a24e740b637&ids=6915928434e44e428f865117&ids=60794832fdf6074422b397b4&ids=6916a6854455e57413d86cf2&ids=607562c4d11a426d01c5e45f&ids=6914361a9e870c719931beeb&ids=6790700afe7e2d5c0462709c&funnel=SRP";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("authorization", "Bearer " + this.accessToken)
                .header("Cookie", "__cf_bm=bHyc7V0.W428cHfATM5vNr6pbjUkku0bKEYILvm_TPM-1767873365-1.0.1.1-fKK8JJVihcLWHnbZReOVsUZhwO3WqR5sqf4nenioZw.xS4hGy_lLaCHRjfx5WMnH6LVkRcBLficdLwrUC4qXNtLfo2C4YqxT9sS4i3IW0rII9Dpw5.kD4rmUtWFSzrUS; _cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000")
                .header("useragent", "tiketcom/android-version (twh:20296642)")
                .header("accept", "application/json")
                .header("containername", "com.tiket.android.ttd.presentation.srpv2.SRPActivity")
                .header("screenname", "com.tiket.android.ttd.presentation.srpv2.fragment.SRPFragmentV2")
                .header("x-correlation-id", "f9df70e3-688b-4228-bf5a-3ca93420692a|1767873930256")
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
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Events API Response ===");
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
