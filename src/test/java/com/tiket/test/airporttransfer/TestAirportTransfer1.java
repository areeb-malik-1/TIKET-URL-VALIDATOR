package com.tiket.test.airporttransfer;

import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.api.airporttransfer.AirportTransfer1Api;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestAirportTransfer1 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestAirportTransfer1.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestAirportTransfer1.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        AirportTransfer1Api airportTransfer1Api = new AirportTransfer1Api(accessToken, "ANDROID", baseUrl);
        apiResult = airportTransfer1Api.hitApi();
    }

    @Api(name = "AirportTransfer1Api")
    @Vertical(name = "Ground")
    @Test(dataProvider = "urlDataProvider")
    public void testAirportTransfer1Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "AirportTransfer1Api")
    @Vertical(name = "Ground")
    @Test(dataProvider = "endpointDataProvider")
    public void testAirportTransfer1Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
