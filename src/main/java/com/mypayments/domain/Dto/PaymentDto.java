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
    private Long paymentId;
    private Long contractorId;
    private String contractorName;
    private String dateOfTransfer;
    private BigDecimal amount;
    private Long settlementId;
    private Long dispositionId;
    private String bankAccountNumber;
    private Long ownerId;
    private String ownerBankAccountNumber;
    private String ownerName;
    private String document;
    private BigDecimal vatAmount;
}
