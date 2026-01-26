package com.tiket.service.headers;

import java.util.Map;

public class CommonProdHeaders {

    public static Map<String, String> getHeaders(String accessToken, String platform) {
        return Map.ofEntries(
                Map.entry("authorization", "Bearer " + accessToken),
                Map.entry("accept-language", "en"),
                Map.entry("appversion", "5.9.1-uat-HEAD"),
                Map.entry("channelid", platform),
                Map.entry("containername", "com.tiket.android.hotelv2.nha.presentation.landing.pagemodule.NhaLandingPageModuleActivity"),
                Map.entry("container-type", "application/json"),
                Map.entry("Cookie", "__cf_bm=bHyc7V0.W428cHfATM5vNr6pbjUkku0bKEYILvm_TPM-1767873365-1.0.1.1-fKK8JJVihcLWHnbZReOVsUZhwO3WqR5sqf4nenioZw.xS4hGy_lLaCHRjfx5WMnH6LVkRcBLficdLwrUC4qXNtLfo2C4YqxT9sS4i3IW0rII9Dpw5.kD4rmUtWFSzrUS; _cfuvid=z7gRF834XbUD6MB2gE6ex8mX_KDmHCZtfKc9AF8FBi0-1767873365186-0.0.1.1-604800000"),
                Map.entry("currency", "IDR"),
                Map.entry("deviceid", "179dd086888c94ec"),
                Map.entry("devicemodel", "Xiaomi+23108RN04Y"),
                Map.entry("lang", "en"),
                Map.entry("language", "en"),
                Map.entry("osversion", "14"),
                Map.entry("platform", platform),
                Map.entry("screenname", "com.tiket.android.hotelv2.nha.presentation.landing.pagemodule.NhaLandingPageModuleActivity"),
                Map.entry("tiket-user-agent", "tiketcom/android-version (twh:20296642) - v5.9.1-uat-HEAD"),
                Map.entry("tiketsessionid", "f9df70e3-688b-4228-bf5a-3ca93420692a"),
                Map.entry("user-agent", "Mozilla/5.0 (Linux; Android 14; 23108RN04Y Build/UP1A.231005.007; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/143.0.7499.146 Mobile Safari/537.36"),
                Map.entry("x-correlation-id", "f9df70e3-688b-4228-bf5a-3ca93420692a|1767874540699"),
                Map.entry("x-country-code", "IDN"),
                Map.entry("x-currency", "IDR")
        );
    }
}
