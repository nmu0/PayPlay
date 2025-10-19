package com.example.demo;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class Wallet {
    private int balance = 0;
    private final List<Transaction> history = new ArrayList<>();

    public int getBalance() { return balance; }

    public void credit(int coins) { balance += coins; }
    public void debit(int coins)  { balance -= coins; }

    public void addTransaction(Transaction t) { history.add(t); }
    public List<Transaction> getHistory() { return history; }

    private int coinBalance;
    private List<Transaction> transactionHistory;

    public Wallet() {
        this.coinBalance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public int getCoinBalance() {
        return coinBalance;
    }

    public void addCoins(int amount, String source) {
        coinBalance += amount;
        transactionHistory.add(new Transaction(amount, source, "EARNED"));
    }

    public boolean spendCoins(int amount, String purpose) {
        if (coinBalance >= amount) {
            coinBalance -= amount;
            transactionHistory.add(new Transaction(-amount, purpose, "SPENT"));
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
