package com.tiket.api.app.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.service.headers.CommonProdHeaders;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HomePageModuleApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HomePageModuleApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {

        String endpoint = "/ms-gateway/tix-home/v2/page-modules/688b70ef8c681124d54c211c";
        String url = baseUrl.replace("www", "api") + endpoint;
        String fullUrl = url + "?gvVariant=oldUI"
                + "&isNotificationActive=false"
                + "&accommPriceBeforeTax=true"
                + "&plaftorm=" + platform.name()
                + "&recommendationVersion=";

        System.out.println("full url in page module api: " + fullUrl);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("containername", "com.tiket.gits.HomeSplashActivity")
//                .header("Cookie",
//                        "__cf_bm=IpvAPlLGvT3oP2wSvV0hrhPqNHTPKGvcnrlaT4mgVys-1768991582-1.0.1.1-RuSi4zp7klNEgZckX4kn.cDHgCmw6gsxOZ.A8y4Xwprx5zNy5DeqmIqxqfjSveuuDryRuwf.TtePJobGXjD0iML8pFB5MDMstvM55Aeat9U4AAgHieYksWCrRmn64Ema; " +
//                                "_cfuvid=IbTKNTKBYrm2NbunQr5rbZmPEa1c09jI07g_gUNOFpE-1768991582067-0.0.1.1-604800000")
                //.header("if-modified-since", "Wed, 14 Jan 2026 07:52:27 GMT")
                .GET();

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Page Module API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }
        isSuccess(request, response, data);
        return new ApiResult(data, response.statusCode());
    }
}