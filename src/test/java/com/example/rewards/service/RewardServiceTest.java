package com.example.rewards.service;

import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class RewardServiceTest {
    @Test void rejectsReversedDateRange() {
        TransactionRepository transactionRepository = new TransactionRepository(Runnable::run);
        RewardService service = new RewardService(new CustomerRepository(),
                transactionRepository, new RewardCalculator());
        assertThrows(InvalidDateRangeException.class, () -> service.getRewards(1L,
                LocalDate.parse("2026-03-31"), LocalDate.parse("2026-01-01")));
    }
}
