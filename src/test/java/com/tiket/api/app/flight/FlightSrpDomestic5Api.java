package com.tiket.api.app.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.service.headers.CommonProdHeaders;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightSrpDomestic5Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightSrpDomestic5Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-search/v3/search/streaming";
        String fullUrl = baseUrl + endpoint;

        String jsonBody = "{\"dcVar\":true,\"enableVI\":true,\"fullRefundReschedule\":false,\"requestItems\":[\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUPlHPQEub2e3gKIEJwvhvjo29QdlKlCk7PIjKdOlVwX-g\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUOUNgzC-en3LbCy31SLWgbtSuI5b5jOTODKAggEoI8uQg\",\"qPysQBLP41OSVm8sWJxSE80ffSdysMJWby5ZvnaG59XZaTFyLEM2bG1lfhmTScoyIBlPfX7F9lj2gJPHUuWNRy87MQRLZCc1EQeuggOKM3SDeTFJzZ4lPNYchGDJ2GbL4xKkl96OjfRFtMRvixb5ow\",\"qPysQBLP41OSVm8sWJxSE80ffSdysMJWby5ZvnaG59XZaTFyLEM2bG1lfhmTScoyY6eTjvKR02wpNdDmg9MDRYlv8A6aZX7I0f8DKUKC13k4qyRcA-jpet8ibcI88r0NHFHOgyI09NT6Zpa9c73DHPHMBlrqPXOxMxyAmIDzFOY\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUMLZUanVSbs4c3KFV-K85UH1h05gJ23sH0tXT2ZU6cQdw\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOKLESMdMwycaFNAPK7yOPzvWRuqNafgofCSiFEFEbLhJuIHt4XljwSKpI9gFbQ-gfpSDZLsRy3w_xzvc4Z-upoA\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUM_2QaGRgqpCL1fVfiXD9VzGpMd1o1GaY8Vcxxo8ugaWt7cKv_FrkVjlCgwnbBDCNw\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUNDi3oS7nCpa91FLq31PmTbivf4ipcT0HAjc3N1W4zEAbZGlg4e7-sSzySnZ5nCG_o\",\"qPysQBLP41OSVm8sWJxSE80ffSdysMJWby5ZvnaG59XZaTFyLEM2bG1lfhmTScoyY6eTjvKR02wpNdDmg9MDRYlv8A6aZX7I0f8DKUKC13k_dL4Ga7P325A0q_mSHcpsjgkCndJz5xpKD6bdt3NohcDO6CD0y11D6o6gnFDEJN4\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUMRWQiO4r2FFYCWh-QT2oZaD3gowOPK5W2ZJcHEi-1jLQ\"],\"streamType\":\"OW\"}";

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        var builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("searchtrackerid", "827cb106-0eb7-4a4b-82e6-0b6f4301411f")
                .header("containername", "com.tiket.android.flight.presentation.searchresult.FlightSearchResultActivity")
                .header("x-app-sign", "CqcCARCnMGvjGFQS-HHXoaAfW-Ivm4TjsqACt6WM9_X1rzfZu-f-eY5QDDLrvEKIS-wCuj9zXOKvrnGKTF7_kpAQj4_9kQvOW1yFUFxwRsyB0C5rMRABO0AzeHuvrRxKFECalsT_ZI503NhcWG_jQh_FZgHrw0qv9hXIZ1WwP7o3V5n46R6w7NFVWOeLh9x2PD76M8-B9Un7p2eZ5-n2u7k81D7GmIWjgfbvS84kUhMe5KAJOddp4v43Uq-9C7yFi_vtEnneI9Unx8ujP0zetoW_3EHVg62Ch-rMWqK1I-bwLROTWCQLV028LAl9efPcoZC8ZuU9FfrESySypNjwcYj0OY_Ns90f4YKxVSMTnfdbWh-vT9WXRERWMAKzGp5ZmcoN3IqkMym4JBqAAQFT8Svt9Qm3Fj-Jtd5qvMUMlCTaRy4GvQXXvsKstEJZndz_9zVaAIk_0kI-7EIzS9zzXDdIjtuPzIKfkXwnV1j2iVN3gJitgUVBYBWw0dyj18YkO-gihU3UCSB7CVXSlC5bIl55pBYU04c4F-j9c_rsUe5E7sni30uGVEdpakwb")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Flight SRP Domestic5 API Response (streaming) ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + (data != null ? data.toPrettyString() : response.body()));
        }

        isSuccess(request, response, data, jsonBody);
        return new ApiResult(data, response.statusCode());
    }
}

