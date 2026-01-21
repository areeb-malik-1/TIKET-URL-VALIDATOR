package com.tiket.test.airporttransfer;

import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.api.airporttransfer.AirportTransfer2Api;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestAirportTransfer2 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestAirportTransfer2.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestAirportTransfer2.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        AirportTransfer2Api airportTransfer2Api = new AirportTransfer2Api(accessToken, "ANDROID", baseUrl);
        apiResult = airportTransfer2Api.hitApi();
    }

    @Api(name = "AirportTransfer2Api")
    @Vertical(name = "Ground")
    @Test(dataProvider = "urlDataProvider")
    public void testAirportTransfer2Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "AirportTransfer2Api")
    @Vertical(name = "Ground")
    @Test(dataProvider = "endpointDataProvider")
    public void testAirportTransfer2Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
