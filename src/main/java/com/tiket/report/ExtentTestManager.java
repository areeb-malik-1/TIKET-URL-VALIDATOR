package com.tiket.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {

    private static final Logger logger = LogManager.getLogger(ExtentTestManager.class);
    private static final ExtentReports extentReports = ExtentReportManager.getReports();
    private static final Map<Long, String> threadToExtentTestMap = new HashMap<>();
    private static final Map<String, ExtentTest> nameToTestMap = new HashMap<>();

    public synchronized static ExtentTest getTest(String testName, String testDescription, long timestamp) {
        String name = testName + "_" + timestamp;
        if (!nameToTestMap.containsKey(name)) {
            try {
                Long threadID = Thread.currentThread().getId();
                ExtentTest test = extentReports.createTest(name, testDescription);
                nameToTestMap.put(name, test);
                threadToExtentTestMap.put(threadID, name);
                TestCountTracker.incrementTestsInExtentReport(testName);
                logger.info("Created ExtentTest for: {} on thread: {}", name, threadID);
            } catch (Exception e) {
                logger.error("Failed to create ExtentTest for: {}", name, e);
                throw new RuntimeException("Failed to create test: " + name, e);
            }
        }
        return nameToTestMap.get(name);
    }

    public synchronized static ExtentTest getTest(String testName, long timestamp) {
        return getTest(testName, "", timestamp);
    }

    public synchronized static ExtentTest getTest(long timestamp) {
        Long threadID = Thread.currentThread().getId();
        if (threadToExtentTestMap.containsKey(threadID)) {
            String testName = threadToExtentTestMap.get(threadID);
            ExtentTest test = nameToTestMap.get(testName);
            if (test != null) {
                return test;
            }
            logger.warn("Test name {} found for thread {} but ExtentTest is null", testName, threadID);
        }

        logger.error("Test not found for thread {} with timestamp {}", threadID, timestamp);
        throw new RuntimeException("Test not found for thread: " + threadID + " timestamp: " + timestamp);
    }

    public synchronized static void cleanupThreadData(long timestamp) {
        // Only clean up thread mappings, not the actual tests from reports
        Long threadID = Thread.currentThread().getId();
        String testName = threadToExtentTestMap.get(threadID);
        if (testName != null && testName.endsWith("_" + timestamp)) {
            threadToExtentTestMap.remove(threadID);
        }
    }

    public synchronized static void flushReports() {
        if (extentReports == null) {
            throw new RuntimeException("Null extent reports instance");
        }
        extentReports.flush();
    }
}
