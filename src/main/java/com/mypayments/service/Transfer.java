package com.mypayments.service;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Disposition;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.validators.BankAccountValidator;
import com.mypayments.validators.NipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Transfer {

    @Autowired
    private BankAccountValidator bankAccountValidator;

    @Autowired
    private NipValidator nipValidator;

    abstract String createTransfer(Disposition disposition) throws InvalidDataFormatException;

    protected String reformatTitle(String title) {
        if(title==null){
            title="";
        }
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

    protected String reformatTitleTo33(String title) {
        if(title==null){
            title="";
        }
        int len = title.length();
        int currentChar = 0;
        StringBuilder sb = new StringBuilder(title);
        while ((currentChar + 33) < len) {
            currentChar = +33;
            sb.insert(currentChar, "|");
        }
        sb.append("|");
        return sb.toString();
    }

    protected String reformatContractorInformation(Contractor contractor) {
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

    protected String reformatDate(LocalDate date) {
        String result = date.toString().replaceAll("[\\-]", "");
        return result;
    }

    protected String reformatBankAccount(BankAccount bankAccount) throws InvalidDataFormatException {
        String result = bankAccountValidator.reformat(bankAccount.getAccountNumber());
        return result;
    }

    protected String reformatAmount(BigDecimal amount) {
        String result = amount.toString().replaceAll("\\.", "");
        return result;
    }

    protected String getBankAccountCode(BankAccount bankAccount) throws InvalidDataFormatException {
        StringBuilder sb = new StringBuilder();
        String formattedBankAccount = bankAccountValidator.reformat(bankAccount.getAccountNumber().replaceAll(" ", ""));
        for (int i = 2; i < 10; i++) {
            sb.append(formattedBankAccount.charAt(i));
        }
        return sb.toString();
    }
    protected String getNipId(Contractor contractor) throws InvalidDataFormatException{
        return nipValidator.reformat(contractor.getNipId());
    }
}
