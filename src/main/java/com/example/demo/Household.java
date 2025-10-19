package com.example.demo;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Household {
    private static final AtomicLong IDS = new AtomicLong(0);

    private final String id = String.valueOf(IDS.incrementAndGet());
    private String name;
    private String joinCode;
    private String parentId;

    // children by arbitrary string id (e.g., "kid-001")
    private final List<String> childIds = new ArrayList<>();

    // keep the full chore objects here
    private final Map<String, Chore> chores = new LinkedHashMap<>();

    public Household() {
        this.joinCode = genJoinCode();
    }

    public Household(String name, String parentId) {
        this();
        this.name = (name == null || name.isBlank()) ? "Household" : name.trim();
        this.parentId = parentId;
    }

    // Getters / Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getJoinCode() { return joinCode; }
    public void setJoinCode(String joinCode) { this.joinCode = joinCode; }
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
    public List<String> getChildIds() { return childIds; }
    public Map<String, Chore> getChores() { return chores; }

    public void addChild(String childId) {
        if (childId != null && !childIds.contains(childId)) childIds.add(childId);
    }

    private static String genJoinCode() {
        String ALPHA = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) sb.append(ALPHA.charAt(r.nextInt(ALPHA.length())));
        return sb.toString();
    }
}
