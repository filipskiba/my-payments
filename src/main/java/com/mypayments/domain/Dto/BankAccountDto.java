package com.mypayments.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BankAccountDto {
    private Long bankAccountId;
    private Long contractorId;
    private String bankAccountNumber;
}
