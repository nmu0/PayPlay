package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Simple in-memory household API:
 *  - POST /api/household  { "name": "..." }  -> 201 { joinCode, household }
 *  - GET  /api/login?joinCode=XX-12345        -> 200 { household } or 404
 */
@RestController
@RequestMapping("/api")
public class HouseholdController {

    private final ConcurrentHashMap<String, Household> store = new ConcurrentHashMap<>();

    @PostMapping("/household")
    public ResponseEntity<?> createHousehold(@RequestBody CreateHouseholdRequest req) {
        if (req == null || req.name == null || req.name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "name is required"));
        }

        // generate unique join code
        String joinCode;
        do {
            joinCode = generateJoinCode();
        } while (store.containsKey(joinCode));

        Household h = new Household(UUID.randomUUID().toString(), req.name.trim(), joinCode);
        store.put(joinCode, h);

        return ResponseEntity.status(201).body(Map.of(
            "joinCode", joinCode,
            "household", h
        ));
    }

    // used by frontend: /api/login?joinCode=XX-12345
    @GetMapping("/login")
    public ResponseEntity<?> findByJoinCode(@RequestParam String joinCode) {
        if (joinCode == null || joinCode.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "joinCode required"));
        }
        Household h = store.get(joinCode);
        if (h == null) return ResponseEntity.status(404).body(Map.of("error", "not found"));
        return ResponseEntity.ok(Map.of("household", h));
    }

    // --- simple DTOs ---
    public static class CreateHouseholdRequest { public String name; }

    public static class Household {
        public final String id;
        public final String name;
        public final String joinCode;
        public Household(String id, String name, String joinCode) {
            this.id = id; this.name = name; this.joinCode = joinCode;
        }
    }

    // join code helpers
    private static String generateJoinCode() {
        return randomUpper(2) + "-" + String.format("%05d", ThreadLocalRandom.current().nextInt(0, 100000));
    }
    private static String randomUpper(int len) {
        var r = ThreadLocalRandom.current();
        var sb = new StringBuilder(len);
        for (int i=0;i<len;i++) sb.append((char)('A' + r.nextInt(26)));
        return sb.toString();
    }
}