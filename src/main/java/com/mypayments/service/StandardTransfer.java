package com.mypayments.service;

import com.mypayments.domain.Disposition;
import com.mypayments.domain.DispositionFileLine;
import com.mypayments.exception.InvalidDataFormatException;
import org.springframework.stereotype.Component;

@Component
public class StandardTransfer extends Transfer {



    @Override
    String createTransfer(Disposition disposition) throws InvalidDataFormatException {
        StringBuilder stringBuilder = new StringBuilder();
        DispositionFileLine dispositionFileLine = new DispositionFileLine().builder()
                .orderCode("110")
                .dateOfExecution(reformatDate(disposition.getDateOfExecution()))
                .amount(reformatAmount(disposition.getAmount()))
                .ownerBankAccountCode(getBankAccountCode(disposition.getOwnerBankAccount()))
                .zeroField("0")
                .ownerBankAcc(reformatBankAccount(disposition.getOwnerBankAccount()))
                .contractorBankAcc(reformatBankAccount(disposition.getBankAccount()))
                .ownerInformations(reformatContractorInformation(disposition.getOwner()))
                .contractorInformations(reformatContractorInformation(disposition.getContractor()))
                .zeroField("0")
                .contractorBankAccountCode(getBankAccountCode(disposition.getBankAccount()))
                .dispositionTitle(reformatTitle(disposition.getTitle()))
                .emptyField("\"" + "\"")
                .transferCode("\"" + disposition.getSettlement().getSettlementType().getSettlementTypeCode() + "\"")
                .splitPayment("\"" + disposition.getSettlement().getSettlementType().getSplitPayment() + "\"")
                .build();

        stringBuilder.append(dispositionFileLine.getOrderCode() + ",");
        stringBuilder.append(dispositionFileLine.getDateOfExecution() + ",");
        stringBuilder.append(dispositionFileLine.getAmount() + ",");
        stringBuilder.append(dispositionFileLine.getOwnerBankAccountCode() + ",");
        stringBuilder.append(dispositionFileLine.getZeroField() + ",");
        stringBuilder.append("\"" + dispositionFileLine.getOwnerBankAcc() + "\"" + ",");
        stringBuilder.append("\"" + dispositionFileLine.getContractorBankAcc() + "\"" + ",");
        stringBuilder.append("\"" + dispositionFileLine.getOwnerInformations() + "\"" + ",");
        stringBuilder.append("\"" + dispositionFileLine.getContractorInformations() + "\"" + ",");
        stringBuilder.append(dispositionFileLine.getZeroField() + ",");
        stringBuilder.append(dispositionFileLine.getContractorBankAccountCode() + ",");
        stringBuilder.append("\"" + dispositionFileLine.getDispositionTitle() + "\"" + ",");
        stringBuilder.append(dispositionFileLine.getEmptyField() + ",");
        stringBuilder.append(dispositionFileLine.getTransferCode() + ",");
        stringBuilder.append("\"" + dispositionFileLine.getSplitPayment() + "\"" );

        return stringBuilder.toString();
    }
}
