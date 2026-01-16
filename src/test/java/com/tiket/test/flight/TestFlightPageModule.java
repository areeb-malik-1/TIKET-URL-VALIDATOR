package com.tiket.test.flight;

import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.api.flight.FlightPageModuleApi;
import com.tiket.model.ApiResult;
import com.tiket.testbase.Assertion;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class TestFlightPageModule extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = {"icon", "url", "mobileUrl", "airlineIcon", "imageUrl"};
    String[] endpointKeys = {"clickUrl", "actionUrl"};

    @BeforeClass
    public void beforeClass() throws Exception {
        FlightPageModuleApi flightPageModuleApi = new FlightPageModuleApi(accessToken, "ANDROID", baseUrl);
        apiResult = flightPageModuleApi.hitApi();
    }

    @Api(name = "FlightPageModuleApi")
    @Vertical(name = "Grand")
    @Test(dataProvider = "urlDataProvider")
    public void testFlightPageModuleUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        step("Verifying Url");
        log("Verifying: " + urlItem);
        log("Result: " + result);
        Assertion.assertThat("Url passed", result.ok(), is(true));
    }

    @Api(name = "FlightPageModuleApi")
    @Vertical(name = "Grand")
    @Test(dataProvider = "endpointDataProvider")
    public void testFlightPageModuleEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        step("Verifying Endpoint");
        log("Verifying: " + endpointItem);
        log("Result: " + result);
        Assertion.assertThat("Endpoint passed", result.ok(), is(true));
    }

    @DataProvider(name = "urlDataProvider")
    public Object[][] urlDataProvider() {

        JsonNode data = apiResult.data();
        List<VerifyUrls.UrlItem> urlItems = new ArrayList<>();
        for (String key : urlKeys) {
            var iconUrls = VerifyUrls.findAllUrls(data, key);
            urlItems.addAll(iconUrls);
        }

        Object[][] objects = new Object[urlItems.size()][];
        for (int i = 0; i < urlItems.size(); i++) {
            objects[i] = new Object[]{urlItems.get(i)};
        }
        return objects;
    }

    @DataProvider(name = "endpointDataProvider")
    public Object[][] endpointDataProvider() {
        JsonNode data = apiResult.data();
        List<VerifyUrls.EndpointItem> endpointItems = new ArrayList<>();
        for (String key : endpointKeys) {
            var endpoints = VerifyUrls.findAllEndpoints(data, key);
            endpointItems.addAll(endpoints);
        }
        Object[][] objects = new Object[endpointItems.size()][];
        for (int i = 0; i < endpointItems.size(); i++) {
            objects[i] = new Object[]{endpointItems.get(i)};
        }
        return objects;
    }
}
