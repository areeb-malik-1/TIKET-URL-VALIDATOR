package com.tiket.testbase;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.logging.ExtentLogger;
import com.tiket.logging.ILogger;
import com.tiket.logging.Log4JLogger;
import com.tiket.logging.MainLogger;
import com.tiket.model.ApiResult;
import com.tiket.model.Environment;
import com.tiket.page.auth.LoginPage;
import com.tiket.page.auth.ServiceTiket;
import com.tiket.page.auth.VerifyPage;
import com.tiket.report.ExtentTestManager;
import com.tiket.service.BaseUrl;
import com.tiket.verify.VerifyUrls;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.tiket.testng.TestListener.THREAD_LOCAL_COUNT;
import static org.hamcrest.core.Is.is;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    public static final ThreadLocal<ILogger> mainLogger = new ThreadLocal<>();
    public static final List<VerifyUrls.UrlVerificationResult> failedResults = new CopyOnWriteArrayList<>();
    protected Environment env;
    protected static String baseUrl;
    protected static String accessToken;
    private static final AtomicLong testCount = new AtomicLong(0);

    @BeforeSuite
    public void beforeSuite(ITestContext context) throws Exception {
        env = Environment.parse(System.getProperty("env"));
        String identity = System.getProperty("username");
        String secret = System.getProperty("secret");

        baseUrl = BaseUrl.get(env);
        logger.debug("Set baseurl: " + baseUrl);

        // 1. Login
        LoginPage loginPage = new LoginPage(identity, secret, env);
        var loginResult = loginPage.hitApi();
        String authCode = loginPage.extractAuthCode(loginResult.response());
        logger.debug("Auth Code: " + authCode);

        // 2. Verify Auth Code
        VerifyPage verifyPage = new VerifyPage(authCode, "prod");
        var verifyResult = verifyPage.hitApi();
        String serviceTicket = verifyResult.serviceTicket();
        logger.debug("Service Ticket: " + serviceTicket);

        // 3. Get Service Token / Access Token
        ServiceTiket serviceTiketPage = new ServiceTiket(serviceTicket, "prod");
        var serviceResult = serviceTiketPage.hitApi();
        accessToken = serviceResult.accessToken();
        logger.debug("Auth Token: " + accessToken);
    }

    @BeforeMethod
    public void beforeMethod(ITestContext context, Method method) {
        synchronized (this) {
            THREAD_LOCAL_COUNT.set(testCount.incrementAndGet());
            ExtentTest test = ExtentTestManager.getTest(method.getName(), testCount.get());
            mainLogger.set(new MainLogger(new ExtentLogger(test), new Log4JLogger()));
        }
        context.setAttribute("mainLogger", mainLogger);
    }

    @AfterMethod
    public void afterMethod(ITestResult result, ITestContext context) {

    }

    @AfterSuite
    public void afterSuite() {

    }

    protected void sleep(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sleep() {
        sleep(10);
    }

    protected void log(String msg) {
        mainLogger.get().log(msg);
    }

    protected void step(String msg) {
        mainLogger.get().step(msg);
    }

    protected void fail(String msg) {
        mainLogger.get().fail(msg);
    }

    protected void verifyFullUrl(VerifyUrls.UrlVerificationResult result, VerifyUrls.UrlItem urlItem) {
        step("Verifying Url");
        log("Verifying: " + urlItem);
        log("Result: " + result);
        if(!result.ok()) failedResults.add(result);
        Assertion.assertThat("Status: " + result.status(), result.ok(), is(true));
    }

    protected Object[][] getFullUrls(ApiResult apiResult, String[] urlKeys) {
        if(apiResult == null || !isSuccess(apiResult)) return new Object[1][];
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

    protected void verifyEndpoint(VerifyUrls.UrlVerificationResult result, VerifyUrls.EndpointItem endpointItem) {
        step("Verifying Endpoint");
        log("Verifying: " + endpointItem);
        log("Result: " + result);
        if(!result.ok()) failedResults.add(result);
        Assertion.assertThat("Status: " + result.status(), result.ok(), is(true));
    }

    protected Object[][] getEndpoints(ApiResult apiResult, String[] endpointKeys) {
        if(apiResult == null || !isSuccess(apiResult)) return new Object[1][];
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

    private boolean isSuccess(ApiResult apiResult) {
        return apiResult.status() >= 200 && apiResult.status() < 300;
    }
}
