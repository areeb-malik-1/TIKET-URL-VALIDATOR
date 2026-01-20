package com.tiket.testng;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tiket.annotation.Api;
import com.tiket.annotation.Vertical;
import com.tiket.io.Slack;
import com.tiket.report.ExtentTestManager;
import com.tiket.report.TestCountTracker;
import com.tiket.testbase.BaseTest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);
    public static final ThreadLocal<Long> timestamp = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);

        String msg = "Test start: " + result.getMethod().getMethodName();
        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), timestamp.get());
        logger.info(msg);
        test.info(msg);
        setAnnotations(result, test);

        TestCountTracker.incrementTestsStarted(result.getMethod().getMethodName());
        ExtentTestManager.flushReports();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), timestamp.get());
        test.log(Status.PASS, "Test success: " + result.getMethod().getMethodName());
        logger.info("Test success: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "PASS");
        ExtentTestManager.flushReports();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), timestamp.get());
        test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        logger.fatal("Test failed: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "FAIL");
        ExtentTestManager.flushReports();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), timestamp.get());
        test.log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
        logger.error("Test Skipped: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "SKIP");
        ExtentTestManager.flushReports();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), timestamp.get());
        test.log(Status.FAIL, "Test failed intermittently: " + result.getMethod().getMethodName());
        logger.fatal("Test failed intermittently: " + result.getMethod().getMethodName());

        ExtentTestManager.flushReports();
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), timestamp.get());

        test.log(Status.FAIL, "Test failed with timeout: " + result.getMethod().getMethodName());
        logger.fatal("Test failed with timeout: " + result.getMethod().getMethodName());

        ExtentTestManager.flushReports();
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);

        logger.debug("Failed cases: ");
        BaseTest.failedResults.forEach(logger::debug);
        // Log test count summary for debugging
        String summary = TestCountTracker.logSummary();
        ExtentTestManager.flushReports();
        Slack.send(summary);
    }

    private void setAnnotations(ITestResult result, ExtentTest test) {
        setApiAnnotation(result, test);
        setVerticalAnnotation(result, test);
    }

    private void setApiAnnotation(ITestResult result, ExtentTest test) {
        try {
            String apiName = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Api.class).name();
            test.assignAuthor(apiName);
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getStackTrace(e));
            test.warning(ExceptionUtils.getStackTrace(e));
        }
    }

    private void setVerticalAnnotation(ITestResult result, ExtentTest test) {
        try {
            String module = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Vertical.class).name();
            test.assignCategory(module);
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getStackTrace(e));
            test.warning(ExceptionUtils.getStackTrace(e));
        }
    }
}
