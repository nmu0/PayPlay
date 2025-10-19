package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HouseholdService {
    // joinCode (uppercased) -> Household
    private final Map<String, Household> byCode = new ConcurrentHashMap<>();

    public Household createHousehold(String name, String parentId) {
        Household h = new Household(name, parentId);
        byCode.put(h.getJoinCode().toUpperCase(), h);
        return h;
    }

    public Household joinHousehold(String joinCode, String childId) {
        Household h = mustGet(joinCode);
        h.addChild(childId);
        return h;
    }

    public Chore addChore(String code, Chore c) {
        Household h = mustGet(code);
        if (c.getId() == null || c.getId().isBlank()) c.setId(UUID.randomUUID().toString());
        if (c.getStatus() == null) c.setStatus(Chore.Status.CREATED);
        // if parent entered rewardCoins/pointValue missing, default 5
        if (c.getRewardCoins() == null && c.getPointValue() == null) c.setRewardCoins(5);
        h.getChores().put(c.getId(), c);
        return c;
    }

    public List<Chore> listChores(String code) {
        Household h = mustGet(code);
        return new ArrayList<>(h.getChores().values());
    }

    /** returns coins earned */
    public int completeChore(String code, String choreId) {
        Household h = mustGet(code);
        Chore c = h.getChores().get(choreId);
        if (c == null) throw new IllegalArgumentException("Unknown chore");
        c.setStatus(Chore.Status.COMPLETED);
        Integer coins = c.getRewardCoins() != null ? c.getRewardCoins()
                : (c.getPointValue() != null ? c.getPointValue() : 5);
        return coins;
    }

    private Household mustGet(String code) {
        String k = code == null ? "" : code.trim().toUpperCase();
        Household h = byCode.get(k);
        if (h == null) throw new IllegalArgumentException("Invalid join code");
        return h;
    }
}
