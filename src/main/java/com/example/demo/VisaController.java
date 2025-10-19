package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisaController {
    private final VisaClient visaClient;

    public VisaController(VisaClient visaClient) {
        this.visaClient = visaClient;
    }

    // Quick sanity test endpoint
    @GetMapping("/visa/nearby-toys")
    public ResponseEntity<String> nearby() {
        // Seattle-ish coords; tweak if you want
        return visaClient.merchantLocator(47.61, -122.33, 10);
    }
}
