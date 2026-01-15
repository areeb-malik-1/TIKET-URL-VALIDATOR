package com.tiket.report;

import com.tiket.service.ExtentReportUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class to track test execution counts and help debug ExtentReports discrepancies
 */
public class TestCountTracker {

    private static final Logger logger = LogManager.getLogger(TestCountTracker.class);

    private static final AtomicInteger testsStarted = new AtomicInteger(0);
    private static final AtomicInteger testsCompleted = new AtomicInteger(0);
    private static final AtomicInteger testsPassed = new AtomicInteger(0);
    private static final AtomicInteger testsFailed = new AtomicInteger(0);
    private static final AtomicInteger testsSkipped = new AtomicInteger(0);
    private static final AtomicInteger testsInExtentReport = new AtomicInteger(0);

    public static void incrementTestsStarted(String testName) {
        int count = testsStarted.incrementAndGet();
        logger.info("Test started: {} (Total started: {})", testName, count);
    }

    public static void incrementTestsCompleted(String testName, String status) {
        int count = testsCompleted.incrementAndGet();
        logger.info("Test completed: {} with status: {} (Total completed: {})", testName, status, count);

        switch (status.toUpperCase()) {
            case "PASS" -> testsPassed.incrementAndGet();
            case "FAIL" -> testsFailed.incrementAndGet();
            case "SKIP" -> testsSkipped.incrementAndGet();
        }
    }

    public static void incrementTestsInExtentReport(String testName) {
        int count = testsInExtentReport.incrementAndGet();
        logger.info("Test added to ExtentReport: {} (Total in report: {})", testName, count);
    }

    public static String logSummary() {
        StringBuffer summary = new StringBuffer();
        summary.append("\n=== TEST EXECUTION SUMMARY ===");
        summary.append("\nTests Started: " + testsStarted.get());
        summary.append("\nTests Completed: " + testsCompleted.get());
        summary.append("\nTests Passed: " + testsPassed.get());
        summary.append("\nTests Failed: " + testsFailed.get());
        summary.append("\nTests Skipped: " + testsSkipped.get());
        summary.append("\nTests in ExtentReport: " + testsInExtentReport.get());
        summary.append("\nExtent report url: " + ExtentReportUrl.get());

        logger.debug(summary);
        int expectedTotal = testsPassed.get() + testsFailed.get() + testsSkipped.get();
        if (testsInExtentReport.get() != expectedTotal) {
            logger.warn("DISCREPANCY DETECTED: ExtentReport shows {} tests but {} tests were executed",
                    testsInExtentReport.get(), expectedTotal);
        } else {
            logger.info("Test counts match - ExtentReport is accurate");
        }
        return summary.toString();
    }

    public static void reset() {
        testsStarted.set(0);
        testsCompleted.set(0);
        testsPassed.set(0);
        testsFailed.set(0);
        testsSkipped.set(0);
        testsInExtentReport.set(0);
        logger.info("Test count tracker reset");
    }
}
