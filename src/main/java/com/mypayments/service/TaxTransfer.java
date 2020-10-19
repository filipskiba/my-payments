package com.mypayments.service;

import com.mypayments.domain.Disposition;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.validators.BankAccountValidator;
import com.mypayments.validators.NipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxTransfer extends Transfer {


    @Autowired
    private BankAccountValidator bankAccountValidator;

    @Autowired
    private NipValidator nipValidator;


    @Override
    String createTransfer(Disposition disposition) throws InvalidDataFormatException {
        return "";
    }
}
