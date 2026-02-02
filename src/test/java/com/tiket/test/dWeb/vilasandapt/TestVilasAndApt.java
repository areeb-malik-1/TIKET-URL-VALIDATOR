package com.tiket.test.dWeb.vilasandapt;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.dWeb.vilasandapt.VilasAndAptApi;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestVilasAndApt extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        VilasAndAptApi api = new VilasAndAptApi(accessToken, baseUrl);
        apiResult = api.hitApi();
    }

    @Api(name = "VilasAndAptApi")
    @Module(name = "Homes")
    @Vertical(name = "Accommodation")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "urlDataProvider")
    public void testVilasAndAptUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "VilasAndAptApi")
    @Module(name = "Homes")
    @Vertical(name = "Accommodation")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "endpointDataProvider")
    public void testVilasAndAptEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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

