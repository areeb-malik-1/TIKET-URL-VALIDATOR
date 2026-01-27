package com.tiket.test.busandshuttle;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.busandshuttle.BusAndShuttle2Api;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestBusAndShuttle2 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestBusAndShuttle2.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestBusAndShuttle2.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        BusAndShuttle2Api busAndShuttle2Api = new BusAndShuttle2Api(accessToken, baseUrl);
        apiResult = busAndShuttle2Api.hitApi();
    }

    @Api(name = "BusAndShuttle2Api")
    @Module(name = "BUS")
    @Vertical(name = "NFT")
    @Test(dataProvider = "urlDataProvider")
    public void testBusAndShuttle2Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "BusAndShuttle2Api")
    @Module(name = "BUS")
    @Vertical(name = "NFT")
    @Test(dataProvider = "endpointDataProvider")
    public void testBusAndShuttle2Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
