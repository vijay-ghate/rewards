package com.example.rewards.service;

import com.example.rewards.dto.*;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.model.*;
import com.example.rewards.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class RewardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardService.class);
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final RewardCalculator rewardCalculator;

    public RewardService(CustomerRepository customerRepository,
                         TransactionRepository transactionRepository,
                         RewardCalculator rewardCalculator) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.rewardCalculator = rewardCalculator;
    }

    public CompletableFuture<CustomerRewardResponse> getRewards(
            Long customerId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidDateRangeException("startDate and endDate are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("startDate must be on or before endDate");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        LOGGER.info("Calculating rewards for customer {} from {} to {}", customerId, startDate, endDate);

        return transactionRepository.findByCustomerAndDateRange(customerId, startDate, endDate)
                .thenApply(transactions -> buildResponse(customer, startDate, endDate, transactions));
    }

    private CustomerRewardResponse buildResponse(Customer customer, LocalDate startDate,
            LocalDate endDate, List<PurchaseTransaction> transactions) {
        List<TransactionRewardResponse> details = transactions.stream()
                .sorted(Comparator.comparing(PurchaseTransaction::getTransactionDate))
                .map(transaction -> new TransactionRewardResponse(transaction.getId(),
                        transaction.getTransactionDate(), transaction.getAmount(),
                        rewardCalculator.calculate(transaction.getAmount())))
                .collect(Collectors.toList());

        Map<YearMonth, List<TransactionRewardResponse>> byMonth = details.stream()
                .collect(Collectors.groupingBy(item -> YearMonth.from(item.getTransactionDate()),
                        TreeMap::new, Collectors.toList()));

        List<MonthlyRewardResponse> monthly = new ArrayList<MonthlyRewardResponse>();
        int totalPoints = 0;
        for (Map.Entry<YearMonth, List<TransactionRewardResponse>> entry : byMonth.entrySet()) {
            int points = entry.getValue().stream().mapToInt(TransactionRewardResponse::getRewardPoints).sum();
            totalPoints += points;
            monthly.add(new MonthlyRewardResponse(entry.getKey(), entry.getValue().size(), points));
        }
        LOGGER.info("Calculated {} points across {} transactions for customer {}",
                totalPoints, details.size(), customer.getId());
        return new CustomerRewardResponse(customer, startDate, endDate, details.size(),
                totalPoints, monthly, details);
    }
}
