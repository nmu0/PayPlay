package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;

/**
 * REST API endpoints for managing chores and rewards.
 */
@RestController
@RequestMapping("/api/chores")
public class ChoreController {
    
    private final List<Chore> chores = new ArrayList<>();

    @GetMapping
    public List<Chore> getAllChores() {
        return chores;
    }

    @PostMapping
    public Chore createChore(@RequestBody Chore chore) {
        chores.add(chore);
        return chore;
    }

    @PostMapping("/{choreId}/complete")
    public void completeChore(@PathVariable String choreId, @RequestParam String childId) {
        // TODO: Implement chore completion logic
        chores.stream()
            .filter(c -> c.getId().equals(choreId))
            .findFirst()
            .ifPresent(chore -> {
                if (childId.equals(chore.getChildId())) {
                    chore.setStatus(Chore.Status.DONE);
                }
            });
    }
}