package com.tiket.test.trainandwoosh;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.api.trainandwoosh.TrainAndWooshApi;
import com.tiket.model.ApiResult;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTrainAndWoosh extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(TestTrainAndWoosh.class.getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(TestTrainAndWoosh.class.getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        TrainAndWooshApi trainAndWooshApi = new TrainAndWooshApi(accessToken, "ANDROID", baseUrl);
        apiResult = trainAndWooshApi.hitApi();
    }

    @Api(name = "TrainAndWooshApi")
    @Module(name = "Train")
    @Vertical(name = "NFT")
    @Test(dataProvider = "urlDataProvider")
    public void testTrainAndWooshUrl(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "TrainAndWooshApi")
    @Module(name = "Train")
    @Vertical(name = "NFT")
    @Test(dataProvider = "endpointDataProvider")
    public void testTrainAndWooshEndpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
