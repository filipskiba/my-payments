package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find bank account.")
public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException() {
        super();
    }

    public BankAccountNotFoundException(String msg) {
        super(msg);
    }
}
