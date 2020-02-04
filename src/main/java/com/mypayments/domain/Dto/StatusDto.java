package com.mypayments.domain.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class StatusDto {
    private Long statusId;
    private Long contractorId;
    private Boolean isContractorOnWL;
    private String statusDate;
    private String contractorName;
}
