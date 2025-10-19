package com.example.demo;

import java.math.BigDecimal;

/**
 * Chore model redesigned to interoperate with Parent and Child classes.
 * - Uses String id (still accepts Long in a compatibility constructor)
 * - Has a Status enum to represent lifecycle states
 * - Tracks assignedTo (child id) and pointValue for rewards
 * - Keeps reward (BigDecimal) for compatibility with existing controller/tests
 */
public class Chore {
    public enum Status {
        CREATED,
        CLAIMED,
        COMPLETED,
        VERIFIED
    }

    private String id;
    private String title;
    private String description;
    private BigDecimal reward;      // monetary reward (optional)
    private Integer pointValue;     // integer points/coins awarded
    private String assignedTo;      // child id
    private Status status;

    public Chore() {
        this.status = Status.CREATED;
    }

    // Compatibility constructor used by existing tests/controllers
    public Chore(Long id, String title, String description, BigDecimal reward, boolean completed) {
        this.id = id == null ? null : id.toString();
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.pointValue = reward == null ? null : reward.intValue();
        this.status = completed ? Status.COMPLETED : Status.CREATED;
    }

    // Preferred constructor for the new domain model
    public Chore(String id, String title, String description, Integer pointValue, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pointValue = pointValue;
        this.status = status == null ? Status.CREATED : status;
    }

    // Full constructor supporting both reward and pointValue
    public Chore(String id, String title, String description, BigDecimal reward, Integer pointValue, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.pointValue = pointValue;
        this.status = status == null ? Status.CREATED : status;
    }

    // Basic accessors
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getReward() { return reward; }
    public void setReward(BigDecimal reward) { this.reward = reward; }

    public Integer getPointValue() { return pointValue; }
    public void setPointValue(Integer pointValue) { this.pointValue = pointValue; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    // Convenience for compatibility
    public boolean isCompleted() { return this.status == Status.COMPLETED || this.status == Status.VERIFIED; }
    public void setCompleted(boolean completed) { this.status = completed ? Status.COMPLETED : Status.CREATED; }
}

