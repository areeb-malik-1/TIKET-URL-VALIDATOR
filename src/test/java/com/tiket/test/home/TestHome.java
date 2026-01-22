package com.tiket.test.home;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.home.HomeApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.Assertion;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class TestHome extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestHome.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestHome.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        HomeApi homeApi = new HomeApi(accessToken, "ANDROID", baseUrl);
        apiResult = homeApi.hitApi();
        Assertion.assertThat("Check api response: ", isSuccess(apiResult), is(true));
        Assertion.assertThat("Check data is not null: ", apiResult.data(), is(notNullValue()));
    }

    @Api(name = "HomeApi")
    @Module(name = "Home")
    @Vertical(name = "Grand")
    @Test(dataProvider = "urlDataProvider")
    public void testHomeUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "HomeApi")
    @Module(name = "Home")
    @Vertical(name = "Grand")
    @Test(dataProvider = "endpointDataProvider")
    public void testHomeEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
