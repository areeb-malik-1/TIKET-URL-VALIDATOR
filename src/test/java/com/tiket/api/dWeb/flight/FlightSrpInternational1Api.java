package com.tiket.api.dWeb.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FlightSrpInternational1Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public FlightSrpInternational1Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-flight-rme-discovery/template/find-layout";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("authorization", "Bearer " + accessToken)
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("Cookie", "_cfuvid=WIuioADx0I1kKqurcYPSEXEX_IMLmS4GYxcgBsUnMRw-1769866906164-0.0.1.1-604800000; device_id=b1910503-47c3-46b8-bb99-8f664cc4ad87; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJYR0tXZW5yY2IyZU9mdWNOY0ltUjFkS0lOeFk5ZVVQcCJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdlMDY5ZTUyZGIzYzJkNzMyZWE1NTciLCJuYmYiOjE3Njk4NjY5MTAsImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTQyNjkxMH0.szVwE_ta7MAdFBLnbHWsp_NKBbkm5TxMkkvYr5BtwRkaG0wfLtjDvKNdriVLa415; userlang=en; tiket_currency=IDR; app_is_desktop=true; _gcl_au=1.1.762856922.1769866913; _ga=GA1.1.1429610085.1769866913; _fbp=fb.1.1769866914425.705769048640544610; _tt_enable_cookie=1; _ttp=01KGA4KTY0X17BKFTG2NDYRJ01_.tt.1; __cf_bm=3GG0N7UkgIaZYoqMAhNXBk2WcbM9FspY4BvfXDSCq7c-1769871855-1.0.1.1-4gvF1xRJLiGy8S6IpM0fZIFURFrVCyw4.DFOz.0cWGHwfuvEHcEYTgQcrVaQTIK2VK6xRB0yVWr4BnF92jxZ4u.5EIDGCRIKngTc26D7TBse9Ka.jl6h_fSUO4SA8tY3; app_is_webview=false; AMP_MKTG_b34eb5901c=JTdCJTdE; cf_clearance=wtzkBUUUL8qPcG60QKiYjZYccnHeq1EZVWiQhcTXbZY-1769872640-1.2.1.1-tETDbrs.IGc39UCOtbgqccETI4PbUo1Z68WOnPX89383IFHpm3X.YBIYGOzIp2VyvxN0uN.qAcfMHmga8jx7pjMxSmDx3ibmKUzhs8nUe0enH8gnWQDCJ2zi.fwN0MAUV6gYfpe57RBIirz_QZBDitypSvnA8e9UvvBrl8RlPN_4AkDkMsD0MpUBg.2J8cmBT8F757KlHZh7J3ya6jMi49A5.8CDVS9T3wpEBdMMB3M; _ga_7H6ZDP2ZXG=GS2.1.s1769871855$o2$g1$t1769872669$j30$l0$h0; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJiMTkxMDUwMy00N2MzLTQ2YjgtYmI5OS04ZjY2NGNjNGFkODclMjIlMkMlMjJ1c2VySWQlMjIlM0ElMjIlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzY5ODcxODU2Njk2JTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTc2OTg3MjY2OTM4MSUyQyUyMmxhc3RFdmVudElkJTIyJTNBMTA0JTdE; app_logger_correlation_id=0c87522f-79b8-4bf2-ad03-d1964873ad11; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769871858255::j0CGzMkA7AWGVBiuE5TQ.2.1769872669763.1; ttcsid=1769871858256::NLgtZEksZVcValo2jKf0.2.1769872669763.0::1.811459.784422::811035.11.1107.617::155460.5.110")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "b1910503-47c3-46b8-bb99-8f664cc4ad87")
                .header("lang", "en")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/pesawat/search?d=JKTC&a=KULC&date=2026-02-09&adult=1&child=0&infant=0&class=economy&dType=CITY&aType=CITY&dLabel=Jakarta&aLabel=Kuala+Lumpur&type=depart&flexiFare=true")
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
                .header("x-request-id", "0c87522f-79b8-4bf2-ad03-d1964873ad11")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Flight SRP International 1 API Response ===");
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
