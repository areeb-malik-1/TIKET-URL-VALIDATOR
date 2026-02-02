package com.tiket.test;

import com.tiket.test.app.home.TestHome;
import com.tiket.test.app.home.TestHomePageModule;
import com.tiket.test.app.home.TestNavbar;
import com.tiket.test.app.home.TestSetting;
import com.tiket.test.dWeb.airporttransfer.TestAirportTransfer;
import com.tiket.test.dWeb.carrental.TestCarRental;
import com.tiket.test.dWeb.flight.*;
import com.tiket.test.dWeb.home.TestHomePageModuledWeb;
import com.tiket.test.app.flight.TestFlight;
import com.tiket.test.app.flight.TestFlightPageModule;
import com.tiket.test.app.hotel.TestHotel;
import com.tiket.test.app.hotel.TestHotelPageModule;
import com.tiket.test.app.trainandwoosh.TestTrainAndWoosh;
import com.tiket.test.app.trainandwoosh.TestTrainAndWooshPageModule1;
import com.tiket.test.app.trainandwoosh.TestTrainAndWooshPageModule2;
import com.tiket.test.app.trainandwoosh.TestTrainAndWooshPageModule3;
import com.tiket.test.app.airporttransfer.TestAirportTransfer1;
import com.tiket.test.app.airporttransfer.TestAirportTransfer2;
import com.tiket.test.app.airporttransfer.TestAirportTransfer3;
import com.tiket.test.app.busandshuttle.TestBusAndShuttle1;
import com.tiket.test.app.busandshuttle.TestBusAndShuttle2;
import com.tiket.test.app.carrental.TestCarRental1;
import com.tiket.test.app.carrental.TestCarRental2;
import com.tiket.test.app.events.TestEvents1;
import com.tiket.test.app.events.TestEvents2;
import com.tiket.test.app.events.TestEvents3;
import com.tiket.test.app.tour.TestTour1;
import com.tiket.test.app.tour.TestTour2;
import com.tiket.test.app.tour.TestTour3;
import com.tiket.test.app.tour.TestTour4;
import com.tiket.test.app.ttd.TestTTDPageModule;
import com.tiket.test.app.todo.TestTodo1;
import com.tiket.test.app.todo.TestTodo2;
import com.tiket.test.app.vilasandapt.TestVilasAndApt1;
import com.tiket.test.app.vilasandapt.TestVilasAndApt2;
import com.tiket.test.app.vilasandapt.TestVilasAndAptPageModule;
import com.tiket.test.dWeb.home.TestHomePageModuledWeb;
import com.tiket.test.dWeb.hotel.TestHotelLandingPage;
import com.tiket.test.dWeb.hotel.TestHotelSRP1;
import com.tiket.test.dWeb.hotel.TestHotelSRP2;
import com.tiket.test.dWeb.todo.TestTodoPdp1;
import com.tiket.test.dWeb.todo.TestTodoPdp2;
import com.tiket.test.dWeb.todo.TestTodoPdp3;
import com.tiket.test.dWeb.todo.TestTodoPdp4;
import com.tiket.test.dWeb.trainandwoosh.TestTrainAndWooshPageModule;
import com.tiket.test.dWeb.vilasandapt.TestVilasAndApt;

import java.util.Map;

public class Mapping {
    public  record Data(String[] urls, String[] endpoints) {}
    public static final Map<String, Data> mapping = Map.ofEntries(
            // flight
            Map.entry(TestFlight.class.getName(), new Data(
                    new String[]{"icon"},
                    new String[]{"link"})),
            Map.entry(TestFlightPageModule.class.getName(), new Data(
                    new String[]{"icon", "url", "mobileUrl", "airlineIcon", "imageUrl"},
                    new String[]{"clickUrl", "actionUrl"})),
            // homepage
            Map.entry(TestHome.class.getName(), new Data(
                    new String[]{"icon", "active", "inactive", "backgroundImage", "globalSearchImage", "iconUrl", "supergraphicImage", "backgroundUrl", "mobileUrl", "airlineIcon", "image"},
                    new String[]{"url", "linkUrl", "clickUrl", "buttonUrl", "actionUrl", "link", "linkInactive"})),
            Map.entry(TestNavbar.class.getName(), new Data(
                    new String[]{"active", "inactive"},
                    new String[]{})),
            Map.entry(TestHomePageModule.class.getName(), new Data(
                    new String[]{"imageUrl"},
                    new String[]{"ctaUrl"})),
            Map.entry(TestSetting.class.getName(), new Data(
                    new String[]{"image"},
                    new String[]{})),
            // hotel
            Map.entry(TestHotel.class.getName(), new Data(
                    new String[]{"iconUrl", "image"},
                    new String[]{"url"})),
            Map.entry(TestHotelPageModule.class.getName(), new Data(
                    new String[]{"icon", "imageUrl", "supergraphicImage", "mobileUrl", "url"},
                    new String[]{"clickUrl", "buttonUrl", "actionUrl"})),
            // Vilas and Apt.
            Map.entry(TestVilasAndAptPageModule.class.getName(), new Data(
                    new String[]{"icon", "mobileUrl", "url", "imageUrl", "supergraphicImage"},
                    new String[]{"link", "actionUrl", "clickUrl", "buttonUrl"})),
            Map.entry(TestVilasAndApt1.class.getName(), new Data(
                    new String[]{"image", "iconUrl"},
                    new String[]{})),
            Map.entry(TestVilasAndApt2.class.getName(), new Data(
                    new String[]{"url", "mobileUrl"},
                    new String[]{})),
            // Train and Woosh
            Map.entry(TestTrainAndWoosh.class.getName(), new Data(
                    new String[]{"heroBannerImageUrl", "heroBannerImageUrlDesktop", "imageUrl"},
                    new String[]{})),
            Map.entry(TestTrainAndWooshPageModule1.class.getName(), new Data(
                    new String[]{"imageUrl"},
                    new String[]{"clickUrl"})),
            Map.entry(TestTrainAndWooshPageModule2.class.getName(), new Data(
                    new String[]{"imageUrl"},
                    new String[]{"clickUrl"})),
            Map.entry(TestTrainAndWooshPageModule3.class.getName(), new Data(
                    new String[]{"imageUrl"},
                    new String[]{"clickUrl"})),
            // Airport Transfer
            Map.entry(TestAirportTransfer1.class.getName(), new Data(
                    new String[]{"originalIcon", "destinationIcon"},
                    new String[]{})),
            Map.entry(TestAirportTransfer2.class.getName(), new Data(
                    new String[]{"image", "originalIcon", "destinationIcon"},
                    new String[]{})),
            Map.entry(TestAirportTransfer3.class.getName(), new Data(
                    new String[]{"imageUrl", "iconUrl"},
                    new String[]{"webUrl"})),
            // Bus and Shuttle
            Map.entry(TestBusAndShuttle1.class.getName(), new Data(
                    new String[]{"iconUrl"},
                    new String[]{})),
            Map.entry(TestBusAndShuttle2.class.getName(), new Data(
                    new String[]{"url"},
                    new String[]{})),
            // Car Rental
            Map.entry(TestCarRental1.class.getName(), new Data(
                    new String[]{"iconUrl"},
                    new String[]{})),
            Map.entry(TestCarRental2.class.getName(), new Data(
                    new String[]{"iconUrl"},
                    new String[]{"url"})),
            // Events
            Map.entry(TestEvents1.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{})),
            Map.entry(TestEvents2.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{})),
            Map.entry(TestEvents3.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{"url"})),
            // Tours
            Map.entry(TestTour1.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{})),
            Map.entry(TestTour2.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{})),
            Map.entry(TestTour3.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{})),
            Map.entry(TestTour4.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{"url"})),
            // TTD
            Map.entry(TestTTDPageModule.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{})),
            Map.entry(TestTodo1.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{"url"})),
            Map.entry(TestTodo2.class.getName(), new Data(
                    new String[]{"urlSmall", "urlMedium", "urlLarge"},
                    new String[]{})),

            // Web
            Map.entry(TestHomePageModuledWeb.class.getName(), new Data(
                    new String[]{"imageUrl"},
                    new String[]{"ctaUrl"})),
            Map.entry(TestAirportTransfer.class.getName(), new Data(
                    new String[]{"iconUrl"},
                    new String[]{})),
            Map.entry(TestCarRental.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.events.TestEvents1.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.events.TestEvents2.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.events.TestEvents3.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightLandingPage.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpDomesting1.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpDomesting2.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpDomesting3.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpDomesting4.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpDomesting5.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpInternational1.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpInternational2.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpInternational3.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpInternational4.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestFlightSrpInternational5.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestHotelLandingPage.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.hotel.TestHotelPageModule.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestHotelSRP1.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestHotelSRP2.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.todo.TestTodo1.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.todo.TestTodo2.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestTodoPdp1.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestTodoPdp2.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestTodoPdp3.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestTodoPdp4.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.tour.TestTour1.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.tour.TestTour2.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.tour.TestTour3.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.tour.TestTour4.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestTrainAndWooshPageModule.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(com.tiket.test.dWeb.ttd.TestTTDPageModule.class.getName(), new Data(
                    new String[]{},
                    new String[]{})),
            Map.entry(TestVilasAndApt.class.getName(), new Data(
                    new String[]{},
                    new String[]{}))
    );
}
