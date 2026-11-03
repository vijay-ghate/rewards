package com.example.rewards.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRewardResponse {
    private final Long transactionId;
    private final LocalDate transactionDate;
    private final BigDecimal purchaseAmount;
    private final int rewardPoints;

    public TransactionRewardResponse(Long transactionId, LocalDate transactionDate,
                                     BigDecimal purchaseAmount, int rewardPoints) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.purchaseAmount = purchaseAmount;
        this.rewardPoints = rewardPoints;
    }
    public Long getTransactionId() { return transactionId; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public BigDecimal getPurchaseAmount() { return purchaseAmount; }
    public int getRewardPoints() { return rewardPoints; }
}
