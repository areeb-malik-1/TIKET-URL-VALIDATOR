package com.tiket.api.dWeb.hotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HotelSRP2Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public HotelSRP2Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-hotel-search/v4/search/filters";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        String body = "{\"accommodationType\":\"hotel\",\"adult\":2,\"childAges\":[],\"searchType\":\"REGION\",\"searchValue\":\"bali-108001534490276212\",\"startDate\":\"2026-02-07\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("content-type", "text/plain;charset=UTF-8")
                .header("Cookie", "__cf_bm=JWcT5qCqO0tdl3Wv7ML6kb0tDX5ReOCfMaGhnbAuaIo-1769866906-1.0.1.1-Y6sZT.40FpbNFj6zelbfX28H2idAt83fM29fVVWv4_gPQQwghBkHq1qDTZtLaa7osUFhdG99Zqk_.YveIyFblYWUFG8UTnTUABG3xIAOh_UzfQHxxx_3AmHa5vRmS4zJ; _cfuvid=WIuioADx0I1kKqurcYPSEXEX_IMLmS4GYxcgBsUnMRw-1769866906164-0.0.1.1-604800000; device_id=b1910503-47c3-46b8-bb99-8f664cc4ad87; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJYR0tXZW5yY2IyZU9mdWNOY0ltUjFkS0lOeFk5ZVVQcCJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdlMDY5ZTUyZGIzYzJkNzMyZWE1NTciLCJuYmYiOjE3Njk4NjY5MTAsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTQyNjkxMH0.szVwE_ta7MAdFBLnbHWsp_NKBbkm5TxMkkvYr5BtwRkaG0wfLtjDvKNdriVLa415; userlang=en; tiket_currency=IDR; app_is_desktop=true; cf_clearance=Rp8SJxvl0PX1870Wkeey2UM.MLqsxCYWQEJh1lyookA-1769866912-1.2.1.1-xdhiSh1bHl9R.1rUWI2LrTGqvbw6BKjd.oixUAo4uUkL5xoF0eiPABaILZvxCXcuqC2HmZMv86UG6kK_OAU6qzhmYtGTSnU_O25N0o_nS9.4IzR4UdDFlvdZV_fWTssfkxZGowjuXvIdgeb3b2L_l8AXmftVgXsWb1etO.tNHX_vFeY9LeKIDcg9mxIKBP9utgdUqhyh6Mh4l.KNGPYvzZeXl3WLBwljULVWBNC9zbM; _gcl_au=1.1.762856922.1769866913; AMP_MKTG_b34eb5901c=JTdCJTdE; _ga=GA1.1.1429610085.1769866913; _fbp=fb.1.1769866914425.705769048640544610; _tt_enable_cookie=1; _ttp=01KGA4KTY0X17BKFTG2NDYRJ01_.tt.1; _ga_7H6ZDP2ZXG=GS2.1.s1769866913$o1$g1$t1769866932$j41$l0$h0; app_logger_correlation_id=62ff8b23-96dc-4f1b-93be-aedd6e54de03; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769866914754::pkBP5903DdcbMEDxxoGJ.1.1769866939012.1; ttcsid=1769866914755::u-nZwWoqO6eizGm5K2JN.1.1769866939012.0; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJiMTkxMDUwMy00N2MzLTQ2YjgtYmI5OS04ZjY2NGNjNGFkODclMjIlMkMlMjJ1c2VySWQlMjIlM0ElMjIlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzY5ODY2OTEzMDc3JTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTc2OTg2NjkzOTA0NiUyQyUyMmxhc3RFdmVudElkJTIyJTNBMjklN0Q=")
                .header("countrycode", "IDN")
                .header("currency", "IDR")
                .header("deviceid", "b1910503-47c3-46b8-bb99-8f664cc4ad87")
                .header("lang", "en")
                .header("origin", baseUrl)
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/hotel/search?room=1&adult=2&id=bali-108001534490276212&type=REGION&q=Bali&checkin=2026-02-07&checkout=2026-02-08")
                .header("sec-ch-ua", "\"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"144\", \"Google Chrome\";v=\"144\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .header("utm-external", "organic")
                .header("utm-medium", "none")
                .header("utm-source", "none")
                .header("x-audience", "tiket.com")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "IDN")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-request-id", "62ff8b23-96dc-4f1b-93be-aedd6e54de03")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Hotel SRP 2 Filters API Response ===");
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

