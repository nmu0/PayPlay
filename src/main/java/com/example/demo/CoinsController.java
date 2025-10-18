package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/coins")
public class CoinsController {

    @GetMapping("/balance")
    public Map<String, Integer> balance(@RequestParam String childId) {
        return Map.of("coins", InMemoryStore.COINS_BY_CHILD.getOrDefault(childId, 0));
    }

    @PostMapping("/payout")
    public Map<String, Object> payout(@RequestParam String childId, @RequestParam int coins) {
        int bal = InMemoryStore.COINS_BY_CHILD.getOrDefault(childId, 0);
        if (coins > bal) return Map.of("ok", false, "error", "Insufficient coins");
        InMemoryStore.COINS_BY_CHILD.put(childId, bal - coins);
        return Map.of(
                "ok", true,
                "network", "VISA_TEST",
                "amount_cents", coins * 100,
                "remaining", InMemoryStore.COINS_BY_CHILD.get(childId)
        );
    }
}
