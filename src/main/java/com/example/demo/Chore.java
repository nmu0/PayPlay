package com.example.demo;

import java.util.UUID;

public class Chore {
    public enum Status { SUGGESTED, APPROVED, DONE }

    private String id = UUID.randomUUID().toString();
    private String childId;
    private String title;
    private String description;
    private int rewardCoins;
    private Status status = Status.SUGGESTED;

    public String getId() { return id; }
    public String getChildId() { return childId; }
    public void setChildId(String childId) { this.childId = childId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getRewardCoins() { return rewardCoins; }
    public void setRewardCoins(int rewardCoins) { this.rewardCoins = rewardCoins; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
