package com.tiket.core;

import com.tiket.model.Summary;

import java.util.Map;

public final class SlackSummaryFormatter {

    private SlackSummaryFormatter() {
        // utility class
    }

    public static String toSlackTable(Map<String, Summary> summaryMap) {
        if (summaryMap == null || summaryMap.isEmpty()) {
            return "No test execution data available.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("```\n");
        sb.append(String.format(
                "%-15s %7s %7s %7s %7s %8s%n",
                "Vertical", "Total", "Pass", "Fail", "Skip", "Pass%"
        ));
        sb.append("-".repeat(60)).append("\n");

        summaryMap.forEach((vertical, summary) -> {
            int total = summary.total();
            double passPercent = total == 0
                    ? 0.0
                    : (summary.pass() * 100.0) / total;

            sb.append(String.format(
                    "%-15s %7d %7d %7d %7d %7.2f%%%n",
                    vertical,
                    total,
                    summary.pass(),
                    summary.fail(),
                    summary.skip(),
                    passPercent
            ));
        });

        sb.append("```");
        return sb.toString();
    }
}
