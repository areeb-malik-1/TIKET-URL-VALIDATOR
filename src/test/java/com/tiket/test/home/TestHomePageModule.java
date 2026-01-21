package com.tiket.test.home;

import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.api.home.HomePageModuleApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestHomePageModule extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestHomePageModule.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestHomePageModule.class.getName()).endpoints();;

    @BeforeClass
    public void beforeClass() throws Exception {
        HomePageModuleApi homePageModuleApi = new HomePageModuleApi(accessToken, "ANDROID", baseUrl);
        apiResult = homePageModuleApi.hitApi();
    }

    @Api(name = "HomePageModuleApi")
    @Vertical(name = "Grand")
    @Test(dataProvider = "urlDataProvider")
    public void testPageModuleUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "HomePageModuleApi")
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
