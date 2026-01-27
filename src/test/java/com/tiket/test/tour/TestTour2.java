package com.tiket.test.tour;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.tour.Tour2Api;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTour2 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestTour2.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestTour2.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        Tour2Api tour2Api = new Tour2Api(accessToken, baseUrl);
        apiResult = tour2Api.hitApi();
    }

    @Api(name = "Tour2Api")
    @Module(name = "Tour")
    @Vertical(name = "TTD")
    @Test(dataProvider = "urlDataProvider")
    public void testTour2Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "Tour2Api")
    @Module(name = "Tour")
    @Vertical(name = "TTD")
    @Test(dataProvider = "endpointDataProvider")
    public void testTour2Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
