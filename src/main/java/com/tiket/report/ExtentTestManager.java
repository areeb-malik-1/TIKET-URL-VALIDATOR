package com.tiket.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentTestManager {

    private static final Logger logger = LogManager.getLogger(ExtentTestManager.class);
    private static final ExtentReports extentReports = ExtentReportManager.getReports();
    private static final Map<String, ExtentTest> nameToTestMap = new ConcurrentHashMap<>();

    public synchronized static ExtentTest getTest(String testName, String testDescription, long timestamp) {
        String name = testName + "_" + timestamp;
        nameToTestMap.computeIfAbsent(name, key -> {
            try {
                ExtentTest test = extentReports.createTest(key, testDescription);
                TestCountTracker.incrementTestsInExtentReport(testName);

                logger.info(
                        "Created ExtentTest for: {} on thread: {}",
                        key,
                        Thread.currentThread().getId()
                );

                return test;
            } catch (Exception e) {
                logger.error("Failed to create ExtentTest for: {}", key, e);
                throw new RuntimeException("Failed to create test: " + key, e);
            }
        });
    }

    public synchronized static ExtentTest getTest(String testName, long timestamp) {
        return getTest(testName, "", timestamp);
    }

    public synchronized static void flushReports() {
        if (extentReports == null) {
            throw new RuntimeException("Null extent reports instance");
        }
        extentReports.flush();
    }
}
