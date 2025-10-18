package src.main.java.com;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
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