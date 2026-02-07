package com.tiket.test.app.trainandwoosh;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.app.trainandwoosh.TrainAndWooshPageModule2Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.model.VerticalEnum;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTrainAndWooshPageModule2 extends BaseTest {

    ApiResult apiResult;
    String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        TrainAndWooshPageModule2Api trainAndWooshPageModule2Api = new TrainAndWooshPageModule2Api(accessToken, baseUrl);
        apiResult = trainAndWooshPageModule2Api.hitApi();
    }

    @Api(name = "TrainAndWooshPageModule2Api")
    @Module(name = "Train")
    @Vertical(name = VerticalEnum.TRAIN_WHOOSH)
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider")
    public void testTrainAndWooshPageModule2Url(VerifyUrls.UrlItem urlItem) throws Exception {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "TrainAndWooshPageModule2Api")
    @Module(name = "Train")
    @Vertical(name = VerticalEnum.TRAIN_WHOOSH)
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider")
    public void testTrainAndWooshPageModule2Endpoint(VerifyUrls.EndpointItem endpointItem) throws Exception {
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
