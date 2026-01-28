package com.tiket.verify;

import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.testbase.Assertion;
import com.tiket.testbase.Ignored;
import com.tiket.testng.TestListener;
import org.testng.SkipException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class VerifyUrls {

    public record UrlItem(String url, String path) {}
    public record EndpointItem(String endpoint, String path) {}

    public record RedirectedInfo(
            String url,
            Integer status,
            String statusText,
            boolean ok,
            String contentType,
            String contentLength,
            String error
    ) {
        @Override
        public String toString() {
            return "RedirectedInfo{" +
                    "url='" + url + '\'' +
                    ", status=" + status +
                    ", statusText='" + statusText + '\'' +
                    ", ok=" + ok +
                    ", contentType='" + contentType + '\'' +
                    ", contentLength='" + contentLength + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        }
    }

    public record UrlVerificationResult(
            Integer status,
            String statusText,
            boolean ok,
            String contentType,
            String contentLength,
            String error,
            RedirectedInfo redirected
    ) {
        @Override
        public String toString() {
            return "UrlVerificationResult{" +
                    "status=" + status +
                    ", statusText='" + statusText + '\'' +
                    ", ok=" + ok +
                    ", contentType='" + contentType + '\'' +
                    ", contentLength='" + contentLength + '\'' +
                    ", error='" + error + '\'' +
                    ", redirected=" + redirected +
                    '}';
        }
    }

    // Find "iconUrl" or "imageUrl" recursively
    public static List<UrlItem> findAllUrls(JsonNode node, String targetKey) {
        List<UrlItem> urls = new ArrayList<>();
        findRecursive(node, targetKey, "", urls);
        return urls;
    }

    private static void findRecursive(JsonNode node, String targetKey, String path, List<UrlItem> urls) {
        if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                findRecursive(node.get(i), targetKey, path + "[" + i + "]", urls);
            }
        } else if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                String currentPath = path.isEmpty() ? key : path + "." + key;

                if (key.equalsIgnoreCase(targetKey) && value.isTextual() && !value.asText().isEmpty()) {
                    urls.add(new UrlItem(value.asText(), currentPath));
                } else {
                    findRecursive(value, targetKey, currentPath, urls);
                }
            });
        }
    }

    // Find "clickUrl" recursively
    public static List<EndpointItem> findAllEndpoints(JsonNode node, String targetKey) {
        List<EndpointItem> endpointItems = new ArrayList<>();
        findEndpointsRecursive(node, targetKey, "", endpointItems);
        return endpointItems;
    }

    private static void findEndpointsRecursive(JsonNode node, String targetKey, String path, List<EndpointItem> urls) {
        if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                findEndpointsRecursive(node.get(i), targetKey, path + "[" + i + "]", urls);
            }
        } else if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                String currentPath = path.isEmpty() ? key : path + "." + key;

                if (key.equalsIgnoreCase(targetKey) && value.isTextual() && !value.asText().isEmpty()) {
                    urls.add(new EndpointItem(value.asText(), currentPath));
                } else {
                    findEndpointsRecursive(value, targetKey, currentPath, urls);
                }
            });
        }
    }


    public static UrlVerificationResult verifyUrl(String urlStr) {
        HttpClient CLIENT = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER) // manual redirect handling
                .build();
        int timeoutMillis = 10000;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .timeout(Duration.ofMillis(timeoutMillis))
                    .GET()
                    .build();

            HttpResponse<Void> response =
                    CLIENT.send(request, HttpResponse.BodyHandlers.discarding());

            UrlVerificationResult baseResult = new UrlVerificationResult(
                    response.statusCode(),
                    httpStatusText(response.statusCode()),
                    isSuccess(response.statusCode()),
                    response.headers().firstValue("content-type").orElse("unknown"),
                    response.headers().firstValue("content-length").orElse("unknown"),
                    null,
                    null
            );

            // Handle 3XX redirect manually
            if (response.statusCode() >= 300 && response.statusCode() < 400) {
                String location = response.headers()
                        .firstValue("location")
                        .orElse(null);

                if (location != null) {
                    String redirectedUrl = resolveRedirect(urlStr, location);
                    TestListener.mainLogger.get().log("URL redirected to: " + redirectedUrl);
                    if(Ignored.getLinks().contains(redirectedUrl)) {
                        Assertion.skip("Redirected URL should be ignored: " + redirectedUrl);
                    }
                    return followRedirect(baseResult, redirectedUrl, timeoutMillis);
                }
            }

            return baseResult;

        } catch (SkipException skipException) {
            throw skipException;
        } catch (HttpTimeoutException e) {
            return timeoutResult();
        } catch (Exception e) {
            return errorResult(e.getMessage());
        }
    }

    public static UrlVerificationResult verifyUrlWithHead(String url) {



        HttpClient NO_REDIRECT_CLIENT =
                HttpClient.newBuilder()
                        .followRedirects(HttpClient.Redirect.NEVER)
                        .build();

        int timeoutMillis = 10000;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofMillis(timeoutMillis))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> response =
                    NO_REDIRECT_CLIENT.send(request, HttpResponse.BodyHandlers.discarding());

            UrlVerificationResult baseResult = new UrlVerificationResult(
                    response.statusCode(),
                    httpStatusText(response.statusCode()),
                    isSuccess(response.statusCode()),
                    response.headers().firstValue("content-type").orElse("unknown"),
                    response.headers().firstValue("content-length").orElse("unknown"),
                    null,
                    null
            );

            // Handle redirect manually
            if (response.statusCode() >= 300 && response.statusCode() < 400) {
                String location = response.headers()
                        .firstValue("location")
                        .orElse(null);

                if (location != null) {
                    String redirectedUrl = resolveRedirect(url, location);
                    TestListener.mainLogger.get().log("URL redirected to: " + redirectedUrl);
                    if(Ignored.getLinks().contains(redirectedUrl)) {
                        Assertion.skip("Redirected URL should be ignored: " + redirectedUrl);
                    }
                    return followRedirectHead(baseResult, redirectedUrl, timeoutMillis);
                }
            }

            return baseResult;

        } catch (SkipException skipException) {
            throw skipException;
        } catch (HttpTimeoutException e) {
            return timeoutResult();
        } catch (Exception e) {
            return errorResult(e.getMessage());
        }
    }

    private static UrlVerificationResult followRedirectHead(
            UrlVerificationResult base,
            String redirectedUrl,
            int timeoutMillis
    ) {
        try {
            HttpClient followClient = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();

            HttpRequest followRequest = HttpRequest.newBuilder()
                    .uri(URI.create(redirectedUrl))
                    .timeout(Duration.ofMillis(timeoutMillis))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> redirectedResponse =
                    followClient.send(followRequest, HttpResponse.BodyHandlers.discarding());

            RedirectedInfo redirectedInfo = new RedirectedInfo(
                    redirectedUrl,
                    redirectedResponse.statusCode(),
                    httpStatusText(redirectedResponse.statusCode()),
                    isSuccess(redirectedResponse.statusCode()),
                    redirectedResponse.headers().firstValue("content-type").orElse("unknown"),
                    redirectedResponse.headers().firstValue("content-length").orElse("unknown"),
                    null
            );

            return new UrlVerificationResult(
                    base.status(),
                    base.statusText(),
                    redirectedInfo.ok(),
                    base.contentType(),
                    base.contentLength(),
                    null,
                    redirectedInfo
            );

        } catch (Exception e) {
            return new UrlVerificationResult(
                    base.status(),
                    base.statusText(),
                    false,
                    base.contentType(),
                    base.contentLength(),
                    null,
                    new RedirectedInfo(
                            redirectedUrl,
                            null,
                            "Error",
                            false,
                            null,
                            null,
                            e.getMessage()
                    )
            );
        }
    }

    private static boolean isSuccess(int status) {
        return status >= 200 && status < 300;
    }

    private static UrlVerificationResult followRedirect(
            UrlVerificationResult base,
            String redirectedUrl,
            int timeoutMillis
    ) {
        try {
            HttpClient followClient = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();

            HttpRequest followRequest = HttpRequest.newBuilder()
                    .uri(URI.create(redirectedUrl))
                    .timeout(Duration.ofMillis(timeoutMillis))
                    .GET()
                    .build();

            HttpResponse<Void> redirectedResponse =
                    followClient.send(followRequest, HttpResponse.BodyHandlers.discarding());

            RedirectedInfo redirectedInfo = new RedirectedInfo(
                    redirectedUrl,
                    redirectedResponse.statusCode(),
                    httpStatusText(redirectedResponse.statusCode()),
                    isSuccess(redirectedResponse.statusCode()),
                    redirectedResponse.headers().firstValue("content-type").orElse("unknown"),
                    redirectedResponse.headers().firstValue("content-length").orElse("unknown"),
                    null
            );

            return new UrlVerificationResult(
                    base.status(),
                    base.statusText(),
                    redirectedInfo.ok(), // overall ok depends on final response
                    base.contentType(),
                    base.contentLength(),
                    null,
                    redirectedInfo
            );

        } catch (Exception e) {
            return new UrlVerificationResult(
                    base.status(),
                    base.statusText(),
                    false,
                    base.contentType(),
                    base.contentLength(),
                    null,
                    new RedirectedInfo(
                            redirectedUrl,
                            null,
                            "Error",
                            false,
                            null,
                            null,
                            e.getMessage()
                    )
            );
        }
    }

    private static String resolveRedirect(String originalUrl, String location) {
        try {
            return URI.create(originalUrl).resolve(location).toString();
        } catch (Exception e) {
            return location;
        }
    }

    private static UrlVerificationResult timeoutResult() {
        return new UrlVerificationResult(
                null,
                "Timeout",
                false,
                null,
                null,
                "Request timeout after 10 seconds",
                null
        );
    }

    private static UrlVerificationResult errorResult(String message) {
        return new UrlVerificationResult(
                null,
                "Error",
                false,
                null,
                null,
                message,
                null
        );
    }

    private static String httpStatusText(int code) {
        return switch (code) {
            case 200 -> "OK";
            case 301 -> "Moved Permanently";
            case 302 -> "Found";
            case 307 -> "Temporary Redirect";
            case 308 -> "Permanent Redirect";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "HTTP " + code;
        };
    }


    public static List<UrlVerificationResult> verifyFullUrls(List<UrlItem> items) {
        List<UrlVerificationResult> failed = new ArrayList<>();
        System.out.println("Verifying " + items.size() + " icon URLs...");
        for (UrlItem item : items) {
            var res = verifyFullUrl(item);
            if(!res.ok) failed.add(res);
        }
        return failed;
    }

    public static UrlVerificationResult verifyFullUrl(UrlItem item) {
        TestListener.mainLogger.get().step("Hitting url: " + shorten(item.url));
        TestListener.mainLogger.get().log("Hitting url: " + item.url);
        if(Ignored.getLinks().contains(item.url)) {
            Assertion.skip("URL should be ignored: " + item.url);
        }
        var res = verifyUrl(item.url());
        if (!res.ok()) {
            TestListener.mainLogger.get().log("Failed: " + item.url() + " (" + (res.error() != null ? res.error() : res.status()) + ")");
            TestListener.mainLogger.get().log("Failed: " + res);
        } else {
            System.out.print(".");
        }
        return res;
    }

    public static List<UrlVerificationResult> verifyEndpoints(List<EndpointItem> items, String baseUrl) {
        List<UrlVerificationResult> failed = new ArrayList<>();
        System.out.println("Verifying " + items.size() + " click URLs...");
        for (EndpointItem item : items) {
            var res = verifyEndpoint(item, baseUrl);
            if (!res.ok()) failed.add(res);
        }
        return failed;
    }

    public static UrlVerificationResult verifyEndpoint(EndpointItem item, String baseUrl) {
        TestListener.mainLogger.get().step("Hitting endpoint: " + shorten(item.endpoint));
        TestListener.mainLogger.get().log("Hitting endpoint: " + item.endpoint);
        String fullUrl = item.endpoint();
        if(Ignored.getLinks().contains(item.endpoint)) {
            Assertion.skip("Endpoint should be ignored: " + item.endpoint);
        }
        if(!fullUrl.startsWith("http")) {
            fullUrl = baseUrl + (fullUrl.startsWith("/") ? "" : "/") + fullUrl;
        }

        TestListener.mainLogger.get().log("Verifying full url for endpoint: " + fullUrl);
        var res = verifyUrlWithHead(fullUrl);
        if (!res.ok()) {
            System.out.println("Failed: " + fullUrl);
            System.out.println("Failed: " + res);
        }else {
            System.out.print(".");
        }
        return res;
    }

    private static String shorten(String s) {
        if(s.length() > 60) {
            return s.substring(0, 59) + "..." + s.substring(s.length() - 59);
        }
        return s;
    }
}