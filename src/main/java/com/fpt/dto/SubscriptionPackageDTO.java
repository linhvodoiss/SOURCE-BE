package com.fpt.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPackageDTO {
    private Integer id;
    private String name;
    private Float price;
    private Float discount;
    private String billingCycle;
    private Boolean isActive;
    private String options;
    private Integer simulatedCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
