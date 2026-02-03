package com.tiket.test.app.flight;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.app.flight.FlightSrpDomestic1Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestFlightSrpDomestic1 extends BaseTest {

    private ApiResult apiResult;
    private final String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    private final String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    private record Route(String origin, String destination) {}

    @Factory(dataProvider = "apiDataProvider")
    @BeforeClass
    public void beforeClass(Route route) throws Exception {
        FlightSrpDomestic1Api api = new FlightSrpDomestic1Api(accessToken, baseUrl, route.origin, route.destination);
        apiResult = api.hitApi();
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider")
    public void testFlightSrpDomestic1Url(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider")
    public void testFlightSrpDomestic1Endpoint(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider
    public static Object[][] apiDataProvider() {
        return new Object[][]{
                {new Route("KNO", "CJG")},
                {new Route("CGK", "DPS")},
                {new Route("CGK", "PDG")},
                {new Route("CGK", "PGK")},
                {new Route("CGK", "PKU")},
                {new Route("CGK", "SUB")},
                {new Route("DTB", "CGK")},
                {new Route("CGK", "PNK")}
        };
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

