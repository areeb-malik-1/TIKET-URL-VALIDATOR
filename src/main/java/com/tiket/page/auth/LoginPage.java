package com.tiket.page.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiket.model.Environment;
import com.tiket.service.BaseUrl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage {
    private static final String URL_PREPROD = "https://sandbox.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";
    private static final String URL_PROD = "https://account.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";

    private final String identity;
    private final String secret;
    private final Environment env;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public LoginPage(String identity, String secret, Environment env) {
        this.identity = identity;
        this.secret = secret;
        this.env = env;
    }

    public record LoginResult(HttpResponse<String> response, Map<String, Object> data) {}

    public LoginResult hitApi() throws Exception {

//        String baseUrl = switch (env) {
//            case GK -> null;
//            case PROD -> "https://www.tiket.com";
//            case PREPROD -> "https://preprod.tiket.com";
//        };
//        String url = switch (env) {
//            case GK -> null;
//            case PROD -> "https://account.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";
//            case PREPROD -> "https://sandbox.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";
//        };
        String baseUrl = "https://www.tiket.com";
        String url = "https://account.bliblitiket.com/gateway/gks-unm-go-be/api/v1/auth/login";

        // Headers setup
        var headersBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        headersBuilder.header("accept", "application/json");
        headersBuilder.header("Content-Type", "application/json");
        headersBuilder.header("X-Client-Id", "9dc79e3916a042abc86c2aa525bff009");
        headersBuilder.header("Accept-Language", "en");
        headersBuilder.header("X-Country-Code", "ID");
        headersBuilder.header("True-Client-Ip", "127.0.0.1");
        headersBuilder.header("Cookie", "unm_device_id=8db0a266-80f0-4022-a189-548d46a20e9d; entity=tiket; shared_storage_bliblitiket-com-analytic=%7B%22utmCampaign%22%3A%22none%22%2C%22utmExternal%22%3A%22external%22%2C%22utmMedium%22%3A%22none%22%2C%22utmSource%22%3A%22none%22%2C%22referrer%22%3A%22none%22%2C%22fbclid%22%3A%22%22%2C%22gclid%22%3A%22%22%7D; _ga=GA1.1.1995835216.1761025416; enabledPasskey=on; AMP_12fecdb0db=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjJub25lJTIyJTJDJTIydXNlcklkJTIyJTNBMCUyQyUyMnNlc3Npb25JZCUyMiUzQTE3NjQ3NjQ4MzgxMDYlMkMlMjJvcHRPdXQlMjIlM0FmYWxzZSUyQyUyMmxhc3RFdmVudFRpbWUlMjIlM0ExNzY0NzY0ODM4MTA3JTJDJTIybGFzdEV2ZW50SWQlMjIlM0ExNjAlN0Q=; AMP_7db88c71bd=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjIxMDVlNDU4ZC00MTEwLTQzYmQtYmU5MS1kZjZlMGVjNTMwZGUlMjIlMkMlMjJ1c2VySWQlMjIlM0EwJTJDJTIyc2Vzc2lvbklkJTIyJTNBMTc2NjM4MTkyODA1OSUyQyUyMm9wdE91dCUyMiUzQWZhbHNlJTJDJTIybGFzdEV2ZW50VGltZSUyMiUzQTE3NjYzODE5NTc2MjglMkMlMjJsYXN0RXZlbnRJZCUyMiUzQTUwJTdE; _ga_CYXQFXTBW8=GS2.1.s1766381928$o12$g1$t1766381963$j25$l0$h0; _ga_M865CMWYXJ=GS2.1.s1766381928$o5$g1$t1766381963$j25$l0$h0; dash_unm_session_access_token=eyJhbGciOiJFZERTQSIsImtpZCI6ImJ4YlVDT1pPY3FrIiwidHlwIjoiSldUIn0.eyJfdGNzIjoibm9uZSIsIl90cnMiOiJub25lIiwiYWNjZXNzIjpbMjA5NzE1MSwwLDAsMF0sImVtYWlsIjoieWFudWFyLmt1cm5pYXdhbkB0aWtldC5jb20iLCJlbnRpdHkiOiJUSUtFVCIsImV4cCI6MTc2Nzc3NTE0NCwiaWF0IjoxNzY3NjAyMzQ0LCJpc1N1cGVyVXNlciI6ZmFsc2UsImlzcyI6Imh0dHBzOi8vc2FuZGJveC5ibGlibGl0aWtldC5jb20iLCJqdGkiOiJud3dtNGNJeFc2RkxxZFZzaDQ1SzZwVDEyUjQ5enFUZSIsIm5iZiI6MTc2NzYwMjM0NCwicm9sZUlkIjoxMDAwMCwic2Vzc2lvbklkIjoibnd3bTRjSXhXNkZMcWRWc2g0NUs2cFQxMlI0OXpxVGUiLCJzdWIiOiIxMDAwMDgyIiwidXNlcklkIjoxMDAwMDgyfQ.hy4ri4xu9dNZjX7rRwDmy1pOtaDKYQ0rAZ7tNSCUS4zU7Zwse_A4JznZ6vfWYWpSpGimR3En0rldwXc1ataUDw; dash_unm_userlang=en; _cfuvid=xptetbH7a9IdNCAmblPQDj8ExwXv7Jhir7LlKgEHHUc-1767692490784-0.0.1.1-604800000; dash_unm_tix_logger_correlation_id=3875ac7e-0701-4f45-a51e-15e09593c704; device_id=105e458d-4110-43bd-be91-df6e0ec530de; userlang=en; unm_cc=SG; cf_clearance=HJ9LH1cV_oEMZKyxNY2ba5aqpjmOUrS7IFII5oFH6g4-1767770490-1.2.1.1-liJgvpfnd_eOfVkhu5fDpPlBL.pv6GkWY.hg6AEvkfgLBPYDrbqo3IwUiiEwPu7CpzuO7UGQ77RFx13baK.VesceEcVUsy1JnOTyvSm8aWCYEJ5GHXqSNHPKLf0tSwmdeb7WXWvpKzP7VanCs7cIpJaq1zfczpL_jwXWyNyXy8SqlqdQGMTBCJfiYfit0QPuqMUgn6e4o5ZDmCm3UJ9TJbbJxGoWIr.78V6zhlRzrNA; fbsr_176613702824432=85fzp7RmJi6h_3nNeR0dy4Omxut8QHiY6XvCdgXqUtA.eyJ1c2VyX2lkIjoiMTIyMTQ0NjM5NzMyMDExMTYwIiwiY29kZSI6IkFRQ3NHd2ZSNlNMTmtkb3lXUkpyMFFIYjNtLVlTaGJBMUM0bzJVd3lDSkN1MWVsQmZjNHdFT0sxTV8xY29wWnJKU1FlRVJlVllia0VRM3VtbGV6OVNXUC02TDdrN29ObUFYRWFhUGVlUlQtM3BzRzRORzNyYTNzVkw0eGNQbGViX25Telp4cWpTcWRKT1BmTElNcjRsbzd4a1NaOG5DUll4ZVFTcFhzcHpNZUVqZVNRLThHc2N1a0hPZFNUOGppNXFZM05PLTZEWmJ1NDlaMjl1SjNTYVVfdWlWcGxqSHhXcS1Sc1lHZVhMNmJfWFdKbmNsbFUwRElKVm9MNER3UlU2WFh4X2NHUUhHRkctSzlmYUV4QnlIX0h1OXVlTUNzR2w3b1YtZGo5Y3F2Yk04S04xbGVkSmhMTFVZblpEVDNVWDU2bzFweTlOV3pvY1ZyRDF0LUhlV0JnIiwib2F1dGhfdG9rZW4iOiJFQUFDZ29SVUU4ZkFCUU9zd1JUcEVlZW9GUHh3aEI3bmI5YWNTalVsbVdOTHdSWkJHZFpBUDZkSGdNWUlzaGJyWkNaQ2FzSlhPYlpBWkJybWNJR1M0U09ZZWhRdmFVa3VDdlJlV1pBOWE5UDgxQVllWkNPQ3VqcXZ2OERBU0VuVHhIZmJzQ1NGMnlncjNmdVpCS1N5N2RIbVhyREtWUWhSTXZmWkFhNnpPT0ZtZFRwOGxWeGZId3ZaQVJBSzFtT0Rvcmc5UkFaRFpEIiwiYWxnb3JpdGhtIjoiSE1BQy1TSEEyNTYiLCJpc3N1ZWRfYXQiOjE3Njc3NzA1NDV9; searchParamsBeforeRedirect=%3FclientId%3D9dc79e3916a042abc86c2aa525bff009%26ref%3Dhttps%253A%252F%252Fwww.tiket.com%252Fen-sg%253Futm_section%253DnavigationBar%25253Blogin_label%2526utm_logic%253Dnone%26device_id%3D105e458d-4110-43bd-be91-df6e0ec530de%26lang%3Den%26utm_section%3DnavigationBar%253Blogin_label%26utm_logic%3Dnone%26skipPasskey%3Dtrue; _tix_logger_correlation_id=57fc0892-ed6d-4727-959e-cf7d5a192086; _ga_7H6ZDP2ZXG=GS2.1.s1767770542$o17$g1$t1767770549$j53$l0$h0; _ga_G3ZP2F3MW9=GS2.1.s1767770490$o19$g1$t1767770550$j60$l0$h2103917972; g_state={\"i_l\":0,\"i_ll\":1767770550836,\"i_b\":\"FUmUKU58vkUiz3BOPeCF5XEMITNbQadSa96Ti7Q+530\",\"i_e\":{\"enable_itp_optimization\":12}}; _gcl_au=1.1.1784299521.1761025416.1412709294.1767770557.1767770557; _ga_D4RSCKVS19=GS2.1.s1767770490$o19$g1$t1767770557$j53$l0$h0; AMP_b34eb5901c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjIxMDVlNDU4ZC00MTEwLTQzYmQtYmU5MS1kZjZlMGVjNTMwZGUlMjIlMkMlMjJ1c2VySWQlMjIlM0EwJTJDJTIyc2Vzc2lvbklkJTIyJTNBMTc2Nzc3MDQ5MTIyNyUyQyUyMm9wdE91dCUyMiUzQWZhbHNlJTJDJTIybGFzdEV2ZW50VGltZSUyMiUzQTE3Njc3NzA1NTczMjklMkMlMjJsYXN0RXZlbnRJZCUyMiUzQTU1MCU3RA==; __cf_bm=C4n_q4rtXwHjomcc1mwnLFUeQWjF3bVK9oAyiACiEuw-1767770557.4424558-1.0.1.1-o2ebgeoVV9D1doJZnnA4sZGPJCwxhJ1KYjQnRq5dBP_QS8Jod92g8ak5d3Z9cFzzrLyH5v2BLdc186QU_.AFaKbpeDOIRZlikBrM_476H6s8XEydHIWPNnvIiEFHQF5.; __cf_bm=nmvoJG.40t2lypS.liz5xmSbZwl9vm_HdqfTRNyMxc8-1767771135.802936-1.0.1.1-VjVoktQ1xgf_HZq263ZjAYRFpnsXJPyJgvP1nrUor8dQ_ZU9X5SeqmYP9fdrPE7yQZOXLz09PdeyJM.mK_d1dx111leuWn.YdlPy5d3hR6ahYxEaOfEJ1T1dZ7i8SzuN; session_access_token=eyJhbGciOiJFZERTQSIsImtpZCI6IkpxSllFRGt3bmZRIiwidHlwIjoiSldUIn0.eyJfdGNzIjoibm9uZSIsIl90cnMiOiJub25lIiwiYWNjZXNzIjpbMTUsMCwwLDBdLCJlbWFpbCI6InlhbnVhci5rdXJuaWF3YW5AdGlrZXQuY29tIiwiZXhwIjoxNzY3Nzc0Njc1LCJpYXQiOjE3Njc3NzExMzUsImlzcyI6Imh0dHBzOi8vYWNjb3VudC5ibGlibGl0aWtldC5jb20iLCJqdGkiOiJzcU1NWkRBLTdnM1pGY3J0VWc5RndLTFROcGJQd1JGeSIsIm5iZiI6MTc2Nzc3MTEzNSwicmVmcmVzaElkIjoiODU1YjI2ZTctMzhjNC00MzI4LThhNDMtZDhiMzVjMDA5N2JkIiwic2Vzc2lvbklkIjoic3FNTVpEQS03ZzNaRmNydFVnOUZ3S0xUTnBiUHdSRnkiLCJzdWIiOiIyNjE4NjEyNjQiLCJ0b3BpYyI6IkFDQ0VTU19UT0tFTiIsInVzZXJJZCI6MjYxODYxMjY0fQ.e-tZaN6lVolzkawDgfAxn7Yl0khUiksq1-68CXXReJPjgEsczWLduxw_UCsQH64wTLAvtGa7VpJStT3LuYrjDw; session_refresh_token=eyJhbGciOiJFZERTQSIsImtpZCI6IjVtSWxBcXlOc3c0IiwidHlwIjoiSldUIn0.eyJfdGNzIjoibm9uZSIsIl90cnMiOiJub25lIiwiZXhwIjoxNzY4OTgwNzM1LCJpYXQiOjE3Njc3NzExMzUsImlzcyI6Imh0dHBzOi8vYWNjb3VudC5ibGlibGl0aWtldC5jb20iLCJqdGkiOiJzcU1NWkRBLTdnM1pGY3J0VWc5RndLTFROcGJQd1JGeSIsIm5iZiI6MTc2Nzc3MTEzNSwicmVmcmVzaElkIjoiODU1YjI2ZTctMzhjNC00MzI4LThhNDMtZDhiMzVjMDA5N2JkIiwic2Vzc2lvbklkIjoic3FNTVpEQS03ZzNaRmNydFVnOUZ3S0xUTnBiUHdSRnkiLCJzdWIiOiIyNjE4NjEyNjQiLCJ0b3BpYyI6IlJFRlJFU0hfVE9LRU4iLCJ1c2VySWQiOjI2MTg2MTI2NH0.qll-OwGCbDPNg9ce1m0LyMLY5lHRRTdNu00a058kJSMiGjYMIzVofnJz9PGAdKiCE-M9rOhRS7oMNvIXAUjTBw; unm_device_id=ee290bbb-e390-435b-9d78-1dc43dc0f632; __cf_bm=qCOoZrBeC5hHIHPtW5SpEj6pxNn.9F2l7arJEwO5b44-1767784726-1.0.1.1-GECF0blPkZc4xuXc0ELgysU0r0h42ALl83cNeY50Ldb8a7_C.lZPLL5i8NnjdkYh3EQzPLEDCx9n98AvzsLj_hZskV1xviH81i2HTyA3s90");

        Map<String, String> body = Map.of(
                "ref", baseUrl,
                "identity", this.identity,
                "secret", this.secret,
                "type", "EMAIL_PASSWORD"
        );

        System.out.println("url: " + baseUrl);

        HttpRequest request = headersBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());

        Map<String, Object> data = null;
        if (response.headers().firstValue("content-type").orElse("").contains("application/json")) {
            data = mapper.readValue(response.body(), Map.class);
            System.out.println("Response: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
        } else {
            System.out.println("Response (non-JSON): " + response.body().substring(0, Math.min(response.body().length(), 500)));
        }

        return new LoginResult(response, data);
    }

    public String extractAuthCode(HttpResponse<String> response) {

        Pattern TOKEN_PATTERN = Pattern.compile("(?:token|auth|access_token)=([^;]+)", Pattern.CASE_INSENSITIVE);

        if (response == null || response.headers() == null) {
            return null;
        }

        List<String> setCookies = response.headers().allValues("Set-Cookie");
        if (setCookies == null || setCookies.isEmpty()) {
            return null;
        }

        for (String cookie : setCookies) {
            Matcher matcher = TOKEN_PATTERN.matcher(cookie);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }
}
