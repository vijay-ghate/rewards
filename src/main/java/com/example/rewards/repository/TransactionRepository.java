package com.example.rewards.repository;

import com.example.rewards.model.PurchaseTransaction;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Repository
public class TransactionRepository {
    private final List<PurchaseTransaction> transactions = new ArrayList<PurchaseTransaction>();
    private final Executor transactionExecutor;

    public TransactionRepository(Executor transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
        add(1001L, 1L, "120.00", "2026-01-15");
        add(1002L, 1L, "75.00", "2026-01-28");
        add(1003L, 1L, "45.00", "2026-02-10");
        add(1004L, 1L, "150.00", "2026-02-20");
        add(1005L, 1L, "200.00", "2026-03-05");
        add(1006L, 1L, "90.00", "2026-03-19");
        add(2001L, 2L, "60.00", "2026-01-12");
        add(2002L, 2L, "110.00", "2026-02-14");
        add(2003L, 2L, "49.99", "2026-03-21");
        add(3001L, 3L, "250.00", "2026-02-01");
    }

    private void add(Long id, Long customerId, String amount, String date) {
        transactions.add(new PurchaseTransaction(id, customerId, new BigDecimal(amount), LocalDate.parse(date)));
    }

    // Simulates a remote/asynchronous transaction service call.
    public CompletableFuture<List<PurchaseTransaction>> findByCustomerAndDateRange(
            final Long customerId, final LocalDate startDate, final LocalDate endDate) {
        return CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(100L); } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Transaction fetch was interrupted", ex);
            }
            List<PurchaseTransaction> result = new ArrayList<PurchaseTransaction>();
            synchronized (transactions) {
                for (PurchaseTransaction transaction : transactions) {
                    if (transaction.getCustomerId().equals(customerId)
                            && !transaction.getTransactionDate().isBefore(startDate)
                            && !transaction.getTransactionDate().isAfter(endDate)) {
                        result.add(transaction);
                    }
                }
            }
            return result;
        }, transactionExecutor);
    }
}
