package com.mypayments.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class TransferFactory {
    public static final String STANDARD_TRANSFER = "STANDARD_TRANSFER";
    public static final String SPLIT_PAYMENT_TRANSFER = "SPLIT_PAYMENT_TRANSFER";
    public static final String TAX_TRANSFER = "TAX_TRANSFER";

    @Autowired
    private StandardTransfer standardTransfer;

    @Autowired
    private SplitPaymentTransfer splitPaymentTransfer;

    @Autowired
    private TaxTransfer taxTransfer;

    public final Transfer makeTransfer(final String transfer) {
        switch (transfer) {
            case STANDARD_TRANSFER:
                return standardTransfer;
            case SPLIT_PAYMENT_TRANSFER:
                return splitPaymentTransfer;
            case TAX_TRANSFER:
                return taxTransfer;
            default:
                return null;
        }

    }
}
