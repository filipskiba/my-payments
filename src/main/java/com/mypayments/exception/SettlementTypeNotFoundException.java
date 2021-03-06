package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find settlement type")
public class SettlementTypeNotFoundException extends Exception {
    public SettlementTypeNotFoundException() {
        super();
    }

    public SettlementTypeNotFoundException(String msg) {
        super(msg);
    }
}
