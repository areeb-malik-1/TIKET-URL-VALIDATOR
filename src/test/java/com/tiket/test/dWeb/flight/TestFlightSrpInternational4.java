package com.tiket.test.dWeb.flight;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.dWeb.flight.FlightSrpInternational4Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.model.VerticalEnum;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestFlightSrpInternational4 extends BaseTest {

    ApiResult[] apiResults = new ApiResult[2];
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        String date = "2026-02-09";
        FlightSrpInternational4Api api1 = new FlightSrpInternational4Api(accessToken, baseUrl, "CGK", "SIN", date);
        FlightSrpInternational4Api api2 = new FlightSrpInternational4Api(accessToken, baseUrl, "CGK", "KU", date);
        apiResults[0] = api1.hitApi();
        apiResults[1] = api2.hitApi();
    }

    @Api(name = "FlightSrpInternational4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Int-CGK-SIN")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "urlDataProvider1")
    public void testFlightSrpInternational4Url1(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpInternational4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Int-CGK-SIN")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "endpointDataProvider1")
    public void testFlightSrpInternational4Endpoint1(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider1() {
        return getFullUrls(apiResults[0], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider1() {
        return getEndpoints(apiResults[0], endpointKeys);
    }

    @Api(name = "FlightSrpInternational4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Int-CGK-KU")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "urlDataProvider2")
    public void testFlightSrpInternational4Url2(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpInternational4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Int-CGK-KU")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "endpointDataProvider2")
    public void testFlightSrpInternational4Endpoint2(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider2() {
        return getFullUrls(apiResults[1], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider2() {
        return getEndpoints(apiResults[1], endpointKeys);
    }
}

