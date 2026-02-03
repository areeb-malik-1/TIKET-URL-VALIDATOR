package com.tiket.api.dWeb.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightSrpDomestic2Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightSrpDomestic2Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-master-discovery/general-content-search/v3/find";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        String body = """
                {
                  "page": "SEARCH_LIST",
                  "origin": "JKTC",
                  "destination": "DPSC",
                  "departureDate": "2026-02-09"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("content-type", "text/plain;charset=UTF-8")
                .header("Cookie", "_cfuvid=WIuioADx0I1kKqurcYPSEXEX_IMLmS4GYxcgBsUnMRw-1769866906164-0.0.1.1-604800000; device_id=b1910503-47c3-46b8-bb99-8f664cc4ad87; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJYR0tXZW5yY2IyZU9mdWNOY0ltUjFkS0lOeFk5ZVVQcCJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdlMDY5ZTUyZGIzYzJkNzMyZWE1NTciLCJuYmYiOjE3Njk4NjY5MTAsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTQyNjkxMH0.szVwE_ta7MAdFBLnbHWsp_NKBbkm5TxMkkvYr5BtwRkaG0wfLtjDvKNdriVLa415; userlang=en; tiket_currency=IDR; app_is_desktop=true; _gcl_au=1.1.762856922.1769866913; _ga=GA1.1.1429610085.1769866913; _fbp=fb.1.1769866914425.705769048640544610; _tt_enable_cookie=1; _ttp=01KGA4KTY0X17BKFTG2NDYRJ01_.tt.1; __cf_bm=3GG0N7UkgIaZYoqMAhNXBk2WcbM9FspY4BvfXDSCq7c-1769871855-1.0.1.1-4gvF1xRJLiGy8S6IpM0fZIFURFrVCyw4.DFOz.0cWGHwfuvEHcEYTgQcrVaQTIK2VK6xRB0yVWr4BnF92jxZ4u.5EIDGCRIKngTc26D7TBse9Ka.jl6h_fSUO4SA8tY3; app_is_webview=false; cf_clearance=AJ36U2lluXC4Y8rM1oxfTunQj02Rvx5Kgb2Qx6Ts0SI-1769871856-1.2.1.1-x33IZWF29QMQrn0Khto.uAg_8i.vRi8Mb5iHCwGFzMl080ZfBl5YqkJhQJ251OvuxEvlo5FPM2RO9hGPOwKVlUngLbVydJtaol_kUAEHsR7Fycyt6mzxlZ335aKeDGeWqnQEhyMUU0fJeX0DhHtM15YAOLCeWgXXppOrM.3Gz5PK3HPWeXUgl6xNymKhQAgMzdcWYc7dzFEd0XOkHJg_YJzdKUTaJHR2TXOJlLCg7tw; AMP_MKTG_b34eb5901c=JTdCJTdE; _ga_7H6ZDP2ZXG=GS2.1.s1769871855$o2$g1$t1769872012$j27$l0$h0; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJiMTkxMDUwMy00N2MzLTQ2YjgtYmI5OS04ZjY2NGNjNGFkODclMjIlMkMlMjJ1c2VySWQlMjIlM0ElMjIlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzY5ODcxODU2Njk2JTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTc2OTg3MjAxMzAxMiUyQyUyMmxhc3RFdmVudElkJTIyJTNBODElN0Q=; app_logger_correlation_id=57ea5483-8edc-4b2e-9876-221be07c3d05; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769871858255::j0CGzMkA7AWGVBiuE5TQ.2.1769872013291.1; ttcsid=1769871858256::NLgtZEksZVcValo2jKf0.2.1769872013291.0::1.155006.151359::154641.7.962.624::136751.3.0")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "b1910503-47c3-46b8-bb99-8f664cc4ad87")
                .header("lang", "en")
                .header("origin", baseUrl)
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/pesawat/search?d=JKTC&a=DPSC&date=2026-02-09&adult=1&child=0&infant=0&class=economy&dType=CITY&aType=CITY&dLabel=Jakarta&aLabel=Denpasar-Bali&type=depart&flexiFare=true")
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

        System.out.println("\n=== dWeb Flight SRP Domestic 2 API Response ===");
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

