package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ChoreService {
    private final ConcurrentHashMap<Long, Chore> chores = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public List<Chore> getAllChores() {
        return new ArrayList<>(chores.values());
    }

    public Optional<Chore> getChoreById(Long id) {
        return Optional.ofNullable(chores.get(id));
    }

    public Chore createChore(Chore chore) {
        chore.setId(counter.incrementAndGet());
        chores.put(chore.getId(), chore);
        return chore;
    }

    public Optional<Chore> updateChore(Long id, Chore chore) {
        if (chores.containsKey(id)) {
            chore.setId(id);
            chores.put(id, chore);
            return Optional.of(chore);
        }
        return Optional.empty();
    }

    public boolean deleteChore(Long id) {
        return chores.remove(id) != null;
    }
}