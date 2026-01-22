package com.tiket.test.ttd;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.ttd.TTDPageModuleApi;
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

public class TestTTDPageModule extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestTTDPageModule.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestTTDPageModule.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        TTDPageModuleApi ttdPageModuleApi = new TTDPageModuleApi(accessToken, "ANDROID", baseUrl);
        apiResult = ttdPageModuleApi.hitApi();
        Assertion.assertThat("Check api response: ", isSuccess(apiResult), is(true));
        Assertion.assertThat("Check data is not null: ", apiResult.data(), is(notNullValue()));
    }

    @Api(name = "TTDPageModuleApi")
    @Module(name = "Todo")
    @Vertical(name = "TTD")
    @Test(dataProvider = "urlDataProvider")
    public void testTTDPageModuleUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "TTDPageModuleApi")
    @Module(name = "Todo")
    @Vertical(name = "TTD")
    @Test(dataProvider = "endpointDataProvider")
    public void testTTDPageModuleEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
