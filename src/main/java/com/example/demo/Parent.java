package com.example.demo;
import java.util.List;

public class Parent {
    private String id;
    private String name;
    private List<String> householdIds; // IDs of households they manage

    // Constructor
    public Parent(String id, String name, List<String> householdIds) {
        this.id = id;
        this.name = name;
        this.householdIds = householdIds;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public List<String> getHouseholdIds() { return householdIds; }

    public void addHousehold(String householdId) {
        householdIds.add(householdId);
    }

    // Example method: verify a chore
    public void verifyChore(Chore chore, Child child) {
        if (chore.getStatus() == Chore.Status.DONE) {
            chore.setStatus(Chore.Status.SUGGESTED); // adjust as appropriate
            child.addCoins(chore.getRewardCoins());
        }
    }

    // Example method: assign a chore to a child
    public void assignChoreToChild(Chore chore, String childId) {
        chore.setChildId(childId);
        chore.setStatus(Chore.Status.SUGGESTED);
    }
}
