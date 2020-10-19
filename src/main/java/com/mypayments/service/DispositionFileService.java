package com.mypayments.service;

import com.mypayments.domain.Disposition;
import com.mypayments.exception.InvalidDataFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DispositionFileService {

    @Autowired
    private TransferFactory transferFactory;

    public String createDispositionFileFromList(List<Disposition> dispositions) throws InvalidDataFormatException {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder(result);
        for (Disposition disposition : dispositions) {
            String transferType = disposition.getSettlement().getSettlementType().getSettlementTypeName();

            stringBuilder.append(transferFactory.makeTransfer(transferType).createTransfer(disposition));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
