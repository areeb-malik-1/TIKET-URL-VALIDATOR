package com.tiket.api.hotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HotelApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HotelApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-hotel-home-api/longstay";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?accommodationType=hotel";

        System.out.println("Hotel full url: " + accessToken);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .header("authorization", "Bearer " + this.accessToken)
                .header("Cookie",
                        "_cfuvid=JARHbqo74xbYSxYV3IOWyoD1MnT5mhxP3itQsEwULB8-1767867758268-0.0.1.1-604800000; "
                                + "__cf_bm=m.LN60hokLf0ZUe9g6.Rxr_v0YO3zOaeVAfmKrlybVo-1767870954-1.0.1.1-4BgikjN2yT0eQDJicnWOtA3sIsKWIqL.G3r9_vvAy1lyITU3eHoxZcn7Yb5FMonJ1DJKafOI2HN6Q0K_.UQUnCCF2.pFnnGo9Y83npAqW_noiMNVbjxkjOu2obP.hayr")
                .header("containername",
                        "com.tiket.android.hotelv2.presentation.landing.HotelLandingActivity")
                .header("screenname",
                        "com.tiket.android.hotelv2.presentation.landing.HotelLandingActivity")
                .header("x-correlation-id",
                        "c5f0154e-aa0b-481a-a5c7-ad9d5b3cc3ca|1767871430664")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion", "5.9.1-debug-NCT-20474/play_integrity")
                .header("tiketsessionid", "c5f0154e-aa0b-481a-a5c7-ad9d5b3cc3ca")
                .header("platform", "ANDROID")
                .header("user-agent",
                        "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv) "
                                + "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 "
                                + "Chrome/143.0.7499.146 Mobile Safari/537.36")
                .header("tiket-user-agent",
                        "tiketcom/android-version (twh:20296642) - v5.9.1-debug-NCT-20474/play_integrity")
                .header("lang", "en")
                .header("currency", "SGD")
                .header("accept-language", "en")
                .header("x-currency", "SGD")
                .header("x-country-code", "IDN")
                .header("language", "en")
                .header("content-type", "application/json")
                .header("channelid", "ANDROID")
                .header("if-modified-since", "Thu, 08 Jan 2026 10:27:47 GMT")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Hotel API Response ===");
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