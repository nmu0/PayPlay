package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ChoreService {
    // Use String IDs to match the Chore model (which stores id as String)
    private final ConcurrentHashMap<String, Chore> chores = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public String createChoreId() {
        return String.valueOf(counter.incrementAndGet());
    }

    public int complete(String id) {
        Chore c = chores.get(id);
        if (c == null) throw new IllegalArgumentException("Unknown chore id: " + id);

        int coins = 5; // change if you later add a reward field on Chore
        // c.setCompleted(true); // optional
        return coins;
    }

    public List<Chore> getAllChores() {
        return new ArrayList<>(chores.values());
    }

    public Optional<Chore> getChoreById(String id) {
        return Optional.ofNullable(chores.get(id));
    }

    public Chore createChore(Chore chore) {
        // If the incoming chore doesn't have an id, assign a new one.
        if (chore.getId() == null || chore.getId().isBlank()) {
            chore.setId(String.valueOf(counter.incrementAndGet()));
        }
        chores.put(chore.getId(), chore);
        return chore;
    }

    public Optional<Chore> updateChore(String id, Chore chore) {
        if (chores.containsKey(id)) {
            chore.setId(id);
            chores.put(id, chore);
            return Optional.of(chore);
        }
        return Optional.empty();
    }

    public boolean deleteChore(String id) {
        return chores.remove(id) != null;
    }
}