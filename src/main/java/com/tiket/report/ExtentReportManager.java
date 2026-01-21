package com.tiket.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.JsonFormatter;
import com.tiket.logging.Log4JLogger;
import org.apache.logging.log4j.LogManager;

public class ExtentReportManager {

    private static final ExtentReports extentReports = new ExtentReports();
    private static volatile boolean isInitialized = false;

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Log4JLogger.class);

    public synchronized static ExtentReports getReports() {
        if (!isInitialized) {
            attachReporter();
            setupSystemInfo();
            isInitialized = true;
        }
        return extentReports;
    }

    private static void attachReporter() {
        String extenReportName = "./extent-reports/extent-report.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(extenReportName);
        reporter.config().setReportName("Url Validation Automation Report");
        reporter.config().thumbnailForBase64(true);
        JsonFormatter jsonReporter = new JsonFormatter("./extent-reports/extent-report.json");
        extentReports.attachReporter(reporter, jsonReporter);
    }

    private static void setupSystemInfo() {
        extentReports.setSystemInfo("Platform", System.getProperty("platform"));
        extentReports.setSystemInfo("Environment", System.getProperty("env"));
    }
}
