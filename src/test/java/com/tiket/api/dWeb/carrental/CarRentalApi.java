package com.tiket.api.dWeb.carrental;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CarRentalApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public CarRentalApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-car-rental-customer-searching/v2/banners";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("Cookie", "device_id=e2972c8e-c56d-445a-af65-242f36455ee2; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJzc1NMVjZDdl9VQ291aEJOMzVRUi1HU3JHYXJRd0FHbSJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMGZhYjUyZGIzYzJkNzNjZjZiN2QiLCJuYmYiOjE3Njk2NzI2MTksImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzMjYxOX0.YvrI9CMVUTB0lsbojQjU18T-BzcjS1Hc5K0PqHXJ6b4S7JxlwcEl6GaFKa9xwLFX; _gcl_au=1.1.2024533154.1769672621; shared_storage_tiket-com-analytic-session-id=%2205794265-06fc-4ede-8765-e7ff12ad46bf%22; shared_storage_tiket-com-analytic-old-user-id=%22%22; shared_storage_tiket-com-analytic=%7B%22tiketSessionId%22%3A%2205794265-06fc-4ede-8765-e7ff12ad46bf%22%2C%22language%22%3A%22EN%22%2C%22currency%22%3A%22IDR%22%2C%22country%22%3A%22ID%22%2C%22platform%22%3A%22mweb%22%2C%22isGuest%22%3A0%2C%22deviceId%22%3A%22e2972c8e-c56d-445a-af65-242f36455ee2%22%2C%22referrer%22%3A%22none%22%2C%22utmCampaign%22%3A%22none%22%2C%22utmContent%22%3A%22none%22%2C%22utmExternal%22%3A%22organic%22%2C%22utmId%22%3A%22none%22%2C%22utmMedium%22%3A%22none%22%2C%22utmSource%22%3A%22none%22%2C%22utmTerm%22%3A%22none%22%2C%22utmAttributedAt%22%3A%222026-01-29T07%3A43%3A41.398Z%22%2C%22ttoclid%22%3A%22none%22%2C%22tagmpid%22%3A%22none%22%2C%22tagttoclid%22%3A%22none%22%2C%22miniappsPartner%22%3A%22none%22%7D; AMP_MKTG_b34eb5901c=JTdCJTdE; _ga=GA1.1.191515909.1769672622; _fbp=fb.1.1769672622720.284665705305062351; _tt_enable_cookie=1; _ttp=01KG4BAGW1KE27BJ2EC3963AXZ_.tt.1; app_is_webview=false; app_is_desktop=false; cf_clearance=YMzX6jDTiq25fh8aIhzZULLIf2WGzkGOlnm5U_BfXcM-1769676413-1.2.1.1-BhE1lFGqE5.Dy4cFTX5FbYdowD8tq3vY3AojlZpX6iK00xzZhKPIoE80Pawge6Y9oWOrGkfdcvKaDopzGIGFU17TgdohfsORqe91lVA5ajxecL0gaIxt1RGvEcRIrt13kBU75kpi60wqtfWkKRttPAdlPQvWiLYiGn_qHxN0Y4UXsbzXzB4F6Io2V7chpsbZqRhX1uWSAgOk_dVwv7OLPO4g7mSO2OdNpXf_K0rk5FQ; ttcsid=1769672622980::PiVvf58S_SXY4YaTMyVN.1.1769676418314.0; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769672622980::jOHCCelONu4yfv91ufgu.1.1769676418314.1; shared_storage_tiket-com-analytic-last-activity=1769676419553; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJlMjk3MmM4ZS1jNTZkLTQ0NWEtYWY2NS0yNDJmMzY0NTVlZTIlMjIlMkMlMjJ1c2VySWQlMjIlM0ElMjIlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzY5NjcyNjIxNDAyJTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTc2OTY3NjQxOTY0MSUyQyUyMmxhc3RFdmVudElkJTIyJTNBMTIzJTdE; __cf_bm=otMqxZUuIt.qR9hSf2_MV_cXRW.jqVgRbGfus4ZPpT4-1769676419-1.0.1.1-vOzki.iTmD.kycFkGUO1sEz5_wvEiF6gxWWHzaj08VIW7Sn3rTAuJ0LkPMtRXJtmzXl5zHog01BIVNfowN0yAqOBLutIoqPTIQTJfRlDiyddOix6VGsaMI0rrGXzco25; _cfuvid=a4fXIbjsdUiwAvfypC9NZWVe7.sUPwRUh41ChsAroVA-1769676419894-0.0.1.1-604800000")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("lang", "en")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/sewa-mobil")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("x-audience", "tiket.com")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-request-id", "543c92bb-cfe8-412a-9429-c8e5ef116cbb")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Car Rental API Response ===");
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

