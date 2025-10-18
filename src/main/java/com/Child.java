package src.main.java.com;

import java.util.ArrayList;
import java.util.List;

public class Child {
    private String id;
    private String name;
    private String householdId;
    private int coinBalance;
    private List<String> assignedChoreIds;
    private List<Transaction> transactionHistory;
    private Wallet wallet;

    // Constructor
    public Child(String id, String name, String householdId) {
        this.id = id;
        this.name = name;
        this.householdId = householdId;
        this.coinBalance = 0;
        this.wallet = new Wallet();
        this.assignedChoreIds = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getHouseholdId() { return householdId; }
    public int getCoinBalance() { return coinBalance; }
    public List<String> getAssignedChoreIds() { return assignedChoreIds; }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }
    public Wallet getWallet() { return wallet; }

    // Coin management with logging
    public void addCoins(int amount) {
        this.coinBalance += amount;
        transactionHistory.add(new Transaction(amount, "Chore reward", "EARNED"));
    }

    public boolean spendCoins(int amount) {
        if (coinBalance >= amount) {
            coinBalance -= amount;
            transactionHistory.add(new Transaction(-amount, "Redemption", "SPENT"));
            return true;
        }
        return false;
    }

    // Chore management
    public void assignChore(String choreId) {
        assignedChoreIds.add(choreId);
    }

    public void completeChore(Chore chore) {
        if (assignedChoreIds.contains(chore.getId()) && chore.getStatus() == Chore.Status.CLAIMED) {
            chore.setStatus(Chore.Status.COMPLETED);
        }
    }
}