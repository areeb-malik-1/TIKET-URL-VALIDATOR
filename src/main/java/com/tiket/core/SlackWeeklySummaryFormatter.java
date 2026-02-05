package com.tiket.core;

import com.tiket.model.Summary;
import com.tiket.report.TestCountTracker;
import com.tiket.service.ExtentReportUrl;

import java.util.Map;

import static com.tiket.testbase.BaseTest.NON_WORKING_APIS;

public class SlackWeeklySummaryFormatter {

    private SlackWeeklySummaryFormatter() {
        // private constructor to prevent instantiation
    }

    public static String toSlackMessage(Map<String, Summary> summaryMap) {
        StringBuilder sb = new StringBuilder();

        sb.append("```\n");
        sb.append("Module  No. of unique failing links\n");
        sb.append("-----------------------------------\n");

        summaryMap.forEach((module, s) -> {
            if(s.fail() > 0) {
                sb.append(String.format(
                        "%-18s %4d%n",
                        //shortName(module),
                        module,
                        s.fail()
                ));
            }
        });

        sb.append("```\n");
        return sb.toString();
    }
}
