package com.mypayments.validators;


import com.mypayments.exception.InvalidDataFormatException;
import org.springframework.stereotype.Component;

@Component
public class NipValidator implements Validator {

    @Override
    public String reformat(String nipId) throws InvalidDataFormatException {
        String result = nipId.replaceAll("-", "").replaceAll("\\s+", "");
        if (result.length() == 10) {
            return result;
        } else throw new InvalidDataFormatException();
    }
    @Override
    public boolean isDataEmpty(String nip) {
        if (nip == null) {
            return true;
        } else return false;
    }

}
