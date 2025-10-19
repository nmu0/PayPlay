package com.example.demo;

import java.time.LocalDateTime;

public class Transaction {
    private int amount;
    private String description;
    private String type; // EARNED, SPENT, REDEEMED
    private LocalDateTime timestamp;

    public Transaction(int amount, String description, String type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public static Transaction earn(int amount) {
        return new Transaction(amount, "Earned chore coins", "EARN");
    }

    public static Transaction redeem(int amount) {
        return new Transaction(amount, "Redeemed coins", "REDEEM");
    }


    // Getters
    public int getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
