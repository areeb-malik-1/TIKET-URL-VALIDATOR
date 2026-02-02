package com.tiket.api.dWeb.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.ApiResult;
import com.tiket.testbase.BaseApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Events3Api implements BaseApi {

    private final String accessToken;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public Events3Api(String accessToken, String baseUrl) {
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    @Override
    public ApiResult hitApi() throws Exception {
        String endpoint = "/ms-gateway/tix-events-v2-search/v3/product-cards?pageSize=12&title=&productAllCategoryCodes=EVENT&sortDirection=DESC&sortField=popularityScore&ff-ttde-popularity-score-automation=true&ff-ttde-enter-search-improvement=true";
        String fullUrl = baseUrl + endpoint;

        System.out.println("full url in " + getClass().getName() + ": " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "*/*")
                .header("accept-language", "en")
                .header("cache-control", "no-cache")
                .header("channelid", "WEB")
                .header("Cookie", "device_id=e2972c8e-c56d-445a-af65-242f36455ee2; session_access_token=" + this.accessToken + "; session_refresh_token=eyJraWQiOiJzc1NMVjZDdl9VQ291aEJOMzVRUi1HU3JHYXJRd0FHbSJ9.eyJhdWQiOiJ0aWtldC5jb20vcnQiLCJzdWIiOiI2OTdiMGZhYjUyZGIzYzJkNzNjZjZiN2QiLCJuYmYiOjE3Njk2NzI2MTksImlzcyI6Imh0dHBzOi8vd3d3LnRpa2V0LmNvbSIsImV4cCI6MTgwMTIzMjYxOX0.YvrI9CMVUTB0lsbojQjU18T-BzcjS1Hc5K0PqHXJ6b4S7JxlwcEl6GaFKa9xwLFX; _gcl_au=1.1.2024533154.1769672621; AMP_MKTG_b34eb5901c=JTdCJTdE; _ga=GA1.1.191515909.1769672622; _fbp=fb.1.1769672622720.284665705305062351; _tt_enable_cookie=1; _ttp=01KG4BAGW1KE27BJ2EC3963AXZ_.tt.1; app_is_webview=false; app_is_desktop=false; cf_clearance=YMzX6jDTiq25fh8aIhzZULLIf2WGzkGOlnm5U_BfXcM-1769676413-1.2.1.1-BhE1lFGqE5.Dy4cFTX5FbYdowD8tq3vY3AojlZpX6iK00xzZhKPIoE80Pawge6Y9oWOrGkfdcvKaDopzGIGFU17TgdohfsORqe91lVA5ajxecL0gaIxt1RGvEcRIrt13kBU75kpi60wqtfWkKRttPAdlPQvWiLYiGn_qHxN0Y4UXsbzXzB4F6Io2V7chpsbZqRhX1uWSAgOk_dVwv7OLPO4g7mSO2OdNpXf_K0rk5FQ; app_logger_correlation_id=bb93f9c0-6b9d-44e9-ba67-6747a72c6780; _ga_7H6ZDP2ZXG=GS2.1.s1769672621$o1$g1$t1769677008$j49$l0$h0; g_state={\"i_l\":2}; ttcsid=1769672622980::PiVvf58S_SXY4YaTMyVN.1.1769677009792.0; ttcsid_C6F1LR0B3BVPD5SJMP6G=1769672622980::jOHCCelONu4yfv91ufgu.1.1769677009793.1; __cf_bm=eqdrxF4LPRBF3sICMjReyuPBE_ZtSTaOSMO._f9uwjU-1769677010-1.0.1.1-qwnDiPqQBQqegNcLv0.kFPzFdUl_lbwS6bn8bNSjP9K4hWDH_TvjwwfWU1_YBoC7l4vhRZchcFy8dgZuq5MSJd_2ZVmogGrJItxmmD3AfZW2MffPuOv2q53sXSWrcoQq; _cfuvid=0p9n1shPUmK0Znzv7kJJpD8XW6oEJGNAki22wWlQepY-1769677010208-0.0.1.1-604800000")
                .header("countrycode", "id")
                .header("currency", "IDR")
                .header("deviceid", "e2972c8e-c56d-445a-af65-242f36455ee2")
                .header("lang", "en")
                .header("language", "EN")
                .header("platform", "WEB")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/en-id/to-do/search?title=&utm_source=INTERNAL&utm_medium=home&productAllCategoryCodes=EVENT")
                .header("requestid", "NONE")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("serviceid", "GATEWAY")
                .header("storeid", "TIKETCOM")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("useragent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
                .header("x-audience", "tiket.com")
                .header("x-channel-id-v2", "WEB")
                .header("x-cookie-session-v2", "true")
                .header("x-country-code", "id")
                .header("x-country-id", "id")
                .header("x-currency", "IDR")
                .header("x-platform-v2", "WEB")
                .header("x-request-id", "bb93f9c0-6b9d-44e9-ba67-6747a72c6780")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n=== dWeb Events3 API Response ===");
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

