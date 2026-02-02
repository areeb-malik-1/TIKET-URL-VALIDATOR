package com.tiket.test.dWeb.todo;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.dWeb.todo.TodoPdp4Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTodoPdp4 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        TodoPdp4Api api = new TodoPdp4Api(accessToken, baseUrl);
        apiResult = api.hitApi();
    }

    @Api(name = "TodoPdp4Api")
    @Module(name = "Todo")
    @Vertical(name = "TTD")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "urlDataProvider")
    public void testTodoPdp4Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "TodoPdp4Api")
    @Module(name = "Todo")
    @Vertical(name = "TTD")
    @Scope(platforms = {Platform.DWEB})
    @Test(dataProvider = "endpointDataProvider")
    public void testTodoPdp4Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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

