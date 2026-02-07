package com.tiket.test.app.flight;

import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Scope;
import com.tiket.annotation.Vertical;
import com.tiket.api.app.flight.FlightSrpDomestic4Api;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import com.tiket.model.VerticalEnum;
import com.tiket.test.Mapping;
import com.tiket.testbase.BaseTest;
import com.tiket.verify.VerifyUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestFlightSrpDomestic4 extends BaseTest {

    private final ApiResult[] apiResults = new ApiResult[8];
    private final String[] urlKeys = Mapping.mapping.get(getClass().getName()).urls();
    private final String[] endpointKeys = Mapping.mapping.get(getClass().getName()).endpoints();

    @BeforeClass
    public void beforeClass() throws Exception {
        String date = "2026-02-09";
        FlightSrpDomestic4Api api1 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        FlightSrpDomestic4Api api2 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        FlightSrpDomestic4Api api3 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        FlightSrpDomestic4Api api4 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        FlightSrpDomestic4Api api5 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        FlightSrpDomestic4Api api6 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        FlightSrpDomestic4Api api7 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        FlightSrpDomestic4Api api8 = new FlightSrpDomestic4Api(accessToken, baseUrl, "KNO", "CJG", date);
        apiResults[0] = api1.hitApi();
        apiResults[1] = api2.hitApi();
        apiResults[2] = api3.hitApi();
        apiResults[3] = api4.hitApi();
        apiResults[4] = api5.hitApi();
        apiResults[5] = api6.hitApi();
        apiResults[6] = api7.hitApi();
        apiResults[7] = api8.hitApi();
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-KNO-CJG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider1")
    public void testFlightSrpDomestic4Url1(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-KNO-CJG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider1")
    public void testFlightSrpDomestic4Endpoint1(VerifyUrls.EndpointItem endpointItem) {
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

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-DPS")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider2")
    public void testFlightSrpDomestic4Url2(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-DPS")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider2")
    public void testFlightSrpDomestic4Endpoint2(VerifyUrls.EndpointItem endpointItem) {
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

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PDG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider3")
    public void testFlightSrpDomestic4Url3(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PDG")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider3")
    public void testFlightSrpDomestic4Endpoint3(VerifyUrls.EndpointItem endpointItem) {
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

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider4")
    public void testFlightSrpDomestic4Url4(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider4")
    public void testFlightSrpDomestic4Endpoint4(VerifyUrls.EndpointItem endpointItem) {
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

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PKU")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider5")
    public void testFlightSrpDomestic4Url5(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PKU")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider5")
    public void testFlightSrpDomestic4Endpoint5(VerifyUrls.EndpointItem endpointItem) {
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

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-SUB")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider6")
    public void testFlightSrpDomestic4Url6(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-SUB")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider6")
    public void testFlightSrpDomestic4Endpoint6(VerifyUrls.EndpointItem endpointItem) {
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

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-DTB-CGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider7")
    public void testFlightSrpDomestic4Url7(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-DTB-CGK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider7")
    public void testFlightSrpDomestic4Endpoint7(VerifyUrls.EndpointItem endpointItem) {
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

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PNK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "urlDataProvider8")
    public void testFlightSrpDomestic4Url8(VerifyUrls.UrlItem urlItem) {
        var result = VerifyUrls.verifyFullUrl(urlItem);
        verifyFullUrl(result, urlItem);
    }

    @Api(name = "FlightSrpDomestic4Api")
    @Vertical(name = VerticalEnum.FLIGHT)
    @Module(name = "Flight-SRP4-Dom-CGK-PNK")
    @Scope(platforms = {Platform.ANDROID, Platform.IOS})
    @Test(dataProvider = "endpointDataProvider8")
    public void testFlightSrpDomestic4Endpoint8(VerifyUrls.EndpointItem endpointItem) {
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
