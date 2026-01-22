package com.tiket.api.tour;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Tour1Api implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public Tour1Api(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-inventory/v1/products/additional-labels";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?ids=67bc2770bb6b7428a270f12c&ids=67fde17c183e666db4aa19b8&ids=635a6fd22e11343f14f98a70&ids=5e5d09130dcd8875a5216988&ids=673f607a5b68bb65a67283d9&ids=6045dbaacf770e5b39b14829&ids=66bb13f76b2c4d6a0d1a2c38&ids=60ff6bb2688c674dc5696caf&funnel=SRP";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .header("Cookie", "_cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000; __cf_bm=LJzEwAz54sylOdzZWF2JDTtzsKfmIBq39uhoGrBzZQQ-1767875167-1.0.1.1-82qCS6pP7eq6Zud0Q0FxLiaX6eg7Jwz73nmoo7eaepYS5tU1GuV2XXx4Qe.ptK3LgbU2s.vwY298AAl0jbswGn38qDVffHzlUW75x1CPt_lVH.XLqDLubwACHNq.nV9j")
                .header("useragent", "tiketcom/android-version (twh:20296642)")
                .header("accept", "application/json")
                .header("containername", "com.tiket.android.ttd.presentation.srpv2.SRPActivity")
                .header("screenname", "com.tiket.android.ttd.presentation.srpv2.fragment.SRPFragmentV2")
                .header("x-correlation-id", "f9df70e3-688b-4228-bf5a-3ca93420692a|1767875063300")
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

        System.out.println("\n=== Tour 1 API Response ===");
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
