package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find status")
public class StatusNotFoundException extends Exception {
    public StatusNotFoundException() {
        super();
    }

    public StatusNotFoundException(String msg) {
        super(msg);
    }
}
