package com.mypayments.domain.Dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class ContractorDto {
    private Long contractorId;
    private String contractorName;
    private String nipId;
    private List<BankAccountDto> bankAccountDtos;
    private List<SettlementDto> settlementDtos;
    private List<PaymentDto> paymentDtos;
    private List<StatusDto> statusDtos;
}
