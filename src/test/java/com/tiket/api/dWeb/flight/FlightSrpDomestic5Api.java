package com.tiket.api.dWeb.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tiket.model.ApiResult;
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

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        // Build JSON body safely
        ObjectNode root = mapper.createObjectNode();
        ArrayNode items = mapper.createArrayNode();
        items.add("qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUMRWQiO4r2FFYCWh-QT2oZaD3gowOPK5W2ZJcHEi-1jLQ");
        items.add("qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUOUNgzC-en3LbCy31SLWgbtSuI5b5jOTODKAggEoI8uQg");
        items.add("qPysQBLP41OSVm8sWJxSE80ffSdysMJWby5ZvnaG59XZaTFyLEM2bG1lfhmTScoyY6eTjvKR02wpNdDmg9MDRYlv8A6aZX7I0f8DKUKC13k_dL4Ga7P325A0q_mSHcpsjgkCndJz5xpKD6bdt3NohcDO6CD0y11D6o6gnFDEJN4");
        items.add("qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUNDi3oS7nCpa91FLq31PmTbivf4ipcT0HAjc3N1W4zEAbZGlg4e7-sSzySnZ5nCG_o");
        items.add("qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUM_2QaGRgqpCL1fVfiXD9VzGpMd1o1GaY8Vcxxo8ugaWt7cKv_FrkVjlCgwnbBDCNw");
        items.add("qPysQBLP41OSVm8sWJxSE80ffSdysMJWby5ZvnaG59XZaTFyLEM2bG1lfhmTScoyIBlPfX7F9lj2gJPHUuWNRy87MQRLZCc1EQeuggOKM3SDeTFJzZ4lPNYchGDJ2GbL4xKkl96OjfRFtMRvixb5ow");
        items.add("qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUOqoAnnaELkmpM9IJe-f291hbOvShFUMODwoAc4TXGgbLvWZ2tsUZvEBOJDlLcwqfo");
        items.add("qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOKLESMdMwycaFNAPK7yOPzvWRuqNafgofCSiFEFEbLhJuIHt4XljwSKpI9gFbQ-gfpSDZLsRy3w_xzvc4Z-upoA");
        items.add("qPysQBLP41OSVm8sWJxSE8mTIPqLp-SDJwrbL6grEV4A9hpUlFyoKnqyGl8df_uOLxrQnvZq7BlqrtLdcdSPgQ038xcmohrpq5oUvesnfUMLZUanVSbs4c3KFV-K85UH1h05gJ23sH0tXT2ZU6cQdw");
        items.add("qPysQBLP41OSVm8sWJxSE80ffSdysMJWby5ZvnaG59XZaTFyLEM2bG1lfhmTScoyY6eTjvKR02wpNdDmg9MDRYlv8A6aZX7I0f8DKUKC13k4qyRcA-jpet8ibcI88r0NHFHOgyI09NT6Zpa9c73DHPHMBlrqPXOxMxyAmIDzFOY");
        root.set("requestItems", items);
        root.put("streamType", "OW");
        root.put("searchTrackerId", "48988e79-e34a-43eb-994c-9d12ce13d777");
        root.put("enableVI", "true");
        root.put("dcVar", true);
        String body = mapper.writeValueAsString(root);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("origin", baseUrl)
                .header("referer", baseUrl + "/en-id/pesawat/search?d=JKTC&a=DPSC&date=2026-02-09&adult=1&child=0&infant=0&class=economy&dType=CITY&aType=CITY&dLabel=Jakarta&aLabel=Denpasar-Bali&type=depart&flexiFare=true")
                .header("Cookie", "_cfuvid=WIuioADx0I1kKqurcYPSEXEX_IMLmS4GYxcgBsUnMRw-1769866906164-0.0.1.1-604800000; device_id=b1910503-47c3-46b8-bb99-8f664cc4ad87; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJYR0tXZW5yY2IyZU9mdWNOY0ltUjFkS0lOeFk5ZVVQcCJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdlMDY5ZTUyZGIzYzJkNzMyZWE1NTciLCJuYmYiOjE3Njk4NjY5MTAsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTQyNjkxMH0.szVwE_ta7MAdFBLnbHWsp_NKBbkm5TxMkkvYr5BtwRkaG0wfLtjDvKNdriVLa415; userlang=en; tiket_currency=IDR; app_is_desktop=true; _gcl_au=1.1.762856922.1769866913; _ga=GA1.1.1429610085.1769866913; _fbp=fb.1.1769866914425.705769048640544610; _tt_enable_cookie=1; _ttp=01KGA4KTY0X17BKFTG2NDYRJ01_.tt.1; __cf_bm=3GG0N7UkgIaZYoqMAhNXBk2WcbM9FspY4BvfXDSCq7c-1769871855-1.0.1.1-4gvF1xRJLiGy8S6IpM0fZIFURFrVCyw4.DFOz.0cWGHwfuvEHcEYTgQcrVaQTIK2VK6xRB0yVWr4BnF92jxZ4u.5EIDGCRIKngTc26D7TBse9Ka.jl6h_fSUO4SA8tY3; app_is_webview=false; cf_clearance=AJ36U2lluXC4Y8rM1oxfTunQj02Rvx5Kgb2Qx6Ts0SI-1769871856-1.2.1.1-x33IZWF29QMQrn0Khto.uAg_8i.vRi8Mb5iHCwGFzMl080ZfBl5YqkJhQJ251OvuxEvlo5FPM2RO9hGPOwKVlUngLbVydJtaol_kUAEHsR7Fycyt6mzxlZ335aKeDGeWqnQEhyMUU0fJeX0DhHtM15YAOLCeWgXXppOrM.3Gz5PK3HPWeXUgl6xNymKhQAgMzdcWYc7dzFEd0XOkHJg_YJzdKUTaJHR2TXOJlLCg7tw; AMP_MKTG_b34eb5901c=JTdCJTdE; _ga_7H6ZDP2ZXG=GS2.1.s1769871855$o2$g1$t1769872012$j27$l0$h0; app_logger_correlation_id=57ea5483-8edc-4b2e-9876-221be07c3d05; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJiMTkxMDUwMy00N2MzLTQ2YjgtYmI5OS04ZjY2NGNjNGFkODclMjIlMkMlMjJ1c2VySWQlMjIlM0ElMjIlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzY5ODcxODU2Njk2JTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTc2OTg3MjAxMzUxOCUyQyUyMmxhc3RFdmVudElkJTIyJTNBODQlN0Q=; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769871858255::j0CGzMkA7AWGVBiuE5TQ.2.1769872013536.1; ttcsid=1769871858256::NLgtZEksZVcValo2jKf0.2.1769872013536.0::1.155006.151359::154641.7.962.624::155460.5.110")
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("content-type", "text/plain;charset=UTF-8")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "b1910503-47c3-46b8-bb99-8f664cc4ad87")
                .header("lang", "en")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("searchtrackerid", "48988e79-e34a-43eb-994c-9d12ce13d777")
                .header("sec-ch-ua", "\"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"144\", \"Google Chrome\";v=\"144\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .header("x-audience", "tiket.com")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-request-id", "57ea5483-8edc-4b2e-9876-221be07c3d05")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Flight SRP Domestic 5 API Response ===");
        System.out.println("Status: " + response.statusCode());

        JsonNode data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readTree(response.body());
            System.out.println("Response: " + data.toPrettyString());
        }

        isSuccess(request, response, data, body);
        return new ApiResult(data, response.statusCode());
    }
}
