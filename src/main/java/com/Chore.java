package src.main.java.com;
import java.util.UUID;

public class Chore {
    public enum Status {
        AVAILABLE, CLAIMED, COMPLETED, VERIFIED
    }

    private String id;
    private String title;
    private String description;
    private int pointValue;
    private String assignedTo; // child user ID
    private Status status;
    private String createdBy; // parent user ID
    private boolean isPublic;

    // Constructor
    public Chore(String title, String description, int pointValue, String createdBy, boolean isPublic) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.pointValue = pointValue;
        this.createdBy = createdBy;
        this.isPublic = isPublic;
        this.status = Status.AVAILABLE;
        this.assignedTo = null;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPointValue() { return pointValue; }
    public String getAssignedTo() { return assignedTo; }
    public Status getStatus() { return status; }
    public String getCreatedBy() { return createdBy; }
    public boolean isPublic() { return isPublic; }

    // Setters
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        this.status = Status.CLAIMED;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    public void verify() {
        if (this.status == Status.COMPLETED) {
            this.status = Status.VERIFIED;
        }
    }

    public void makePublic() {
        this.isPublic = true;
        this.assignedTo = null;
        this.status = Status.AVAILABLE;
    }

    public void reset() {
        this.assignedTo = null;
        this.status = Status.AVAILABLE;
    }
}