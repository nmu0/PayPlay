package src.main.java.com;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Household {
    private String id;
    private String name;
    private String joinCode;
    private String parentId;
    private Integer budget; // Optional: in cents or dollars
    private List<String> childIds;
    private List<String> choreIds;

    // Constructor
    public Household(String name, String parentId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.parentId = parentId;
        this.joinCode = generateJoinCode();
        this.budget = null;
        this.childIds = new ArrayList<>();
        this.choreIds = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getJoinCode() { return joinCode; }
    public String getParentId() { return parentId; }
    public Integer getBudget() { return budget; }
    public List<String> getChildIds() { return childIds; }
    public List<String> getChoreIds() { return choreIds; }

    // Setters
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    // Membership management
    public void addChild(String childId) {
        if (!childIds.contains(childId)) {
            childIds.add(childId);
        }
    }

    public void addChore(String choreId) {
        if (!choreIds.contains(choreId)) {
            choreIds.add(choreId);
        }
    }

    // Utility
    private String generateJoinCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
