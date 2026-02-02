package com.tiket.api.dWeb.airporttransfer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AirportTransferApi implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public AirportTransferApi(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-airport-transfer-location/v3/suggestions?locationType=AIRPORT&type=OUT&pos=&keyword=";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("Cookie", "device_id=e2972c8e-c56d-445a-af65-242f36455ee2; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJzc1NMVjZDdl9VQ291aEJOMzVRUi1HU3JHYXJRd0FHbSJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMGZhYjUyZGIzYzJkNzNjZjZiN2QiLCJuYmYiOjE3Njk2NzI2MTksImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzMjYxOX0.YvrI9CMVUTB0lsbojQjU18T-BzcjS1Hc5K0PqHXJ6b4S7JxlwcEl6GaFKa9xwLFX; _gcl_au=1.1.2024533154.1769672621; _ga=GA1.1.191515909.1769672622; _fbp=fb.1.1769672622720.284665705305062351; _tt_enable_cookie=1; _ttp=01KG4BAGW1KE27BJ2EC3963AXZ_.tt.1; app_is_webview=false; app_is_desktop=false; cf_clearance=OI5vJqd_N1qPgxj1oOz1mYCc9yRH.Wx6yKHmrlsgf7o-1769675907-1.2.1.1-DZzHWV5Ie0rYDOwDJAlLb96f19OPSDj7XnmNaEAV4DVDvZXT_CtCDxZQj3ZFePaSwqLrOwL7kFwwG_6ygeNbicvzZa2cwFt2O9abfwgjGnRTefgrsaNlqivO7BPbeaW0_s6X7wsZ9YFlZ1XNEioc8deNziqZf0IwJuFh_8yDVMq5YMPXNUe5fwPc0CwoGGFTkO06v7p9ep75IJibD6eQjEmPSHVD1UBZIf4ba5YXMb8; __cf_bm=_QxqgPDNsUgCBL1Cf06hMN2CgGXsHB9Scp0Bzff5ShM-1769675910-1.0.1.1-RnJo2NDlWO.iiJx7CAIB13BIlVjMzIKRfFrq_xD.vcjJzPNKdGfB8Ka_hiKe081x7gacKGRcAhANkgxrmK86caNjuqN6K6suSdmIl7Qpq1XuH341p.fjD8XdtgsHObjJ; _cfuvid=x1WHRTT4MpUt8F0cmXnS2Kq3a88MzYXCFMcRlsbJ5Hw-1769675910860-0.0.1.1-604800000")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("lang", "en")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/airport-transfer")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("x-audience", "tiket.com")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-request-id", "f5c2aeba-5303-43b3-9635-e82b2a8cab5a")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Airport Transfer API Response ===");
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

