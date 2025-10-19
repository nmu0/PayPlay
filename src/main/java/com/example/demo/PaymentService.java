package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final VisaClient visaClient;
    private final ChoreService choreService; // you already have this
    private final Wallet wallet;             // simple in-memory or your existing class

    public PaymentService(VisaClient visaClient, ChoreService choreService, Wallet wallet) {
        this.visaClient = visaClient;
        this.choreService = choreService;
        this.wallet = wallet;
    }

    /**
     * Redeem coins and return nearby merchants for the kid to spend at.
     * Does NOT actually charge a card—just a demo flow + Visa locator.
     */
    public RedeemResponse redeem(RedeemRequest req) {
        // 1) Verify balance and “parent approval” flag if you have one
        if (wallet.getBalance() < req.getCoins()) {
            return RedeemResponse.error("Not enough coins");
        }

        // 2) Deduct coins and record a transaction
        wallet.debit(req.getCoins());
        Transaction t = new Transaction(req.getCoins(), "Redeemed coins", "REDEEM");
        wallet.addTransaction(t);

        // 3) Call Visa Merchant Locator (sandbox)
        ResponseEntity<String> visaResult = visaClient.merchantLocator(
                req.getLat(), req.getLng(), req.getRadiusMiles()
        );

        // 4) Build response (you can parse JSON later; for time, return raw Visa JSON)
        RedeemResponse resp = new RedeemResponse();
        resp.setNewBalance(wallet.getBalance());
        resp.setVisaRaw(visaResult.getBody());
        resp.setSuccess(true);
        return resp;
    }
}
