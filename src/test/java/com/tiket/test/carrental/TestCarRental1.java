package com.tiket.test.carrental;

import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.api.carrental.CarRental1Api;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCarRental1 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestCarRental1.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestCarRental1.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        CarRental1Api carRental1Api = new CarRental1Api(accessToken, "ANDROID", baseUrl);
        apiResult = carRental1Api.hitApi();
    }

    @Api(name = "CarRental1Api")
    @Vertical(name = "NFT")
    @Test(dataProvider = "urlDataProvider")
    public void testCarRental1Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "CarRental1Api")
    @Vertical(name = "NFT")
    @Test(dataProvider = "endpointDataProvider")
    public void testCarRental1Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
