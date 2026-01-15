package com.tiket.service;

public class ExtentReportUrl {
    public static String get() {
        if(isJenkinsSystem()) {
            String buildUrl = System.getProperty("BUILD_URL");
            return buildUrl + "artifact/extent-reports/extent-report.html";
        }
        return "Executed locally";
    }

    private static boolean isJenkinsSystem() {
        String pwd = System.getProperty("user.dir");
        return pwd != null && pwd.contains("jenkins");
    }
}
