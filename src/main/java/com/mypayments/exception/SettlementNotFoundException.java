package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find settlement.")
public class SettlementNotFoundException extends Exception {
    public SettlementNotFoundException() {
        super();
    }

    public SettlementNotFoundException(String msg) {
        super(msg);
    }
}
