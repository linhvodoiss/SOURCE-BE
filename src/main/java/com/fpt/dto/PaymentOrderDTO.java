package com.fpt.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrderDTO {
    private Long id;
    private String orderId;
    private String paymentLink;
    private Float amount;
    private String billingCycle;
    private String target;
    private String paymentStatus;
    private Integer userId;
    private Integer subscriptionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
