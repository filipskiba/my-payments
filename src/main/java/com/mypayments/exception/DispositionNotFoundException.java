package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find contractor.")
public class DispositionNotFoundException extends Exception {
    public DispositionNotFoundException() {
        super();
    }

    public DispositionNotFoundException(String msg) {
        super(msg);
    }
}
