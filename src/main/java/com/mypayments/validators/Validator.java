package com.mypayments.validators;


import com.mypayments.exception.InvalidDataFormatException;

public interface Validator {
    String reformat(String value) throws InvalidDataFormatException;
    boolean isDataEmpty(String value);
}
