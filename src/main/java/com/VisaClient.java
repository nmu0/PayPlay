package src.main.java.com;

/**
 * VisaClient
 *
 * A minimal HTTP client that performs mTLS-authenticated requests to Visa (or other)
 * sandbox endpoints using a PKCS#12 client keystore. This class demonstrates how
 * to load a .p12 keystore, initialize an SSLContext with the client certificate
 * material, and make JSON POST requests over TLS using Java 11's HttpClient.
 *
 * Usage notes:
 * - Provide the path to your PKCS#12 keystore (clientkeystore.p12) and the
 *   keystore password when constructing the client.
 * - The client uses the JVM default truststore for server certificate
 *   verification. If you're using custom CA certificates (for sandbox), load
 *   a custom truststore instead of the default.
 * - Replace or extend the example `post` method headers with Visa-specific
 *   headers (API keys, signatures, or other auth headers) per Visa Developer
 *   documentation for the specific API you're calling (e.g. Visa Direct).
 *
 * Security notes:
 * - Never check the .p12 file or its password into version control.
 * - Store the keystore path and password in secure configuration (environment
 *   variables, secret manager, or encrypted vault) and inject at runtime.
 * - Limit permissions on the file system for the keystore file so only the
 *   application user can read it.
 *
 * Example construction:
 *   Path p12 = Paths.get(System.getenv("VISA_KEYSTORE_PATH"));
 *   String password = System.getenv("VISA_KEYSTORE_PASSWORD");
 *   VisaClient client = new VisaClient(p12, password, "https://sandbox.api.visa.com");
 */

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

    /**
     * Create a VisaClient configured for mutual TLS (mTLS) using a PKCS#12 keystore.
     *
     * <p>Behavior:
     * <ul>
     *   <li>Loads the PKCS#12 keystore from {@code p12Path} using {@code p12Password}.</li>
     *   <li>Initializes a KeyManagerFactory from the keystore to present the client
     *       certificate during the TLS handshake.</li>
     *   <li>Initializes a TrustManagerFactory using the JVM default truststore. If the
     *       Visa sandbox provides custom CA certificates, consider loading a custom
     *       truststore instead.</li>
     *   <li>Creates an {@code SSLContext} (TLSv1.2) and builds a Java 11 {@link HttpClient}
     *       that uses the SSL context for HTTPS connections.</li>
     * </ul>
     *
     * <p>Security notes: do not commit the .p12 file or its password to source control. Store
     * the keystore and password in environment variables or a secrets manager and restrict
     * file permissions so only the application user can read the keystore.
     *
     * @param p12Path path to the PKCS#12 keystore containing the client certificate
     * @param p12Password password for the PKCS#12 keystore
     * @param baseUrl base URL for the Visa sandbox or API endpoints (e.g. https://sandbox.api.visa.com)
     * @throws Exception if the keystore cannot be loaded or the SSL context cannot be initialized
     */
    public VisaClient(Path p12Path, String p12Password, String baseUrl) throws Exception {
        this.baseUrl = baseUrl;
        // Load PKCS12 keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (var is = Files.newInputStream(p12Path)) {
            keyStore.load(is, p12Password.toCharArray());
        }

        // Key manager (client cert)
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, p12Password.toCharArray());

        // Trust manager (use default JVM truststore or supply Visa CA certs)
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null); // use default truststore

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
                // add required Visa headers: placeholder below
                .header("Authorization", "Bearer " + additionalAuthHeaderValue) // if required
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        return resp.body();
    }
}
