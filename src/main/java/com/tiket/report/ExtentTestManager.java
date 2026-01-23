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
    private static final ThreadLocal<Map<String, ExtentTest>> nameToTestMap = ThreadLocal.withInitial(HashMap::new);

    public synchronized static ExtentTest getTest(String testName, String testDescription, long timestamp) {
        String name = testName + "_" + timestamp;
        ExtentTest test = extentReports.createTest(name, testDescription);
        if (!nameToTestMap.get().containsKey(name)) {
            try {
                Long threadID = Thread.currentThread().getId();
                nameToTestMap.get().put(name, test);
                TestCountTracker.incrementTestsInExtentReport(testName);
                logger.info("Created ExtentTest for: {} on thread: {}", name, threadID);
            } catch (Exception e) {
                logger.error("Failed to create ExtentTest for: {}", name, e);
                throw new RuntimeException("Failed to create test: " + name, e);
            }
        }
        return nameToTestMap.get().get(name);
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
