package com.tiket.test.vilasandapt;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.hotel.HotelApi;
import com.tiket.api.vilasandapt.VilasAndAptPageModuleApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.test.home.TestSetting;
import com.tiket.testbase.Assertion;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class TestVilasAndAptPageModule extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestSetting.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestSetting.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        VilasAndAptPageModuleApi vilasAndAptPageModuleApi = new VilasAndAptPageModuleApi(accessToken, "ANDROID", baseUrl);
        apiResult = vilasAndAptPageModuleApi.hitApi();
        Assertion.assertThat("Check api response: ", isSuccess(apiResult), is(true));
        Assertion.assertThat("Check data is not null: ", apiResult.data(), is(notNullValue()));
    }

    @Api(name = "VilasAndAptApi")
    @Module(name = "NHA")
    @Vertical(name = "Accommodation")
    @Test(dataProvider = "urlDataProvider")
    public void testSettingUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "VilasAndAptApi")
    @Module(name = "NHA")
    @Vertical(name = "Accommodation")
    @Test(dataProvider = "endpointDataProvider")
    public void testSettingEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
