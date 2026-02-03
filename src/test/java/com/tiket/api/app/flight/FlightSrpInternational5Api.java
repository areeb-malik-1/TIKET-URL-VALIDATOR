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

public class FlightSrpInternational5Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightSrpInternational5Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-search/v3/search/streaming";
        String fullUrl = baseUrl + endpoint;

        String jsonBody = "{\"dcVar\":true,\"enableVI\":true,\"fullRefundReschedule\":false,\"requestItems\":[\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ7sG_POU4C9IiCsfdJBRZMTbujZVQb7swMFuTmsxq6rPg\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ7PbbQjHlcpzHacMxLEtNC5z2a8GadMB_4bhS4OW7kebA\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ60ojtxDHexY9MQQ4EMzEAuO0436fxj2Vv0QUlez3R3xNLefi0aoMJGqFTNt4UOt5M\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ5GvTHAk-LuO8Vcftvfobxzdacv-APv7ucqTVavCA8ZHQ\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5UCRJQAhE4HLTFNDJ_3dNhhfMdeMvRoX_Tr1MOq8_gXorEDDtCehkF5HEXJXK78AaCU3kbmZF8VBt6fay2CPCrYGhGmrNkpu_b8dCINn_Pcg\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5UCRJQAhE4HLTFNDJ_3dNhhfMdeMvRoX_Tr1MOq8_gXorEDDtCehkF5HEXJXK78AYcufDjdTBVg7IwivpLKyh_sTH9aY7IESoTG13yQ3w3UbEjXtyX0tcxg9zkj5kCFSE\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5UCRJQAhE4HLTFNDJ_3dNhR40va1XkMvTUKTYWbE6-A6C_mmyEhaFcuZnxOx6kKDE4FdOrsEytIHtJ3tQMhclGnLyj1KoBCCD0gUWFtjEArw\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ5EaMZruTf3QtiPE0OR3ctNvQA2AGr2r2PTB8aKy5zAcg\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5UCRJQAhE4HLTFNDJ_3dNhhfMdeMvRoX_Tr1MOq8_gXorEDDtCehkF5HEXJXK78AbFE-LQTbFdEl8v7si4g5YcNWPza_L1RJaCnKC2IfD5iPycAtdpIpvYkKutKrWgOP8\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M1sF20NRMXZfdTEz0AlsU-k9_L1_xQN2xhGqhb98YcTzQXP564ock0vx9lBmW5k9vxpzbjRA4N_ryBGe5N-uBntA\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ73S1NpgOXTRm1Y0HSQPw5-M2w0BJuv6wFiil-V4QcG18pi5QC8jFfLh8OnGldbl4A\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ6lgqgMVrX4KHMoZXqoaCw21G10_r_D9f7oHEKgBcJfVA\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ5pF15giPHvMydWj0crKgHlsB-sIwYw04UXOeW6dwDY0w\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ6dLAml-5zqMqVJ0nn7DcIW-yEGMkoPhsj28wpK5oCbYT62f-9FZHrcpqwTGB6hxlE\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5UCRJQAhE4HLTFNDJ_3dNhhfMdeMvRoX_Tr1MOq8_gXorEDDtCehkF5HEXJXK78Ab5Xk2h9ZiBxe2mfy7JUub7gnKnWMfOoD1iFLYwp-tAzQ\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5UCRJQAhE4HLTFNDJ_3dNhhfMdeMvRoX_Tr1MOq8_gXorEDDtCehkF5HEXJXK78Ab-urC7rBNHxgLuDKHXHJvbELUQjmRZOHG8HDgcpyDpXA\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ4Ih6-YB-TtntipspxvZbDuBKttZIOQXA8usWmAyWTDpsBvC5gjmSFlGrgw3Kj_J0s\",\"qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV5_g0s6ZOXyIBDHyCLxY5M12QgwEXl73JnSPGHdLlGRtTMja1ZjkOR19T0JhgynwJ7lR_iDvVzfZcPhVUfptnjtJe8tBPNrV27TMl7VJT7hjT4EmnGwGuUgGdeJGkw1dag\"],\"streamType\":\"OW\"}";

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        var builder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("searchtrackerid", "9ac50753-5a79-4ac8-a1be-63a166ae5154")
                .header("x-app-sign", "CqcCARCnMGvjGFQS-HHXoaAfW-Ivm4TjsqACt6WM9_X1rzfZu-f-eY5QDDLrvEKIS-wCuj9zXOKvrnGKTF7_kpAQj4_9kQvOW1yFUFxwRsyB0C5rMRABO0AzeHuvrRxKFECalsT_ZI503NhcWG_jQh_FZgHrw0qv9hXIZ1WwP7o3V5n46R6w7NFVWOeLh9x2PD76M8-B9Un7p2eZ5-n2u7k81D7GmIWjgfbvS84kUhMe5KAJOddp4v43Uq-9C7yFi_vtEnneI9Unx8ujP0zetoW_3EHVg62Ch-rMWqK1I-bwLROTWCQLV028LAl9efPcoZC8ZuU9FfrESySypNjwcYj0OY_Ns90f4YKxVSMTnfdbWh-vT9WXRERWMAKzGp5ZmcoN3IqkMym4JBqAAQFT8SvteYhLqApUDZGKmVE7zq_NJ9GsjL1gzPM3qMGggUwCXFYg_9gBrDJTCtjJLgCm_dakIzDU5NfRmGdzkn92xjdnBTpZp2yCtNMdUKwTYFoWYIwPCbZxaCSXMwGgz1I8Pu6rOiWf0oj9htQEd_CcNc_tbSY148GAs6-tES_f")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        CommonProdHeaders.getHeaders(this.accessToken).forEach(builder::header);
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== Flight SRP International5 API Response (streaming) ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + (data != null ? data.toPrettyString() : response.body()));
        }

        isSuccess(response, data);
        return new ApiResult(data, response.statusCode());
    }
}

