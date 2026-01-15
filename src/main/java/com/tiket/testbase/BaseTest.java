package com.tiket.testbase;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.io.Slack;
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

import static com.tiket.testng.TestListener.timestamp;
import static org.hamcrest.core.Is.is;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    public static final ThreadLocal<ILogger> mainLogger = new ThreadLocal<>();
    protected Environment env;
    protected String baseUrl = "https://tiket.com";
    protected String accessToken;

    @BeforeSuite
    public void beforeSuite(ITestContext context) throws Exception {
        env = Environment.parse(System.getProperty("env"));
        String identity = System.getProperty("username");
        String secret = System.getProperty("secret");

        //baseUrl = BaseUrl.get(env);
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
        //ThreadLocal<ILogger> mainLogger = (ThreadLocal<ILogger>) context.getAttribute("mainLogger");
        long t = System.currentTimeMillis();
        timestamp.set(t);
        ExtentTest test = ExtentTestManager.getTest(method.getName(), t);
        mainLogger.set(new MainLogger(new ExtentLogger(test), new Log4JLogger()));
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

    protected boolean checkApiResult(String baseUrl, ApiResult apiResult, String[] urlKeys, String[] endpointKeys) {
        boolean resultFlag = true;
        JsonNode data = apiResult.data();

        // Verify urls
        for (String key : urlKeys) {
            step("Verifying: " + key);
            var iconUrls = VerifyUrls.findAllUrls(data, key);
            log("Found " + iconUrls.size() + " urls for key: " + key);
            iconUrls.forEach(urlItem -> log(urlItem.toString()));
            var failed = VerifyUrls.verifyFullUrls(iconUrls);
            Assertion.assertThat("Failure count is 0", failed.size(), is(0));
            for (VerifyUrls.UrlVerificationResult failure : failed) {
                resultFlag = false;
                fail(failure.toString());
                Slack.send(failure);
            }
        }

        // Verify endpoints
        for (String key : endpointKeys) {
            step("Verifying: " + key);
            var clickUrls = VerifyUrls.findAllEndpoints(data, key);
            log("Found " + clickUrls.size() + " endpoints for key: " + key);
            clickUrls.forEach(urlItem -> log(urlItem.toString()));
            var failed = VerifyUrls.verifyEndpoints(clickUrls, baseUrl);
            Assertion.assertThat("Failure count is 0", failed.size(), is(0));
            for (VerifyUrls.UrlVerificationResult failure : failed) {
                resultFlag = false;
                fail(failure.toString());
                Slack.send(failure);
            }
        }

        return resultFlag;
    }
}
