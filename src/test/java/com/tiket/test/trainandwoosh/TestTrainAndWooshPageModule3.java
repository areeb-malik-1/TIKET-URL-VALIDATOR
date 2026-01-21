package com.tiket.test.trainandwoosh;

import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.api.trainandwoosh.TrainAndWooshPageModule3Api;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTrainAndWooshPageModule3 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestTrainAndWooshPageModule3.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestTrainAndWooshPageModule3.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        TrainAndWooshPageModule3Api trainAndWooshPageModule3Api = new TrainAndWooshPageModule3Api(accessToken, "ANDROID", baseUrl);
        apiResult = trainAndWooshPageModule3Api.hitApi();
    }

    @Api(name = "TrainAndWooshPageModule3Api")
    @Vertical(name = "Ground")
    @Test(dataProvider = "urlDataProvider")
    public void testTrainAndWooshPageModule3Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "TrainAndWooshPageModule3Api")
    @Vertical(name = "Ground")
    @Test(dataProvider = "endpointDataProvider")
    public void testTrainAndWooshPageModule3Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
