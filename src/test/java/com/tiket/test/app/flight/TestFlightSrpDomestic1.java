package com.tiket.test.app.flight;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.app.flight.FlightSrpDomestic1Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestFlightSrpDomestic1 extends BaseTest {

    private ApiResult[] apiResults = new ApiResult[8];
    private final String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    private final String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();


    @BeforeClass
    public void beforeClass() throws Exception {
        FlightSrpDomestic1Api api1 = new FlightSrpDomestic1Api(accessToken, baseUrl, "KNO", "CJG");
        FlightSrpDomestic1Api api2 = new FlightSrpDomestic1Api(accessToken, baseUrl, "CGK", "DPS");
        FlightSrpDomestic1Api api3 = new FlightSrpDomestic1Api(accessToken, baseUrl, "CGK", "PDG");
        FlightSrpDomestic1Api api4 = new FlightSrpDomestic1Api(accessToken, baseUrl, "CGK", "PGK");
        FlightSrpDomestic1Api api5 = new FlightSrpDomestic1Api(accessToken, baseUrl, "CGK", "PKU");
        FlightSrpDomestic1Api api6 = new FlightSrpDomestic1Api(accessToken, baseUrl, "CGK", "SUB");
        FlightSrpDomestic1Api api7 = new FlightSrpDomestic1Api(accessToken, baseUrl, "DTB", "CGK");
        FlightSrpDomestic1Api api8 = new FlightSrpDomestic1Api(accessToken, baseUrl, "CGK", "PNK");

        apiResults[0] = api1.hitApi();
        apiResults[1] = api2.hitApi();
        apiResults[2] = api3.hitApi();
        apiResults[3] = api4.hitApi();
        apiResults[4] = api5.hitApi();
        apiResults[5] = api6.hitApi();
        apiResults[6] = api7.hitApi();
        apiResults[7] = api8.hitApi();
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-KNO-CJG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider1")
    public void testFlightSrpDomestic1Url1(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-KNO-CJG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider1")
    public void testFlightSrpDomestic1Endpoint1(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider1() {
        return getFullUrls(apiResults[0], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider1() {
        return getEndpoints(apiResults[0], endpointKeys);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-DPS")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider2")
    public void testFlightSrpDomestic1Url2(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-DPS")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider2")
    public void testFlightSrpDomestic1Endpoint2(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider2() {
        return getFullUrls(apiResults[1], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider2() {
        return getEndpoints(apiResults[1], endpointKeys);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PDG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider3")
    public void testFlightSrpDomestic1Url3(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PDG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider3")
    public void testFlightSrpDomestic1Endpoint3(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider3() {
        return getFullUrls(apiResults[2], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider3() {
        return getEndpoints(apiResults[2], endpointKeys);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider4")
    public void testFlightSrpDomestic1Url4(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider4")
    public void testFlightSrpDomestic1Endpoint4(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider4() {
        return getFullUrls(apiResults[3], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider4() {
        return getEndpoints(apiResults[3], endpointKeys);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PKU")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider5")
    public void testFlightSrpDomestic1Url5(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PKU")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider5")
    public void testFlightSrpDomestic1Endpoint5(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider5() {
        return getFullUrls(apiResults[4], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider5() {
        return getEndpoints(apiResults[4], endpointKeys);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-SUB")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider6")
    public void testFlightSrpDomestic1Url6(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-SUB")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider6")
    public void testFlightSrpDomestic1Endpoint6(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider6() {
        return getFullUrls(apiResults[5], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider6() {
        return getEndpoints(apiResults[5], endpointKeys);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-DTB-CGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider7")
    public void testFlightSrpDomestic1Url7(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-DTB-CGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider7")
    public void testFlightSrpDomestic1Endpoint7(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider7() {
        return getFullUrls(apiResults[6], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider7() {
        return getEndpoints(apiResults[6], endpointKeys);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PNK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider8")
    public void testFlightSrpDomestic1Url8(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic1Api")
    @Vertical(name = "Flight")
    @Module(name = "Flight-SRP-Dom-CGK-PNK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider8")
    public void testFlightSrpDomestic1Endpoint8(VerifyUrls.EndpointItem endpointItem) {
        var result = VerifyUrls.verifyEndpoint(endpointItem, baseUrl);
        verifyEndpoint(result, endpointItem);
    }

    @DataProvider(parallel = true)
    public Object[][] urlDataProvider8() {
        return getFullUrls(apiResults[7], urlKeys);
    }

    @DataProvider(parallel = true)
    public Object[][] endpointDataProvider8() {
        return getEndpoints(apiResults[7], endpointKeys);
    }
}
