package com.tiket.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentTestManager {

    private static final Logger logger = LogManager.getLogger(ExtentTestManager.class);
    private static final ExtentReports extentReports = ExtentReportManager.getReports();
    private static final Map<String, ExtentTest> nameToTestMap = new ConcurrentHashMap<>();

    public synchronized static ExtentTest createTest(String name) {
        try {
            ExtentTest test = extentReports.createTest(name, "");
            Long threadID = Thread.currentThread().getId();
            nameToTestMap.put(name, test);
            TestCountTracker.incrementTestsInExtentReport(name);
            logger.info("Created ExtentTest for: {} on thread: {}", name, threadID);
            return test;
        } catch (Exception e) {
            logger.error("Failed to create ExtentTest for: {}", name, e);
            throw new RuntimeException("Failed to create test: " + name, e);
        }
    }

    public synchronized static ExtentTest getTest(String name) {
        if(!nameToTestMap.containsKey(name)) {
            throw new RuntimeException("No ExtentTest found for name: " + name);
        }
        return nameToTestMap.get(name);
    }

    public synchronized static void flushReports() {
        if (extentReports == null) {
            throw new RuntimeException("Null extent reports instance");
        }
        extentReports.flush();
    }
}
