package com.mypayments.gov;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Status;
import com.mypayments.exception.EmptyDataException;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.validators.ContractorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GovFacade {

    @Autowired
    private ContractorValidator contractorValidator;

    public Status createStatus(Contractor contractor) throws InvalidDataFormatException, EmptyDataException {
        return new Status().builder()
                .contractor(contractor)
                .statusDate(LocalDate.now())
                .isContractorOnWL(contractorValidator.checkContractor(contractor)).build();
    }
}
