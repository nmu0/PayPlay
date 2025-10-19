package com.example.demo;
import java.util.List;

public class Parent {
    private String id;
    private String name;
    private List<String> householdIds; // IDs of households they manage

    // Constructor
    public Parent(String id, String name, String email, List<String> householdIds) {
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
        if (chore.getStatus() == Chore.Status.COMPLETED) {
            chore.setStatus(Chore.Status.VERIFIED);
            child.addCoins(chore.getPointValue());
        }
    }

    // Example method: assign a chore to a child
    public void assignChoreToChild(Chore chore, String childId) {
        chore.setAssignedTo(childId);
        chore.setStatus(Chore.Status.CLAIMED);
    }
}
