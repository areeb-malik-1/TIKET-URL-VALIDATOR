package com.tiket.testng;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.core.SlackSummaryFormatter;
import com.tiket.io.Slack;
import com.tiket.logging.ExtentLogger;
import com.tiket.logging.ILogger;
import com.tiket.logging.Log4JLogger;
import com.tiket.logging.MainLogger;
import com.tiket.model.Summary;
import com.tiket.report.ExtentTestManager;
import com.tiket.report.TestCountTracker;
import com.tiket.testbase.BaseTest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static final Map<String, Summary> summaryMap = new ConcurrentHashMap<>();
    public static final ThreadLocal<ILogger> mainLogger = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);

        String msg = "Test start: " + result.getMethod().getMethodName();

        TestCountTracker.incrementTestsStarted(result.getMethod().getMethodName());
        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), TestCountTracker.getTestsStarted());
        
        mainLogger.set(new MainLogger(new ExtentLogger(test), new Log4JLogger()));
        logger.info(msg);
        test.info(msg);
        setAnnotations(result, test);

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), TestCountTracker.getTestsStarted());
        test.log(Status.PASS, "Test success: " + result.getMethod().getMethodName());
        logger.info("Test success: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "PASS");
        updatePass(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), TestCountTracker.getTestsStarted());
        test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        logger.fatal("Test failed: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "FAIL");
        updateFail(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), TestCountTracker.getTestsStarted());
        test.skip("Test skipped: " + result.getMethod().getMethodName());
        logger.error("Test Skipped: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "SKIP");
        updateSkip(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), TestCountTracker.getTestsStarted());
        test.log(Status.FAIL, "Test failed intermittently: " + result.getMethod().getMethodName());
        logger.fatal("Test failed intermittently: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);

        ExtentTest test = ExtentTestManager.getTest(result.getMethod().getMethodName(), TestCountTracker.getTestsStarted());

        test.log(Status.FAIL, "Test failed with timeout: " + result.getMethod().getMethodName());
        logger.fatal("Test failed with timeout: " + result.getMethod().getMethodName());
        updateFail(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);

        logger.debug("Failed cases: ");
        BaseTest.FAILED_RESULTS.forEach(logger::debug);
        // Log test count summary for debugging
        String summary = TestCountTracker.logSummary();
        ExtentTestManager.flushReports();
        Slack.send(SlackSummaryFormatter.toSlackMessage(summaryMap));
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
            String module = getModuleName(result);
            test.assignCategory(module);
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getStackTrace(e));
            test.warning(ExceptionUtils.getStackTrace(e));
        }
    }

    private String getModuleName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Module.class).name();
    }

    private void updatePass(ITestResult result) {
        String vertical = getModuleName(result);
        if(summaryMap.get(vertical) == null) {
            summaryMap.put(vertical, new Summary(1, 0, 0));
        } else {
            summaryMap.computeIfPresent(vertical, (k, currentSummary) -> new Summary(
                    currentSummary.pass() + 1,
                    currentSummary.fail(),
                    currentSummary.skip()
            ));
        }
    }

    private void updateFail(ITestResult result) {
        String vertical = getModuleName(result);
        if(summaryMap.get(vertical) == null) {
            summaryMap.put(vertical, new Summary(1, 0, 0));
        } else {
            summaryMap.computeIfPresent(vertical, (k, currentSummary) -> new Summary(
                    currentSummary.pass(),
                    currentSummary.fail() + 1,
                    currentSummary.skip()
            ));
        }
    }

    public void updateSkip(ITestResult result) {
        String vertical = getModuleName(result);
        if(summaryMap.get(vertical) == null) {
            summaryMap.put(vertical, new Summary(1, 0, 0));
        } else {
            summaryMap.computeIfPresent(vertical, (k, currentSummary) -> new Summary(
                    currentSummary.pass(),
                    currentSummary.fail(),
                    currentSummary.skip() + 1
            ));
        }
    }
}
