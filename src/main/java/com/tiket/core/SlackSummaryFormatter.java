package com.tiket.core;

import com.tiket.model.Summary;
import com.tiket.report.TestCountTracker;
import com.tiket.service.ExtentReportUrl;

import java.util.Map;

import static com.tiket.testbase.BaseTest.NON_WORKING_APIS;

public final class SlackSummaryFormatter {

    private SlackSummaryFormatter() {
        // utility class
    }

    public static String toSlackMessage(Map<String, Summary> summaryMap) {
        StringBuilder sb = new StringBuilder();

        sb.append("```\n");
        sb.append("Mod  Tot  P  F  S  P%\n");
        sb.append("--------------------\n");

        summaryMap.forEach((module, s) -> {
            int total = s.total();
            int passPct = total == 0 ? 0 : (int) Math.round(s.pass() * 100.0 / total);

            sb.append(String.format(
                    "%-12s %4d %4d %4d %4d %3d%n",
                    //shortName(module),
                    module,
                    total,
                    s.pass(),
                    s.fail(),
                    s.skip(),
                    passPct
            ));
        });

        sb.append("```\n");
        sb.append("Total links verified: ").append(TestCountTracker.getTestsCompleted()).append("\n");
        if(!NON_WORKING_APIS.isEmpty()) {
            sb.append("Non Working APIs: ").append("\n");
            for(String api : NON_WORKING_APIS) {
                sb.append(api).append("\n");
            }
        }
        sb.append("📊 Extent: ").append(ExtentReportUrl.get());

        return sb.toString();
    }

    private static String shortName(String name) {
        return name.length() <= 3
                ? name.toUpperCase()
                : name.substring(0, 3).toUpperCase();
    }
}
