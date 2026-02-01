package com.tiket.test.app.events;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.app.events.EventsApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestEvents1 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        EventsApi eventsApi = new EventsApi(accessToken, baseUrl);
        apiResult = eventsApi.hitApi();
    }

    @Api(name = "EventsApi1")
    @Module(name = "Events")
    @Vertical(name = "TTD")
    @Test(dataProvider = "urlDataProvider")
    public void testEventsUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "EventsApi1")
    @Module(name = "Events")
    @Vertical(name = "TTD")
    @Test(dataProvider = "endpointDataProvider")
    public void testEventsEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
