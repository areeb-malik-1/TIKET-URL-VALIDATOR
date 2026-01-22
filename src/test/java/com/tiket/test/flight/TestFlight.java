package com.tiket.test.flight;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.flight.FlightApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestFlight extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestFlight.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestFlight.class.getName()).urls();

    @BeforeClass
    public void beforeClass() throws Exception {
        FlightApi flightApi = new FlightApi(accessToken, "ANDROID", baseUrl);
        apiResult = flightApi.hitApi();
    }

    @Api(name = "FlightApi")
    @Vertical(name = "Flight")
    @Module(name = "Flight")
    @Test(dataProvider = "urlDataProvider")
    public void testFlightUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightApi")
    @Vertical(name = "Flight")
    @Module(name = "Flight")
    @Test(dataProvider = "endpointDataProvider")
    public void testFlightEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(name = "urlDataProvider", parallel = true)
    public Object[][] urlDataProvider() {
        return getFullUrls(apiResult, urlKeys);
    }

    @DataProvider(name = "endpointDataProvider", parallel = true)
    public Object[][] endpointDataProvider() {
        return getEndpoints(apiResult, endpointKeys);
    }
}
