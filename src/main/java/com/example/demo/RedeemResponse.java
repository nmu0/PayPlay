package com.example.demo;

public class RedeemResponse {
    private boolean success;
    private String message;
    private int newBalance;
    private String visaRaw; // quick win for demo; parse later if you have time

    public static RedeemResponse error(String msg) {
        RedeemResponse r = new RedeemResponse();
        r.success = false;
        r.message = msg;
        return r;
    }

    // getters/setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getNewBalance() { return newBalance; }
    public void setNewBalance(int newBalance) { this.newBalance = newBalance; }
    public String getVisaRaw() { return visaRaw; }
    public void setVisaRaw(String visaRaw) { this.visaRaw = visaRaw; }
}
