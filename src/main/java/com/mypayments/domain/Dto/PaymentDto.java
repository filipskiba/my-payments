package com.mypayments.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PaymentDto {
    private String paymentType;
    private Long contractorId;
    private String dateOfTransfer;
    private BigDecimal amount;
    private String currency;
    private BigDecimal vat;
    private Boolean isSplitPayment;
    private Long settlementId;
    
}
