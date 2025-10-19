package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Component
public class VisaClient {
    @Value("${visa.api.key}")
    private String apiKey;

    @Value("${visa.shared.secret}")
    private String sharedSecret;

    @Value("${visa.base.url}")
    private String baseUrl;

    private final RestTemplate http = new RestTemplate();

    private static String hmacSha256Hex(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : raw) sb.append(String.format("%02x", b));
            return sb.toString(); // lowercase hex
        } catch (Exception e) {
            throw new RuntimeException("HMAC error", e);
        }
    }

    private String buildXPayToken(String resourcePath, String queryString, String body) {
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String data = timestamp + resourcePath + queryString + (body == null ? "" : body);
        String hash = hmacSha256Hex(sharedSecret, data);
        return "xv2:" + timestamp + ":" + hash;
    }

    // Example: Merchant Locator POST
    public ResponseEntity<String> merchantLocator(double lat, double lng, int radiusMiles) {
        String resourcePath = "/merchantlocator/v1/locator";
        String query = "apikey=" + apiKey; // only apikey; if you add more params, sort alphabetically
        String url = baseUrl + resourcePath + "?" + query;

        String body = "{"
                + "\"requestData\": {"
                + "\"header\": {\"messageDateTime\": \"2025-10-19T00:00:00.000\",\"requestMessageId\": \"DL-" + System.currentTimeMillis() + "\"},"
                + "\"searchAttrList\": {\"merchantName\": \"TOYS\", \"merchantCountryCode\": \"840\", \"latitude\": " + lat + ", \"longitude\": " + lng + ", \"distance\": " + radiusMiles + ", \"distanceUnit\": \"M\"},"
                + "\"responseAttrList\": [\"GNLOCATOR\"],"
                + "\"searchOptions\": {\"maxRecords\": 10, \"matchIndicators\": true, \"matchScore\": true}"
                + "}"
                + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("x-pay-token", buildXPayToken(resourcePath, query, body));

        HttpEntity<String> req = new HttpEntity<>(body, headers);
        return http.exchange(url, HttpMethod.POST, req, String.class);
    }
}
