package com.example.rewards.dto;

import com.example.rewards.model.Customer;
import java.time.LocalDate;
import java.util.List;

public class CustomerRewardResponse {
    private final Customer customer;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int transactionCount;
    private final int totalRewardPoints;
    private final List<MonthlyRewardResponse> monthlyRewards;
    private final List<TransactionRewardResponse> transactions;

    public CustomerRewardResponse(Customer customer, LocalDate startDate, LocalDate endDate,
            int transactionCount, int totalRewardPoints, List<MonthlyRewardResponse> monthlyRewards,
            List<TransactionRewardResponse> transactions) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.transactionCount = transactionCount;
        this.totalRewardPoints = totalRewardPoints;
        this.monthlyRewards = monthlyRewards;
        this.transactions = transactions;
    }
    public Customer getCustomer() { return customer; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getTransactionCount() { return transactionCount; }
    public int getTotalRewardPoints() { return totalRewardPoints; }
    public List<MonthlyRewardResponse> getMonthlyRewards() { return monthlyRewards; }
    public List<TransactionRewardResponse> getTransactions() { return transactions; }
}
