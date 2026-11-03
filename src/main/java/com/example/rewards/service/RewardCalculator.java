package com.example.rewards.service;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class RewardCalculator {
    private static final BigDecimal FIFTY = new BigDecimal("50");
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    public int calculate(BigDecimal purchaseAmount) {
        if (purchaseAmount == null || purchaseAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Purchase amount must be zero or greater");
        }
        BigDecimal points = BigDecimal.ZERO;
        if (purchaseAmount.compareTo(ONE_HUNDRED) > 0) {
            points = purchaseAmount.subtract(ONE_HUNDRED).multiply(new BigDecimal("2"))
                    .add(FIFTY);
        } else if (purchaseAmount.compareTo(FIFTY) > 0) {
            points = purchaseAmount.subtract(FIFTY);
        }
        return points.setScale(0, RoundingMode.FLOOR).intValueExact();
    }
}
