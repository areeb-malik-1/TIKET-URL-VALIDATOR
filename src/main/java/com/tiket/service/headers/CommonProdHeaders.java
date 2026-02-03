package com.tiket.service.headers;

import com.tiket.model.Platform;

import java.util.HashMap;
import java.util.Map;

public class CommonProdHeaders {

    private static final Platform PLATFORM = Platform.parse(System.getProperty("platform"));

    public static Map<String, String> getHeaders(String accessToken) {

        Map<String, String> appHeaders = new HashMap<>(Map.ofEntries(
                Map.entry("authorization", "Bearer " + accessToken),
                Map.entry("accept-language", "en"),
                Map.entry("channelid", PLATFORM.name()),
                Map.entry("container-type", "application/json"),
                Map.entry("currency", "IDR"),
                Map.entry("lang", "en"),
                Map.entry("language", "en"),
                Map.entry("platform", PLATFORM.name()),
                Map.entry("x-country-code", "IDN"),
                Map.entry("x-currency", "IDR")
        ));

        Map<String, String> webHeaders = new HashMap<>(Map.ofEntries(
                Map.entry("accept", "*/*"),
                Map.entry("accept-language", "en-US,en;q=0.9"),
                Map.entry("cache-control", "no-cache"),
                Map.entry("pragma", "no-cache"),
                Map.entry("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1")
        ));

        switch (PLATFORM) {
            case ANDROID -> {
                appHeaders.put("appversion", "5.9.1-uat-HEAD");
                appHeaders.put("containername", "com.tiket.android.hotelv2.nha.presentation.landing.pagemodule.NhaLandingPageModuleActivity");
                appHeaders.put("deviceid", "179dd086888c94ec");
                appHeaders.put("osversion", "14");
                appHeaders.put("devicemodel", "Xiaomi+23108RN04Y");
                appHeaders.put("screenname", "com.tiket.android.hotelv2.nha.presentation.landing.pagemodule.NhaLandingPageModuleActivity");
                appHeaders.put("tiket-user-agent", "tiketcom/android-version (twh:20296642) - v5.9.1-uat-HEAD");
                appHeaders.put("tiketsessionid", "f9df70e3-688b-4228-bf5a-3ca93420692a");
                appHeaders.put("user-agent", "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/143.0.7499.146 Mobile Safari/537.36");
                appHeaders.put("x-correlation-id", "f9df70e3-688b-4228-bf5a-3ca93420692a|1767874540699");
                appHeaders.put("Cookie", "__cf_bm=bHyc7V0.W428cHfATM5vNr6pbjUkku0bKEYILvm_TPM-1767873365-1.0.1.1-fKK8JJVihcLWHnbZReOVsUZhwO3WqR5sqf4nenioZw.xS4hGy_lLaCHRjfx5WMnH6LVkRcBLficdLwrUC4qXNtLfo2C4YqxT9sS4i3IW0rII9Dpw5.kD4rmUtWFSzrUS; _cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000");
                return appHeaders;
            }
            case IOS -> {
                appHeaders.put("appversion", "5.9.1-uat-HEAD");
                appHeaders.put("deviceid", "EEF3DBF3-11FA-494B-8F9A-1B1DD80A0C0F");
                appHeaders.put("osversion", "18.7.3");
                appHeaders.put("devicemodel", "iPhone 12");
                appHeaders.put("screenname", "-");
                appHeaders.put("tiket-user-agent", "tiketcom/ios-version (twh:20420882) - v5.10.0");
                appHeaders.put("tiketsessionid", "BF59FB0D-B1F8-4D38-83EF-8F368B155123");
                appHeaders.put("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
                appHeaders.put("x-correlation-id", "YmMEnA");
                appHeaders.put("iscritical", "0");
                appHeaders.put("iswebapi", "0");
                appHeaders.put("Cookie", "_cfuvid=9SujpV7HL37W67He30hkHHfjM6Hk70PSuLGA6H6T3QE-1769671627309-0.0.1.1-604800000; __cf_bm=Fb6.5Dv5y_U3AfSV9GZZ8CQNAblyAQ1hRYWqgI21qx8-1769671627-1.0.1.1-dKDktDru2z0BP8vzUQ09yCPaIvGbcDONFwJ0hERSfQFpC6Kvn9LT30Dttu30CWc6C5dDMQN83_nRCv6dfKvoe.f1x2lK0X3L5DsjTX_1Q41_XEYb.245fSG317wy_atS");
                return appHeaders;
            }

            case DWEB -> {return webHeaders;}

            default -> throw new IllegalStateException("Unexpected value: " + PLATFORM);
        }
    }

    public static Map<String, String> getWebviewHeaders(String accessToken) {
        Map<String, String> webHeaders = new HashMap<>();
        switch (PLATFORM) {
            case ANDROID -> {
                webHeaders.put("authorization", "Bearer " + accessToken);
                webHeaders.put("devicemodel", "Samsung SM-A515F");
                webHeaders.put("language", "EN");
                webHeaders.put("x-request-id", "3c4dfaf1-0359-47c9-8e11-dae2ba58e4c9");
                webHeaders.put("sec-ch-ua-platform", "\"Android\"");
                webHeaders.put("useragent", "Mozilla/5.0 (Linux; Android 13; SM-A515F Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/144.0.7559.59 Mobile Safari/537.36");
                webHeaders.put("lang", "en");
                webHeaders.put("x-platform-v2", PLATFORM.name());
                webHeaders.put("sec-ch-ua", "\"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"144\", \"Android WebView\";v=\"144\"");
                webHeaders.put("storeid", "TIKETCOM");
                webHeaders.put("sec-ch-ua-mobile", "?1");
                webHeaders.put("x-currency", "IDR");
                webHeaders.put("deviceid", "77ad0f71f1c359c4");
                webHeaders.put("appversion", "5.10.1");
                webHeaders.put("tiketsessionid", "5427a0d2-4f99-4fcd-a3d8-7946387f7b05");
                webHeaders.put("countrycode", "IDN");
                webHeaders.put("requestid", "NONE");
                webHeaders.put("x-country-code", "IDN");
                webHeaders.put("x-country-id", "id");
                webHeaders.put("platform", "WEB");
                webHeaders.put("x-cookie-session-v2", "true");
                webHeaders.put("accept-language", "en");
                webHeaders.put("osversion", "13");
                webHeaders.put("currency", "IDR");
                webHeaders.put("x-audience", "tiket.com");
                webHeaders.put("user-agent", "Mozilla/5.0 (Linux; Android 13; SM-A515F Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/144.0.7559.59 Mobile Safari/537.36");
                webHeaders.put("channelid", "WEB");
                webHeaders.put("x-channel-id-v2", "WEB");
                webHeaders.put("serviceid", "GATEWAY");
                webHeaders.put("accept", "*/*");
                webHeaders.put("x-requested-with", "com.tiket.gits");
                webHeaders.put("sec-fetch-site", "same-origin");
                webHeaders.put("sec-fetch-mode", "cors");
                webHeaders.put("sec-fetch-dest", "empty");
                webHeaders.put("referer", "https://m.tiket.com");
                webHeaders.put("priority", "u=1, i");
                return webHeaders;
            }

            case IOS -> {
                webHeaders.put("authorization", "Bearer " + accessToken);
                webHeaders.put("devicemodel", "iPhone 12");
                webHeaders.put("language", "EN");
                webHeaders.put("x-request-id", "1d99f79d-755b-4543-9b9e-3d6e0988d323");
                webHeaders.put("useragent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
                webHeaders.put("lang", "en");
                webHeaders.put("x-platform-v2", PLATFORM.name());
                webHeaders.put("storeid", "TIKETCOM");
                webHeaders.put("x-currency", "IDR");
                webHeaders.put("deviceid", "EEF3DBF3-11FA-494B-8F9A-1B1DD80A0C0F");
                webHeaders.put("appversion", "5.10.1");
                webHeaders.put("tiketsessionid", "B9488B49-77DB-4809-82CD-0FD720C7CC02");
                webHeaders.put("countrycode", "IDN");
                webHeaders.put("requestid", "NONE");
                webHeaders.put("x-country-code", "IDN");
                webHeaders.put("x-country-id", "id");
                webHeaders.put("platform", "WEB");
                webHeaders.put("x-cookie-session-v2", "true");
                webHeaders.put("accept-language", "en");
                webHeaders.put("osversion", "13");
                webHeaders.put("currency", "IDR");
                webHeaders.put("x-audience", "tiket.com");
                webHeaders.put("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
                webHeaders.put("channelid", "WEB");
                webHeaders.put("x-channel-id-v2", "WEB");
                webHeaders.put("serviceid", "GATEWAY");
                webHeaders.put("accept", "*/*");
                webHeaders.put("sec-fetch-mode", "cors");
                webHeaders.put("sec-fetch-dest", "empty");
                webHeaders.put("referer", "https://m.tiket.com");
                return webHeaders;
            }

            default -> throw new IllegalStateException("Unexpected value: " + PLATFORM);
        }
    }
}
