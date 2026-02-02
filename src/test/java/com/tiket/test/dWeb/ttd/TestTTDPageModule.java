package com.tiket.test.dWeb.ttd;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.dWeb.ttd.TTDPageModuleApi;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTTDPageModule extends BaseTest {

    private ApiResult apiResult;
    private final String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    private final String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        TTDPageModuleApi ttdPageModuleApi = new TTDPageModuleApi(accessToken, baseUrl);
        apiResult = ttdPageModuleApi.hitApi();
    }

    @Api(name = "TTDPageModuleApi")
    @Module(name = "To-Do")
    @Vertical(name = "Grand")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "urlDataProvider")
    public void testTTDPageModuleUrl(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "TTDPageModuleApi")
    @Module(name = "To-Do")
    @Vertical(name = "Grand")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "endpointDataProvider")
    public void testTTDPageModuleEndpoint(VerifyUrls.EndpointItem endpointItem) {
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
