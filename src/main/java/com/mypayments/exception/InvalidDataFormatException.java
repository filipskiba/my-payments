package com.mypayments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid nip id or bank account number.")
public class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException() {
        super();
    }

    public InvalidDataFormatException(String msg) {
        super(msg);
    }
}
