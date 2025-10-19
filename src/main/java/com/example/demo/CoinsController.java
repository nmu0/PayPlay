package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/coins")
public class CoinsController {
    private final Wallet wallet;
    public CoinsController(Wallet wallet) { this.wallet = wallet; }

    @GetMapping("/balance")
    public Map<String,Object> balance() { return Map.of("coins", wallet.getBalance()); }

    @PostMapping("/payout")
    public ResponseEntity<Map<String,Object>> payout(@RequestParam int coins) {
        if (coins <= 0) return ResponseEntity.badRequest().body(Map.of("error","coins must be > 0"));
        if (wallet.getBalance() < coins) return ResponseEntity.badRequest().body(Map.of("error","not enough coins"));
        wallet.debit(coins);
        wallet.addTransaction(Transaction.redeem(coins));
        return ResponseEntity.ok(Map.of("ok",true,"coins",coins,"balance",wallet.getBalance()));
    }
}
