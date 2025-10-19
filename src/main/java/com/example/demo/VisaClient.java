package com.example.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Files;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;
import java.time.Duration;

public class VisaClient {
    private final HttpClient httpClient;
    private final String baseUrl;

    public VisaClient(Path p12Path, String p12Password, String baseUrl) throws Exception {
        this.baseUrl = baseUrl;
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (var is = Files.newInputStream(p12Path)) {
            keyStore.load(is, p12Password.toCharArray());
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, p12Password.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        this.httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }

    public String post(String path, String json, String additionalAuthHeaderValue) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(baseUrl + path))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + additionalAuthHeaderValue)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        return resp.body();
    }
}
