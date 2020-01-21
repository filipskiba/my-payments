package com.mypayments.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StatusDto {
    private Long contractorId;
    private Boolean isContractorOnWL;
    private Boolean isBankAccountOnWL;
    private String statusDate;
}
