package com.example.rewards.controller;

import com.example.rewards.dto.CustomerRewardResponse;
import com.example.rewards.service.RewardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/customers")
public class RewardController {
    private final RewardService rewardService;

    public RewardController(RewardService rewardService) { this.rewardService = rewardService; }

    @GetMapping("/{customerId}/rewards")
    public CompletableFuture<ResponseEntity<CustomerRewardResponse>> getCustomerRewards(
            @PathVariable @NotNull Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return rewardService.getRewards(customerId, startDate, endDate)
                .thenApply(ResponseEntity::ok);
    }
}
