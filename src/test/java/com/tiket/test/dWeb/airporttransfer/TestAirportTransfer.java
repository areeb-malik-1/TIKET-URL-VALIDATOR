package com.tiket.test.dWeb.airporttransfer;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.dWeb.airporttransfer.AirportTransferApi;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestAirportTransfer extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        AirportTransferApi api = new AirportTransferApi(accessToken, baseUrl);
        apiResult = api.hitApi();
    }

    @Api(name = "AirportTransferApi")
    @Module(name = "AT")
    @Vertical(name = "NFT")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "urlDataProvider")
    public void testAirportTransferUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "AirportTransferApi")
    @Module(name = "AT")
    @Vertical(name = "NFT")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "endpointDataProvider")
    public void testAirportTransferEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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

