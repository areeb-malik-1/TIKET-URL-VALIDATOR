package com.tiket.test.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.annotation.Api;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class TestHome extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestHome.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestHome.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        HomeApi homeApi = new HomeApi(accessToken, "ANDROID", baseUrl);
        apiResult = homeApi.hitApi();
    }

    @Api(name = "HomeApi")
    @Vertical(name = "Grand")
    @Test(dataProvider = "urlDataProvider")
    public void testHomeUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        step("Verifying Url");
        log("Verifying: " + urlItem);
        log("Result: " + result);
        if(!result.ok()) failedResults.add(result);
        Assertion.assertThat("Status: " + result.status(), result.ok(), is(true));
    }

    @Api(name = "HomeApi")
    @Vertical(name = "Grand")
    @Test(dataProvider = "endpointDataProvider")
    public void testHomeEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        step("Verifying Endpoint");
        log("Verifying: " + endpointItem);
        log("Result: " + result);
        if(!result.ok()) failedResults.add(result);
        Assertion.assertThat("Status: " + result.status(), result.ok(), is(true));
    }

    @DataProvider(name = "urlDataProvider", parallel = true)
    public Object[][] urlDataProvider() {

        JsonNode data = apiResult.data();
        List<VerifyUrls.UrlItem> urlItems = new ArrayList<>();
        for (String key : urlKeys) {
            var iconUrls = VerifyUrls.findAllUrls(data, key);
            urlItems.addAll(iconUrls);
        }

        Object[][] objects = new Object[urlItems.size()][];
        for (int i = 0; i < urlItems.size(); i++) {
            objects[i] = new Object[]{urlItems.get(i)};
        }
        return objects;
    }

    @DataProvider(name = "endpointDataProvider", parallel = true)
    public Object[][] endpointDataProvider() {
        JsonNode data = apiResult.data();
        List<VerifyUrls.EndpointItem> endpointItems = new ArrayList<>();
        for (String key : endpointKeys) {
            var endpoints = VerifyUrls.findAllEndpoints(data, key);
            endpointItems.addAll(endpoints);
        }
        Object[][] objects = new Object[endpointItems.size()][];
        for (int i = 0; i < endpointItems.size(); i++) {
            objects[i] = new Object[]{endpointItems.get(i)};
        }
        return objects;
    }
}
