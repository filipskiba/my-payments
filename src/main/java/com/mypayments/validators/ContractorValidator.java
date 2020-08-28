package com.mypayments.validators;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.exception.EmptyDataException;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.gov.GovClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContractorValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractorValidator.class);

    @Autowired
    private GovClient govClient;

    @Autowired
    private NipValidator nipValidator;

    @Autowired
    private BankAccountValidator bankAccountValidator;

    private boolean isContractorOnWL(String nipId, String bankAccount) throws InvalidDataFormatException, EmptyDataException {
        if (!nipValidator.isDataEmpty(nipId) && !bankAccountValidator.isDataEmpty(bankAccount)) {
            nipId = nipValidator.reformat(nipId);
            bankAccount = bankAccountValidator.reformat(bankAccount);
            if (govClient.getGovSubject(nipId, bankAccount, LocalDate.now()).getResultDto().getAccountAssigned().equals("TAK")) {
                LOGGER.info("Contractor with nip id: " + nipId + " and bank account number: " + bankAccount + " is on white list");
                return true;
            } else {
                LOGGER.error("Contractor with nip id: " + nipId + " and bank account number: " + bankAccount + " is not on white list!");
                return false;
            }
        } else {
            throw new EmptyDataException();
        }
    }

    public boolean checkContractor(Contractor contractor) throws InvalidDataFormatException, EmptyDataException {

        List<Contractor> invalidContractor = new ArrayList<>();
        for (BankAccount b : contractor.getBankAccounts()) {
            if (!isContractorOnWL(contractor.getNipId(), b.getAccountNumber())) {
                invalidContractor.add(contractor);
            }
        }
        if (invalidContractor.isEmpty()) {
            return true;
        } else return false;
    }
}
