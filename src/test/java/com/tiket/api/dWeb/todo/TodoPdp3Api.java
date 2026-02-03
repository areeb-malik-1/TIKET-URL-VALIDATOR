package com.tiket.api.dWeb.todo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TodoPdp3Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public TodoPdp3Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-order/v1/promotions";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("channelid", "WEB")
                .header("Cookie", "_cfuvid=WIuioADx0I1kKqurcYPSEXEX_IMLmS4GYxcgBsUnMRw-1769866906164-0.0.1.1-604800000; device_id=b1910503-47c3-46b8-bb99-8f664cc4ad87; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJYR0tXZW5yY2IyZU9mdWNOY0ltUjFkS0lOeFk5ZVVQcCJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdlMDY5ZTUyZGIzYzJkNzMyZWE1NTciLCJuYmYiOjE3Njk4NjY5MTAsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTQyNjkxMH0.szVwE_ta7MAdFBLnbHWsp_NKBbkm5TxMkkvYr5BtwRkaG0wfLtjDvKNdriVLa415; userlang=en; tiket_currency=IDR; app_is_desktop=true; _gcl_au=1.1.762856922.1769866913; _ga=GA1.1.1429610085.1769866913; _fbp=fb.1.1769866914425.705769048640544610; _tt_enable_cookie=1; _ttp=01KGA4KTY0X17BKFTG2NDYRJ01_.tt.1; app_is_webview=false; AMP_MKTG_b34eb5901c=JTdCJTdE; __cf_bm=h1mzNWUJqLyhGktGhMrrOs6Hxv7Vwu1WtODo_V.ip0Y-1769877768-1.0.1.1-NxWo8OJ.kBBE05xQRk24svLj69xiM1nmi2zHlTcY6eYqRYOd7kOlAiteyhEGyspNPVjuDnzwDhx.bdHsCe362ob6FxSI6xDZy4DIh55sNysPjTtrO813F1b29YubZQGb; cf_clearance=04BFAqK_B6sJwjd4cUy5S3TKoHFJR8MICswAQBJdwSU-1769877768-1.2.1.1-VzCCHxddxKDUIaKZT1F3QlNnDU6g8poLQR.IvJlDCYRcVMmUFbYPZPBae07ZqiX7mETLXb2wgLC9OLAIT6lBnMBj6VTD4FnAxbKxt4JdjTX1eiCoKh1pgqEjXf5UchuYJEw9jYEO7meorggCVC1h9l_NCnTA.kkNOo.A8H9.94luU25zttJp6Eb_y34dqPSRmjFmDo7disE5EYqCXadyXOzl98VhNq6z43C7D48kdQA; g_state=\"{\\\"i_l\\\":0}\"; app_logger_correlation_id=f880c95b-2410-4413-9f16-cfa14624ced3; _ga_7H6ZDP2ZXG=GS2.1.s1769877768$o3$g1$t1769877781$j47$l0$h0; ttcsid=1769876626583::RchChlyRJc8i1hdwEEOT.3.1769877783575.0; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769877770701::8nnMjeZRRocx2W3C3ToW.3.1769877783575.1; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJiMTkxMDUwMy00N2MzLTQ2YjgtYmI5OS04ZjY2NGNjNGFkODclMjIlMkMlMjJ1c2VySWQlMjIlM0ElMjIlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzY5ODc2NjIyNTc2JTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTc2OTg3Nzc4MzcyMSUyQyUyMmxhc3RFdmVudElkJTIyJTNBMTMxJTdE")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "b1910503-47c3-46b8-bb99-8f664cc4ad87")
                .header("lang", "en")
                .header("language", "EN")
                .header("platform", "WEB")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/to-do/taman-safari-indonesia-bogor?utm_logic=ED08B55301612DA7490BE093215C70CD&utm_section=pageModule%3B682f66765403d140aaeb01d4")
                .header("requestid", "NONE")
                .header("sec-ch-ua", "\"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"144\", \"Google Chrome\";v=\"144\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("serviceid", "GATEWAY")
                .header("storeid", "TIKETCOM")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .header("useragent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .header("x-audience", "tiket.com")
                .header("x-channel-id-v2", "WEB")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-platform-v2", "WEB")
                .header("x-request-id", "f880c95b-2410-4413-9f16-cfa14624ced3")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb TodoPdp3 API Response ===");
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

