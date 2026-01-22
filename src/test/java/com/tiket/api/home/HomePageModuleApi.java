package com.tiket.api.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HomePageModuleApi implements BaseApi {

    private final String accessToken;
    private final String platform;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HomePageModuleApi(String accessToken, String platform, String baseUrl) {
        this.accessToken = accessToken;
        this.platform = platform;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {

        String endpoint = "/ms-gateway/tix-home/v2/page-modules/688b70ef8c681124d54c211c";;
        String url = baseUrl + endpoint;
        String fullUrl = url + "?gvVariant=oldUI"
                + "&isNotificationActive=false"
                + "&accommPriceBeforeTax=true"
                + "&recommendationVersion=";

        System.out.println("full url in page module api: " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + this.accessToken).header("Cookie",
                        "__cf_bm=IpvAPlLGvT3oP2wSvV0hrhPqNHTPKGvcnrlaT4mgVys-1768991582-1.0.1.1-RuSi4zp7klNEgZckX4kn.cDHgCmw6gsxOZ.A8y4Xwprx5zNy5DeqmIqxqfjSveuuDryRuwf.TtePJobGXjD0iML8pFB5MDMstvM55Aeat9U4AAgHieYksWCrRmn64Ema; " +
                                "_cfuvid=IbTKNTKBYrm2NbunQr5rbZmPEa1c09jI07g_gUNOFpE-1768991582067-0.0.1.1-604800000")

                .header("containername", "com.tiket.gits.HomeSplashActivity")
                .header("screenname", "com.tiket.android.homev4.screens.hometabfragment.homev5.HomeTabV5Fragment")
                .header("x-correlation-id", "10863874-9f7f-462d-ba6b-265847bb3b8e|1768991466982")

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
                .header("if-modified-since", "Wed, 14 Jan 2026 07:52:27 GMT")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Page Module API Response ===");
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