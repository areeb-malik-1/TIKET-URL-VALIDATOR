package com.tiket.test.flight;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.flight.FlightPageModuleApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestFlightPageModule extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        FlightPageModuleApi flightPageModuleApi = new FlightPageModuleApi(accessToken, baseUrl);
        apiResult = flightPageModuleApi.hitApi();
    }

    @Api(name = "FlightPageModuleApi")
    @Module(name = "Flight")
    @Vertical(name = "Flight")
    @Test(dataProvider = "urlDataProvider")
    public void testFlightPageModuleUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightPageModuleApi")
    @Module(name = "Flight")
    @Vertical(name = "Flight")
    @Test(dataProvider = "endpointDataProvider")
    public void testFlightPageModuleEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
