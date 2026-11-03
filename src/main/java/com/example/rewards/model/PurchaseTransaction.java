package com.example.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseTransaction {
    private final Long id;
    private final Long customerId;
    private final BigDecimal amount;
    private final LocalDate transactionDate;

    public PurchaseTransaction(Long id, Long customerId, BigDecimal amount, LocalDate transactionDate) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getTransactionDate() { return transactionDate; }
}
