package com.fpt.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenseDTO {
    private Long id;
    private String licenseKey;
    private LocalDateTime expiryDate;
    private String ip;
    private Integer userId;
    private Integer subscriptionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
