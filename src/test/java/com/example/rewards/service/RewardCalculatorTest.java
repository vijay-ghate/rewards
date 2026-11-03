package com.example.rewards.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorTest {
    private final RewardCalculator calculator = new RewardCalculator();

    @Test void calculatesZeroForAmountBelowOrEqualToFifty() {
        assertEquals(0, calculator.calculate(new BigDecimal("50.00")));
        assertEquals(0, calculator.calculate(new BigDecimal("45.00")));
    }
    @Test void calculatesOnePointPerDollarBetweenFiftyAndOneHundred() {
        assertEquals(25, calculator.calculate(new BigDecimal("75.00")));
        assertEquals(50, calculator.calculate(new BigDecimal("100.00")));
    }
    @Test void calculatesTwoPointsForEachDollarAboveOneHundred() {
        assertEquals(90, calculator.calculate(new BigDecimal("120.00")));
        assertEquals(150, calculator.calculate(new BigDecimal("150.00")));
    }
    @Test void rejectsNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculate(new BigDecimal("-1.00")));
    }
}
