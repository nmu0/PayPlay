package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chores")
public class ChoreController {

    private final ChoreService choreService;
    private final Wallet wallet;

    public ChoreController(ChoreService choreService, Wallet wallet) {
        this.choreService = choreService;
        this.wallet = wallet;
    }

    // A) Generate chores (stubs so UI doesn't 404)
    @PostMapping("/generate")
    public ResponseEntity<List<Chore>> generate(
            @RequestBody(required = false) Map<String, Object> req) {

        // Always add two chores
        Chore c1 = new Chore();
        c1.setId(choreService.createChoreId());
        c1.setTitle("Tidy room");
        c1.setRewardCoins(5);
        choreService.createChore(c1);

        Chore c2 = new Chore();
        c2.setId(choreService.createChoreId());
        c2.setTitle("Help with dishes");
        c2.setRewardCoins(7);
        choreService.createChore(c2);

        // Optional: add LEGO chore if requested
        try {
            @SuppressWarnings("unchecked")
            List<String> interests = (List<String>) (req == null ? null : req.get("interests"));
            if (interests != null && interests.stream().anyMatch(s -> s.toLowerCase().contains("lego"))) {
                Chore c3 = new Chore();
                c3.setId(choreService.createChoreId());
                c3.setTitle("Sort LEGO by color");
                c3.setRewardCoins(8);
                choreService.createChore(c3);
            }
        } catch (Exception ignored) {}

        return ResponseEntity.ok(choreService.getAllChores());
    }

    // B) List chores (backs "Refresh chores")
    @GetMapping("")
    public ResponseEntity<List<Chore>> all() {
        return ResponseEntity.ok(choreService.getAllChores());
    }

    // C) Complete a chore (earn coins)
    @PostMapping("/complete")
    public ResponseEntity<Wallet> complete(@RequestParam String choreId) {
        int earned = choreService.complete(choreId);
        wallet.credit(earned);
        wallet.addTransaction(Transaction.earn(earned));
        return ResponseEntity.ok(wallet);
    }

    // D) Wallet balance (backs "Show balance")
    @GetMapping("/balance")
    public ResponseEntity<Wallet> balance() {
        return ResponseEntity.ok(wallet);
    }

    // E) Mock payout (backs "Payout (mock)")
    @PostMapping("/payout")
    public ResponseEntity<Wallet> payout(@RequestParam int coins) {
        wallet.credit(coins);
        wallet.addTransaction(Transaction.earn(coins));
        return ResponseEntity.ok(wallet);
    }
}
