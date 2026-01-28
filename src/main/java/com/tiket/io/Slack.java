package com.tiket.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Slack {
    private static final Logger logger = LogManager.getLogger(Slack.class);
    private static final String SLACK_WEBHOOK_URL = System.getProperty("SLACK_URL");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void send(Object msg) {
        if (SLACK_WEBHOOK_URL == null || SLACK_WEBHOOK_URL.isEmpty()) {
            logger.debug("Slack webhook URL not configured, skipping notification");
            return;
        }
        
        logger.info("Sending message to Slack");
        try {
            var blocks = List.of(
                    Map.of(
                            "type", "header",
                            "text", Map.of("type", "plain_text", "text", "Urls Test Result")
                    ),
                    Map.of(
                            "type", "section",
                            "fields", List.of(
                                    Map.of("type", "mrkdwn", "text",  msg.toString())
                            )
                    )
            );

            var payload = Map.of("blocks", blocks);
            String jsonBody = mapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SLACK_WEBHOOK_URL))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.discarding());
            logger.info("Successfully sent message to Slack");
        } catch (Exception e) {
            logger.error("Failed to send message to Slack", e);
        }
    }
}