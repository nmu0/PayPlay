package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/chores")
public class ChoreController {

    @PostMapping("/generate")
    public List<Chore> generate(@RequestParam String childId,
                                @RequestParam int age,
                                @RequestParam(required = false) List<String> interests) {
        var list = List.of(
                mk(childId, "Tidy room", "Pick up toys & books", 10),
                mk(childId, "Dish helper", "Load dishwasher (supervised)", 15),
                mk(childId, "Money mini-lesson", "Watch 3-min debit card clip", 20)
        );
        InMemoryStore.CHORES_BY_CHILD
                .computeIfAbsent(childId, k -> new ArrayList<>()).addAll(list);
        InMemoryStore.COINS_BY_CHILD.putIfAbsent(childId, 0);
        return list;
    }

    @GetMapping
    public List<Chore> list(@RequestParam String childId) {
        return InMemoryStore.CHORES_BY_CHILD.getOrDefault(childId, List.of());
    }

    @PostMapping("/{id}/approve")
    public Chore approve(@PathVariable String id, @RequestParam String childId) {
        var c = find(childId, id);
        c.setStatus(Chore.Status.APPROVED);
        return c;
    }

    @PostMapping("/{id}/complete")
    public Map<String, Object> complete(@PathVariable String id, @RequestParam String childId) {
        var c = find(childId, id);
        c.setStatus(Chore.Status.DONE);
        int coins = InMemoryStore.COINS_BY_CHILD.getOrDefault(childId, 0) + c.getRewardCoins();
        InMemoryStore.COINS_BY_CHILD.put(childId, coins);
        return Map.of("coins", coins, "chore", c);
    }

    private Chore mk(String child, String t, String d, int r) {
        var ch = new Chore();
        ch.setChildId(child);
        ch.setTitle(t);
        ch.setDescription(d);
        ch.setRewardCoins(r);
        return ch;
    }

    private Chore find(String childId, String id) {
        return InMemoryStore.CHORES_BY_CHILD.getOrDefault(childId, List.of())
                .stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Chore not found"));
    }
}
