package com.tiket;

import com.tiket.core.SlackSummaryFormatter;
import com.tiket.io.FailureDB;
import com.tiket.io.Slack;
import com.tiket.io.sqlite.SQLiteFailureDB;
import com.tiket.model.Platform;
import com.tiket.model.Summary;

import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

    private final static Map<String, Summary> summaryMap = new HashMap<>();
    private final static Map<String, Set<String>> moduleFailures = new HashMap<>();

    public static void main(String[] args) {
        FailureDB db = new SQLiteFailureDB();
        Set<FailureDB.Failure> failures = db.getFailures(Duration.ofDays(7));
        System.out.println("Failures in the last 7 days:");
        failures.forEach(failure -> {
            System.out.println(failure);
            String module = failure.module();
            String platform = failure.platform();
            if(Platform.parse(System.getProperty("platform")) == Platform.parse(platform)) {
                moduleFailures.putIfAbsent(module, new java.util.HashSet<>());
                moduleFailures.get(module).add(failure.link());
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append("Weekly unique failures report:\n");
        moduleFailures.forEach((module, links) -> {
            sb.append(module).append(": ").append(links.size()).append("\n");
            summaryMap.put(module, new Summary(0, links.size(), 0));
        });
        String slackMessage = SlackSummaryFormatter.toSlackMessage(summaryMap);
        System.out.println(slackMessage);
        Slack.send(slackMessage);
    }

    private static void updatePass(String name) {
        if(summaryMap.get(name) == null) {
            summaryMap.put(name, new Summary(1, 0, 0));
        } else {
            summaryMap.computeIfPresent(name, (k, currentSummary) -> new Summary(
                    currentSummary.pass() + 1,
                    currentSummary.fail(),
                    currentSummary.skip()
            ));
        }
    }

    private static void updateFail(String name) {
        if(summaryMap.get(name) == null) {
            summaryMap.put(name, new Summary(0, 1, 0));
        } else {
            summaryMap.computeIfPresent(name, (k, currentSummary) -> new Summary(
                    currentSummary.pass(),
                    currentSummary.fail() + 1,
                    currentSummary.skip()
            ));
        }
    }

    private static void updateSkip(String name) {
        if(summaryMap.get(name) == null) {
            summaryMap.put(name, new Summary(0, 0, 1));
        } else {
            summaryMap.computeIfPresent(name, (k, currentSummary) -> new Summary(
                    currentSummary.pass(),
                    currentSummary.fail(),
                    currentSummary.skip() + 1
            ));
        }
    }
}
