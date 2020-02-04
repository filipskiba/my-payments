package com.mypayments.validators;


import com.mypayments.exception.InvalidDataFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BankAccountValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountValidator.class);

    @Override
    public String reformat(String bankAccount) throws InvalidDataFormatException {
        String result = bankAccount.replaceAll("-", "").replaceAll("\\s+", "");
        if (result.length() == 26) {
            return result;
        } else {
            LOGGER.error("Invalid bank account format");
            throw new InvalidDataFormatException();
        }
    }

    @Override
    public boolean isDataEmpty(String bankAccount) {
        if (bankAccount == null) {
            LOGGER.error("Bank account field is empty");
            return true;
        } else {
            return false;
        }
    }
}
