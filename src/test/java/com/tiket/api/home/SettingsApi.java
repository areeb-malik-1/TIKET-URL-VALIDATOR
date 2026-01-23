package com.tiket.api.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SettingsApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public SettingsApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-general-config/settings";
        String url = baseUrl + endpoint;
        String fullUrl = url + "?language=en";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken)
                .header("Cookie", "_cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000; __cf_bm=6Bn1q2FIrosp_KiF5.nHGPzhtNRemc3...jMY1j8A5A-1769163893-1.0.1.1-3xsmHW0yAQsOYGvEozrKW_H4ySbZl5dSM26_07bHM3Zr9YFYHJd8AOgdAqYaVg4DxTBoStDPMIsewlTzAoXH7HBEOvx2vTFL8te_eP6EX9OD0utGifKXtd2ZQ9.0cppE; __cf_bm=06vfXTpACMvD8rULW1WlE4N3oE6oMxVIVMspZEKCN9o-1769169254-1.0.1.1-wih9lYYVD7UR9iWcNnlSYTRa_6xGgdWdKYP1SYhs9lFkj4Uq2ZVqgOEoE0iG0ywvZac.zJl00UsRw6xsJMdqT7Wgjtav0jUKxyKmWO0c_Uaq1y7HoaHKpOkSRW.ldRZL; session_refresh_token=eyJraWQiOiJWWUM4SnpwWVZKbEJKbExrb3VGNnFlM19Wd0J2WUtlVyJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2N2FkOTYzYTkzZGNjODJhNWY3MWYwZGYiLCJuYmYiOjE3Mzk0Mjk0MzQsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTc3MDk4OTQzNH0.TDTum4MN1T2I5fnOWuTNfqpoiaDtA8jMtzCU9ONhmyByn_no2sA_-e61zPZ3Yvw7")
                .header("containername", "com.tiket.gits.HomeSplashActivity")
                .header("screenname", "com.tiket.gits.HomeSplashActivity")
                .header("x-correlation-id", "10863874-9f7f-462d-ba6b-265847bb3b8e|1768991461995")
                .header("deviceid", "179dd086888c94ec")
                .header("devicemodel", "Xiaomi+23108RN04Y")
                .header("osversion", "14")
                .header("appversion", "5.9.1-uat-HEAD")
                .header("tiketsessionid", "10863874-9f7f-462d-ba6b-265847bb3b8e")
                .header("platform", "ANDROID")
                .header("user-agent", "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/143.0.7499.146 Mobile Safari/537.36")
                .header("tiket-user-agent", "tiketcom/android-version (twh:20296642) - v5.9.1-uat-HEAD")
                .header("lang", "en")
                .header("currency", "IDN")
                .header("Accept-language", "en")
                .header("x-currency", "USD")
                .header("x-country-code", "IDN")
                .header("language", "en")
                .header("content-type", "application/json")
                .header("channelid", "ANDROID")
                //.header("if-none-match", "W/\"4b9909854d1160dd6606eefd136464cf\"")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Settings API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }
        isSuccess(response, data);
        return new ApiResult(data, response.statusCode());
    }

    public static void main(String[] args) throws Exception {
        SettingsApi api = new SettingsApi("your_access_token", "ANDROID", "https://api.tiket.com");
        api.hitApi();
    }
}