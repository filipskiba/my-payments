package com.mypayments.domain.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class DispositionDto {
    private Long dispositionId;
    private String dateOfExecution;
    private Boolean isExecuted;
    private String title;
    private BigDecimal amount;
    private Long contractorId;
    private String contractorName;
    private Long bankAccountId;
    private List<PaymentDto> paymentDtoList;
    private Long ownerId;
    private String ownerBankAccountNumber;
    private String ownerName;

}
