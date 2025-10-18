package src.main.java.com;

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

    // Getters
    public int getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
}