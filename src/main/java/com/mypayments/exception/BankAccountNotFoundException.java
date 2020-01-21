package com.mypayments.exception;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException() {
        super();
    }

    public BankAccountNotFoundException(String msg) {
        super(msg);
    }
}
