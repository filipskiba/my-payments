package com.mypayments.service;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Disposition;
import com.mypayments.domain.DispositionFileLine;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.validators.BankAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DispositionFileService {

    @Autowired
    private BankAccountValidator bankAccountValidator;

    public String createDispositionFileFromList(List<Disposition> dispositions) throws InvalidDataFormatException {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder(result);
        for (Disposition d : dispositions) {
            DispositionFileLine dispositionFileLine = new DispositionFileLine().builder()
                    .orderCode("110")
                    .dateOfExecution(reformatDate(d.getDateOfExecution()))
                    .amount(reformatAmount(d.getAmount()))
                    .ownerBankAccountCode(getBankAccountCode(d.getOwnerBankAccount()))
                    .zeroField("0")
                    .ownerBankAcc(reformatBankAccount(d.getOwnerBankAccount()))
                    .contractorBankAcc(reformatBankAccount(d.getBankAccount()))
                    .ownerInformations(reformatContractorInformation(d.getOwner()))
                    .contractorInformations(reformatContractorInformation(d.getContractor()))
                    .zeroField("0")
                    .contractorBankAccountCode(getBankAccountCode(d.getBankAccount()))
                    .dispositionTitle(reformatTitle(d.getTitle()))
                    .emptyField("\"" + "\"")
                    .transferCode("\"" + "\"")
                    .splitPayment("51")
                    .build();

            stringBuilder.append(dispositionFileLine.getOrderCode() + ",");
            stringBuilder.append(dispositionFileLine.getDateOfExecution() + ",");
            stringBuilder.append(dispositionFileLine.getAmount() + ",");
            stringBuilder.append(dispositionFileLine.getOwnerBankAccountCode() + ",");
            stringBuilder.append(dispositionFileLine.getZeroField() + ",");
            stringBuilder.append("\"" + dispositionFileLine.getOwnerBankAcc() + "\"" + ",");
            stringBuilder.append("\"" + dispositionFileLine.getContractorBankAcc() + "\"" + ",");
            stringBuilder.append("\"" + dispositionFileLine.getOwnerInformations() + "\"" + ",");
            stringBuilder.append("\"" + dispositionFileLine.getContractorInformations() + "\""  + ",");
            stringBuilder.append(dispositionFileLine.getZeroField() + ",");
            stringBuilder.append(dispositionFileLine.getContractorBankAccountCode() + ",");
            stringBuilder.append("\"" + dispositionFileLine.getDispositionTitle() + "\"" + ",");
            stringBuilder.append(dispositionFileLine.getEmptyField() + ",");
            stringBuilder.append(dispositionFileLine.getTransferCode() + ",");
            stringBuilder.append("\"" + dispositionFileLine.getSplitPayment() + "\"" + ",");
            stringBuilder.append("\n");

        }

        return stringBuilder.toString();
    }

    private String reformatTitle(String title) {
        int len = title.length();
        int currentChar = 0;
        StringBuilder sb = new StringBuilder(title);
        while ((currentChar + 35) < len) {
            currentChar = +35;
            sb.insert(currentChar, "|");
        }
        sb.append("|");
        return sb.toString();
    }

    private String reformatContractorInformation(Contractor contractor) {
        String contractorName = contractor.getContractorName();

        int len = contractorName.length();
        int currentChar = 0;
        String result = contractorName;
        StringBuilder sb = new StringBuilder(result);
        while ((currentChar + 35) < len) {
            currentChar = +35;
            sb.insert(currentChar, "|");
        }
        sb.append("|");
        sb.append(contractor.getAddress());
        sb.append("|");
        sb.append(contractor.getZipCode() + " " + contractor.getCity());
        return sb.toString();
    }

    private String reformatDate(LocalDate date) {
        String result = date.toString().replaceAll("[\\-]", "");
        return result;
    }

    private String reformatBankAccount(BankAccount bankAccount) throws InvalidDataFormatException {
        String result = bankAccountValidator.reformat(bankAccount.getAccountNumber());
        return result;
    }

    private String reformatAmount(BigDecimal amount) {
        String result = amount.toString().replaceAll("\\.", "");
        return result;
    }

    private String getBankAccountCode(BankAccount bankAccount) throws InvalidDataFormatException {
        String result = "";
        StringBuilder sb = new StringBuilder(result);
        String formattedBankAccount = bankAccountValidator.reformat(bankAccount.getAccountNumber().replaceAll(" ", ""));
        for (int i = 2; i < 10; i++) {
            sb.append(formattedBankAccount.charAt(i));
        }
        return sb.toString();
    }

}
