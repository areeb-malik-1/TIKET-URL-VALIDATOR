package com.tiket.test.app.hotel;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.app.hotel.HotelSrp2Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestHotelSrp2 extends BaseTest {

    private ApiResult apiResult;
    private final String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    private final String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        HotelSrp2Api api = new HotelSrp2Api(accessToken, baseUrl);
        apiResult = api.hitApi();
    }

    @Api(name = "HotelSrp2Api")
    @Vertical(name = "Hotel")
    @Module(name = "Hotel")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider")
    public void testHotelSrp2Url(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "HotelSrp2Api")
    @Vertical(name = "Hotel")
    @Module(name = "Hotel")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider")
    public void testHotelSrp2Endpoint(VerifyUrls.EndpointItem endpointItem) {
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
