package com.tiket.test.dWeb.flight;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.dWeb.flight.FlightSrpDomesting4Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestFlightSrpDomestic4 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        FlightSrpDomesting4Api api = new FlightSrpDomesting4Api(accessToken, baseUrl);
        apiResult = api.hitApi();
    }

    @Api(name = "FlightSrpDomesting4Api")
    @Module(name = "Flight")
    @Vertical(name = "Air")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "urlDataProvider")
    public void testFlightSrpDomesting4Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomesting4Api")
    @Module(name = "Flight")
    @Vertical(name = "Air")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "endpointDataProvider")
    public void testFlightSrpDomesting4Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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

