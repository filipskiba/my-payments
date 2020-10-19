package com.mypayments.domain.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SettlementDto {
    private Long settlementId;
    private String document;
    private String settlementTypeName;
    private Long contractorId;
    private Long ownerId;
    private String contractorName;
    private String ownerName;
    private String dateOfIssue;
    private String dateOfPayment;
    private BigDecimal amount;
    private List<PaymentDto> paymentDtoList;
    private Boolean isPaid;
    private BigDecimal paidAmount;
    private String bankAccountNumber;
    private List<DispositionDto> dispositionDtoList;
    private BigDecimal vatAmount;
    private String ownerBankAccountNumber;
    private String title;

}
