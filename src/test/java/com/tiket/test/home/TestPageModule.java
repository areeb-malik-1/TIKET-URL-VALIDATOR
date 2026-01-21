package com.tiket.test.home;

import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.api.home.PageModuleApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestPageModule extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestPageModule.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestPageModule.class.getName()).endpoints();;

    @BeforeClass
    public void beforeClass() throws Exception {
        PageModuleApi pageModuleApi = new PageModuleApi(accessToken, "ANDROID", baseUrl);
        apiResult = pageModuleApi.hitApi();
    }

    @Api(name = "PageModuleApi")
    @Vertical(name = "Grand")
    @Test(dataProvider = "urlDataProvider")
    public void testPageModuleUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "PageModuleApi")
    @Vertical(name = "Grand")
    @Test(dataProvider = "endpointDataProvider")
    public void testPageModuleEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
