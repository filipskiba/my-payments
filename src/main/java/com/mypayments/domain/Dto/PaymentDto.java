package com.mypayments.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PaymentDto {
    private Long paymentId;
    private Long contractorId;
    private String contractorName;
    private LocalDate dateOfTransfer;
    private BigDecimal amount;
    private Long settlementId;
    
}
