package com.tiket.api.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NavbarApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public NavbarApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {

        String endpoint = "/ms-gateway/tix-home/v2/navbars";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?platform=ANDROID";

        System.out.println("full url in navbar api: " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .header("Cookie", "_cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000; __cf_bm=VxiVGdHSSJdVgoUHB0GEAG52d0Ieu8SeFq6hmYMHSQg-1768377076-1.0.1.1-S9kTtnoS90L87V5gSwzQQB3bvedjQdbPPQkWGIiAwdWYCv_1dTHsFPFFjvNmbPf7Ushsx59ikP3WZ8lgOSzuuTba.XS8r7Ix5JhBR8M08KeHH8tgBFPu_7rc6pnyxGs4; session_refresh_token=eyJraWQiOiJWWUM4SnpwWVZKbEJKbExrb3VGNnFlM19Wd0J2WUtlVyJ9...")
                .header("containername", "com.tiket.gits.HomeSplashActivity")
                .header("screenname", "com.tiket.gits.HomeSplashActivity")
                .header("x-correlation-id", "33988b61-52f8-43ad-af66-447ff2ef4642|1768377028111")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion", "5.9.1-uat-HEAD")
                .header("tiketsessionid", "33988b61-52f8-43ad-af66-447ff2ef4642")
                .header("platform", "ANDROID")
                .header("user-agent", "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv)")
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

        System.out.println("\n=== Navbar API Response ===");
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
