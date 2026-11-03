package com.example.rewards.controller;

import com.example.rewards.RewardsApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RewardsApplication.class)
@AutoConfigureMockMvc
class RewardControllerIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Disabled
    @Test void returnsRewardSummaryForKnownCustomer() throws Exception {
        mockMvc.perform(get("/api/customers/1/rewards")
                .param("startDate", "2026-01-01")
                .param("endDate", "2026-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer.name").value("Alice Johnson"))
                .andExpect(jsonPath("$.transactionCount").value(6))
                .andExpect(jsonPath("$.totalRewardPoints").value(555))
                .andExpect(jsonPath("$.monthlyRewards.length()").value(3));
    }

    @Test void returnsNotFoundForUnknownCustomer() throws Exception {
        mockMvc.perform(get("/api/customers/999/rewards")
                .param("startDate", "2026-01-01")
                .param("endDate", "2026-03-31"))
                .andExpect(status().isNotFound());
    }

    @Test void returnsBadRequestForInvalidDateRange() throws Exception {
        mockMvc.perform(get("/api/customers/1/rewards")
                .param("startDate", "2026-03-31")
                .param("endDate", "2026-01-01"))
                .andExpect(status().isBadRequest());
    }
}
