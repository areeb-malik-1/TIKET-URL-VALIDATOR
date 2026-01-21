package com.tiket.core;

import com.tiket.model.Summary;
import com.tiket.service.ExtentReportUrl;

import java.util.Map;

public final class SlackSummaryFormatter {

    private SlackSummaryFormatter() {
        // utility class
    }

    public static String toSlackMessage(Map<String, Summary> summaryMap) {
        StringBuilder sb = new StringBuilder();

        sb.append("TEST EXECUTION SUMMARY\n\n");

        // Header
        sb.append(String.format(
                "%-12s %6s %6s %6s %6s %7s%n",
                "MODULE", "TOTAL", "PASS", "FAIL", "SKIP", "PASS %"
        ));

        sb.append("-------------------------------------------------\n");

        // Rows
        summaryMap.forEach((module, s) -> {
            int total = s.total();
            int passPct = total == 0 ? 0 : (int) Math.round(s.pass() * 100.0 / total);

            sb.append(String.format(
                    "%-12s %6d %6d %6d %6d %6d%%%n",
                    module,
                    total,
                    s.pass(),
                    s.fail(),
                    s.skip(),
                    passPct
            ));
        });

        sb.append("\nReport: ").append(ExtentReportUrl.get());

        return sb.toString();
    }
}
