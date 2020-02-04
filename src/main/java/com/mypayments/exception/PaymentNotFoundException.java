package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find payment.")
public class PaymentNotFoundException extends Exception {
    public PaymentNotFoundException() {
        super();
    }

    public PaymentNotFoundException(String msg) {
        super(msg);
    }
}
