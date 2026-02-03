package com.tiket.test.app.hotel;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.app.hotel.HotelSrpPageModuleApi;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestHotelSrpPageModule extends BaseTest {

    private ApiResult apiResult;
    private final String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    private final String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        HotelSrpPageModuleApi api = new HotelSrpPageModuleApi(accessToken, baseUrl);
        apiResult = api.hitApi();
    }

    @Api(name = "HotelSrpPageModuleApi")
    @Vertical(name = "Hotel")
    @Module(name = "Hotel")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider")
    public void testHotelSrpPageModuleUrl(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "HotelSrpPageModuleApi")
    @Vertical(name = "Hotel")
    @Module(name = "Hotel")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider")
    public void testHotelSrpPageModuleEndpoint(VerifyUrls.EndpointItem endpointItem) {
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

