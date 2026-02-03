package com.tiket.core;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class CurlBuilder {

    public static String toCurl(HttpRequest request, HttpResponse<?> response, String requestBody) {
        StringBuilder curl = new StringBuilder("curl");

        // HTTP method
        curl.append(" -X ").append(request.method());

        // URL
        curl.append(" '").append(request.uri()).append("'");

        // Request headers
        for (Map.Entry<String, List<String>> entry : request.headers().map().entrySet()) {
            for (String value : entry.getValue()) {
                curl.append(" \\\n  -H '")
                        .append(entry.getKey())
                        .append(": ")
                        .append(value)
                        .append("'");
            }
        }

        // Request body
        if (requestBody != null && !requestBody.isBlank()) {
            curl.append(" \\\n  --data-raw '")
                    .append(escapeSingleQuotes(requestBody))
                    .append("'");
        }

        // Optional: response info as comment
        if (response != null) {
            curl.append("\n\n# Response Status: ")
                    .append(response.statusCode());
        }

        return curl.toString();
    }

    private static String escapeSingleQuotes(String body) {
        return body.replace("'", "'\"'\"'");
    }
}
