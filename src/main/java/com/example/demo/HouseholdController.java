package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class HouseholdController {

    private final HouseholdService households;
    private final Wallet wallet;

    public HouseholdController(HouseholdService households, Wallet wallet) {
        this.households = households;
        this.wallet = wallet;
    }

    // Create household (UI: /api/households/create or /api/household/create)
    @PostMapping({"/households/create","/household/create"})
    public Map<String, Object> create(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String parentId) {
        Household h = households.createHousehold(name, parentId);
        return Map.of("id", h.getId(), "name", h.getName(), "joinCode", h.getJoinCode());
    }

    // Join household
    @PostMapping({"/households/join","/household/join"})
    public Map<String, Object> join(@RequestParam String joinCode,
                                    @RequestParam String childId) {
        Household h = households.joinHousehold(joinCode, childId);
        return Map.of("id", h.getId(), "name", h.getName(),
                "joinCode", h.getJoinCode(), "children", h.getChildIds());
    }

    // Parent adds a chore (JSON body)
    @PostMapping({"/households/{code}/chores","/household/{code}/chores"})
    public ResponseEntity<Chore> add(@PathVariable String code, @RequestBody Chore c) {
        return ResponseEntity.ok(households.addChore(code, c));
    }

    // List chores
    @GetMapping({"/households/{code}/chores","/household/{code}/chores"})
    public List<Chore> list(@PathVariable String code) {
        return households.listChores(code);
    }

    // Complete chore -> credit wallet
    @PostMapping({"/households/{code}/chores/{choreId}/complete","/household/{code}/chores/{choreId}/complete"})
    public Map<String,Object> complete(@PathVariable String code, @PathVariable String choreId) {
        int earned = households.completeChore(code, choreId);
        wallet.credit(earned);
        wallet.addTransaction(Transaction.earn(earned));
        return Map.of("earned", earned, "balance", wallet.getBalance());
    }
}
