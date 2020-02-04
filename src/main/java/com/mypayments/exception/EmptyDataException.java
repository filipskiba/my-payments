package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There is empty bank account or nip id in requested data.")
public class EmptyDataException extends Exception {
    public EmptyDataException() {
        super();
    }

    public EmptyDataException(String msg) {
        super(msg);
    }
}
