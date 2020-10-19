package com.mypayments.service;

import com.mypayments.domain.Disposition;
import com.mypayments.domain.DispositionFileLine;
import com.mypayments.exception.InvalidDataFormatException;
import org.springframework.stereotype.Component;

@Component
public class SplitPaymentTransfer extends Transfer {

    @Override
    String createTransfer(Disposition disposition) throws InvalidDataFormatException {
        StringBuilder stringBuilder = new StringBuilder();
        DispositionFileLine dispositionFileLine = new DispositionFileLine().builder()
                .orderCode("110")
                .dateOfExecution(reformatDate(disposition.getDateOfExecution()))
                .amount(reformatAmount(disposition.getSettlement().getAmount()))
                .ownerBankAccountCode(getBankAccountCode(disposition.getSettlement().getOwnerBankAccount()))
                .zeroField("0")
                .ownerBankAcc(reformatBankAccount(disposition.getSettlement().getOwnerBankAccount()))
                .contractorBankAcc(reformatBankAccount(disposition.getSettlement().getBankAccount()))
                .ownerInformations(reformatContractorInformation(disposition.getSettlement().getOwner()))
                .contractorInformations(reformatContractorInformation(disposition.getSettlement().getContractor()))
                .zeroField("0")
                .contractorBankAccountCode(getBankAccountCode(disposition.getSettlement().getBankAccount()))
                .vatAmount(String.valueOf(disposition.getSettlement().getVatAmount()))
                .nipId(getNipId(disposition.getSettlement().getContractor()))
                .dispositionTitle(reformatTitleTo33(disposition.getSettlement().getTitle()))
                .document(disposition.getSettlement().getDocument())
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
        stringBuilder.append("\""+"/VAT/"+dispositionFileLine.getVatAmount().replace(".",","));
        stringBuilder.append("/IDC/"+dispositionFileLine.getNipId());
        stringBuilder.append("/INV/"+dispositionFileLine.getDocument());
        stringBuilder.append("/TXT/");
        stringBuilder.append("\"" + dispositionFileLine.getDispositionTitle() + "\"" + ",");
        stringBuilder.append(dispositionFileLine.getEmptyField() + ",");
        stringBuilder.append(dispositionFileLine.getTransferCode() + ",");
        stringBuilder.append("\"" + dispositionFileLine.getSplitPayment() + "\"" + ",");

        return stringBuilder.toString();
    }

}
