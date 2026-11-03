package com.example.rewards.dto;

import java.time.YearMonth;

public class MonthlyRewardResponse {
    private final YearMonth month;
    private final int transactionCount;
    private final int rewardPoints;

    public MonthlyRewardResponse(YearMonth month, int transactionCount, int rewardPoints) {
        this.month = month;
        this.transactionCount = transactionCount;
        this.rewardPoints = rewardPoints;
    }
    public YearMonth getMonth() { return month; }
    public int getTransactionCount() { return transactionCount; }
    public int getRewardPoints() { return rewardPoints; }
}
