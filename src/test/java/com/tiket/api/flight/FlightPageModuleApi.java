package com.tiket.api.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightPageModuleApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightPageModuleApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-home/v2/page-modules-full";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?pageModuleCode=FLIGHT_PAGE"
                + "&isNotificationActive=false"
                + "&accommPriceBeforeTax=true"
                + "&recommendationVersion=";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .header("authorization", "Bearer " + this.accessToken)
                .header("Cookie",
                        "__cf_bm=1PJsJFbKILXTV5Z7F.l78BOZ9sgc76UeFzkOItinuP0-1768991585-1.0.1.1-_3h9jd81JBAKMe3az94LuuxHpXfGYH3xikjMq.4htz1W8QVgx6MzzXpSn4lYkCniboSDLd6JMgj5YgXN2V3ClvldT8lBEGMDdacOV0UfVxxKsWB0lxm.mWn52YiqNwLG; " +
                                "_cfuvid=6cE6ZeXKdlbd0cedTou5Cq8umjoxFPfQToc3aABi3rs-1768991585906-0.0.1.1-604800000")
                .header("containername", "com.tiket.android.flight.presentation.landing.FlightLandingActivity")
                .header("screenname", "com.tiket.android.flight.presentation.landing.FlightLandingActivity")
                .header("x-correlation-id", "10863874-9f7f-462d-ba6b-265847bb3b8e|1768991587184")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion", "5.9.1-uat-HEAD")
                .header("tiketsessionid", "10863874-9f7f-462d-ba6b-265847bb3b8e")
                .header("platform", "ANDROID")
                .header("user-agent",
                        "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/143.0.7499.146 Mobile Safari/537.36")
                .header("tiket-user-agent", "tiketcom/android-version (twh:20296642) - v5.9.1-uat-HEAD")
                .header("lang", "en")
                .header("currency", "USD")
                .header("accept-language", "en")
                .header("x-currency", "USD")
                .header("x-country-code", "IDN")
                .header("language", "en")
                .header("content-type", "application/json")
                .header("channelid", "ANDROID")

                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== FlightPageModule API Response ===");
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
