package com.tiket.testng;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.tiket.annotation.Api;
import com.tiket.annotation.Module;
import com.tiket.annotation.Vertical;
import com.tiket.core.SlackDailySummaryFormatter;
import com.tiket.io.db.FailureDB;
import com.tiket.io.Slack;
import com.tiket.io.db.influx.InfluxFailureDB;
import com.tiket.io.db.sqlite.SQLiteFailureDB;
import com.tiket.logging.ExtentLogger;
import com.tiket.logging.ILogger;
import com.tiket.logging.Log4JLogger;
import com.tiket.logging.MainLogger;
import com.tiket.model.FailureSummary;
import com.tiket.model.Summary;
import com.tiket.report.ExtentReportManager;
import com.tiket.report.ExtentTestManager;
import com.tiket.report.TestCountTracker;
import com.tiket.testbase.BaseTest;
import com.tiket.testbase.Config;
import com.tiket.verify.VerifyUrls;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.tiket.testbase.Config.PLATFORM;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static final Map<String, Summary> summaryMap = new ConcurrentHashMap<>();
    public static final ThreadLocal<ILogger> mainLogger = new ThreadLocal<>();
    private static final ThreadLocal<String> testNameThreadLocal = new ThreadLocal<>();
    private static final SQLiteFailureDB failureDB = new SQLiteFailureDB();

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);

        TestCountTracker.incrementTestsStarted(result.getMethod().getMethodName());

        synchronized (this) {
            String testName = result.getMethod().getMethodName() + "_" + System.currentTimeMillis() + "_" + TestCountTracker.getTestsStarted();
            String msg = "Test start: " + testName;
            testNameThreadLocal.set(testName);
            ExtentTest test = ExtentTestManager.createTest(testName);
            mainLogger.set(new MainLogger(new ExtentLogger(test), new Log4JLogger()));
            logger.info(msg);
            test.info(msg);
            setAnnotations(result, test);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);

        ExtentTest test = ExtentTestManager.getTest(testNameThreadLocal.get());
        test.log(Status.PASS, "Test success: " + result.getMethod().getMethodName());
        logger.info("Test success: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "PASS");
        updatePass(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);

        ExtentTest test = ExtentTestManager.getTest(testNameThreadLocal.get());
        test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        logger.fatal("Test failed: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "FAIL");
        updateFail(result);

        Object parameter = result.getParameters()[0];
        if(parameter instanceof VerifyUrls.UrlItem urlItem) {
            failureDB.insertFailure(new FailureDB.DBFailure(new FailureDB.Failure(urlItem.url(), getApiName(result), getModuleName(result), getVerticalName(result), PLATFORM.name()), System.currentTimeMillis()));
        } else if (parameter instanceof VerifyUrls.EndpointItem endpointItem) {
            failureDB.insertFailure(new FailureDB.DBFailure(new FailureDB.Failure(endpointItem.endpoint(), getApiName(result), getModuleName(result), getVerticalName(result), PLATFORM.name()), System.currentTimeMillis()));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);

        ExtentTest test = ExtentTestManager.getTest(testNameThreadLocal.get());
        test.skip("Test skipped: " + result.getMethod().getMethodName());
        logger.error("Test Skipped: " + result.getMethod().getMethodName());

        TestCountTracker.incrementTestsCompleted(result.getMethod().getMethodName(), "SKIP");
        updateSkip(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);

        ExtentTest test = ExtentTestManager.getTest(testNameThreadLocal.get());
        test.log(Status.FAIL, "Test failed intermittently: " + result.getMethod().getMethodName());
        logger.fatal("Test failed intermittently: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);

        ExtentTest test = ExtentTestManager.getTest(testNameThreadLocal.get());

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
        Slack.send(SlackDailySummaryFormatter.toSlackMessage(summaryMap));

        ExtentReportManager.getReports().getReport().getTestList().removeIf(test -> test.getStatus() == Status.PASS);
        ExtentTestManager.flushReports();

        // Write failure summaries to InfluxDB
        List<FailureSummary> summaries = buildFailureSummaries(summaryMap);
        writeSummaries(summaries);
    }

    private void setAnnotations(ITestResult result, ExtentTest test) {
        setApiAnnotation(result, test);
        setVerticalAnnotation(result, test);
    }

    private void setApiAnnotation(ITestResult result, ExtentTest test) {
        try {
            String apiName = getApiName(result);
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

    private String getApiName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Api.class).name();
    }

    private String getModuleName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Module.class).name();
    }

    private String getVerticalName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Vertical.class).name();
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

    private static List<FailureSummary> buildFailureSummaries(
            Map<String, Summary> summaryMap
    ) {
        long timestamp = System.currentTimeMillis();
        List<FailureSummary> result = new ArrayList<>();

        summaryMap.forEach((vertical, summary) -> {
            result.add(new FailureSummary(
                    Config.ENV.name(),
                    PLATFORM.name(),
                    vertical,
                    summary.total(),
                    summary.pass(),
                    summary.fail(),
                    summary.skip(),
                    timestamp
            ));
        });

        return result;
    }

    private static void writeSummaries(
            List<FailureSummary> summaries
    ) {
        WriteApi writeApi = InfluxFailureDB.getInstance().getWriteApi();
        Objects.requireNonNull(summaries, "summaries");

        for (FailureSummary s : summaries) {
            Point point = Point
                    .measurement("failure_summary")
                    .addTag("env", s.env())
                    .addTag("platform", s.platform())
                    .addTag("vertical", s.vertical())
                    .addField("total", s.total())
                    .addField("passed", s.passed())
                    .addField("failed", s.failed())
                    .addField("skipped", s.skipped())
                    .time(Instant.ofEpochMilli(s.timestamp()), WritePrecision.MS);

            writeApi.writePoint(point);
        }
    }
}
