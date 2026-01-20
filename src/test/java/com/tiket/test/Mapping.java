package com.tiket.test;

import com.tiket.test.flight.TestFlight;
import com.tiket.test.flight.TestFlightPageModule;
import com.tiket.test.home.TestHome;
import com.tiket.test.home.TestNavbar;
import com.tiket.test.home.TestPageModule;
import com.tiket.test.home.TestSetting;

import java.util.Map;

public class Mapping {
    public  record Data(String[] urls, String[] endpoints) {}
    public static final Map<String, Data> mapping = Map.of(
            // flight
            TestFlight.class.getName(), new Data(
                    new String[]{"icon"},
                    new String[]{"link"}),
            TestFlightPageModule.class.getName(), new Data(
                    new String[]{"icon", "url", "mobileUrl", "airlineIcon", "imageUrl"},
                    new String[]{"clickUrl", "actionUrl"}),
            // home
            TestHome.class.getName(), new Data(
                    new String[]{"icon", "active", "inactive", "backgroundImage", "globalSearchImage", "iconUrl", "supergraphicImage", "backgroundUrl", "url", "mobileUrl", "airlineIcon", "image"},
                    new String[]{"url", "linkUrl", "clickUrl", "buttonUrl", "actionUrl", "link", "linkInactive"}),
            TestNavbar.class.getName(), new Data(
                    new String[]{"active", "inactive"},
                    new String[]{}),
            TestPageModule.class.getName(), new Data(
                    new String[]{"imageUrl"},
                    new String[]{"ctaUrl"}),
            TestSetting.class.getName(), new Data(
                    new String[]{"image"},
                    new String[]{})
    );
}
