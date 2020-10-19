package com.mypayments.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DispositionFileLine {
    private String orderCode;
    private String dateOfExecution;
    private String amount;
    private String vatAmount;
    private String ownerBankAccountCode;
    private String zeroField;
    private String ownerBankAcc;
    private String contractorBankAcc;
    private String ownerInformations;
    private String contractorInformations;
    private String contractorBankAccountCode;
    private String dispositionTitle;
    private String emptyField;
    private String transferCode;
    private String splitPayment;
    private String nipId;
    private String document;

}
