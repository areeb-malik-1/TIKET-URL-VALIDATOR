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

        sb.append("```\n");
        sb.append(String.format(
                "%-12s %5s %5s %5s %5s %6s%n",
                "Vertical", "Tot", "Pass", "Fail", "Skip", "Pass%"
        ));
        sb.append("---------------------------------------------\n");

        summaryMap.forEach((vertical, s) -> {
            int total = s.total();
            double passPct = total == 0 ? 0 : (s.pass() * 100.0 / total);

            sb.append(String.format(
                    "%-12s %5d %5d %5d %5d %5.1f%%%n",
                    trim(vertical, 12),
                    total,
                    s.pass(),
                    s.fail(),
                    s.skip(),
                    passPct
            ));
        });

        sb.append("```");
        sb.append("\n📊 Extent Report: ").append(ExtentReportUrl.get());

        return sb.toString();
    }

    private static String trim(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max - 1) + "…";
    }
}
