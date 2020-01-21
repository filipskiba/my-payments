package com.mypayments.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ContractorDto {
    private String contractorName;
    private String nipId;
    private String regonId;
    private String peselId;
    private List<BankAccountDto> bankAccountDtos;
    private List<SettlementDto> settlementDtos;
    private List<PaymentDto> paymentDtos;
    private List<StatusDto> statusDtos;

    public ContractorDto(String contractorName, String nipId, String regonId, String peselId) {
        this.contractorName = contractorName;
        this.nipId = nipId;
        this.regonId = regonId;
        this.peselId = peselId;
    }
}
