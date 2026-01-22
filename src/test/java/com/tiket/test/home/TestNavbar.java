package com.tiket.test.home;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.home.NavbarApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNavbar  extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestNavbar.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestNavbar.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        NavbarApi navbarApi = new NavbarApi(accessToken, "ANDROID", baseUrl);
        apiResult = navbarApi.hitApi();
    }

    @Api(name = "NavbarApi")
    @Module(name = "Home")
    @Vertical(name = "Grand")
    @Test(dataProvider = "urlDataProvider")
    public void testNavbarUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "NavbarApi")
    @Module(name = "Home")
    @Vertical(name = "Grand")
    @Test(dataProvider = "endpointDataProvider")
    public void testNavbarEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
