package com.mypayments.service;

import com.mypayments.domain.Contractor;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.repository.ContractorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    ContractorRepository contractorRepository;

    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }

    public Contractor getContractorById(final Long contractorId) throws ContractorNotFoundException {
        if(contractorRepository.findById(contractorId).isPresent()){
            return contractorRepository.findById(contractorId).get();
        }
        else {
            LOGGER.error("Can not find contractor with ID: "+contractorId);
            throw new ContractorNotFoundException();
        }
    }
    public Contractor saveContractor(final Contractor contractor){
        LOGGER.info("Successfully saved contractor");
        return contractorRepository.save(contractor);
    }

    public Contractor updateContractor(final Contractor contractor) throws ContractorNotFoundException {
        if(contractorRepository.findById(contractor.getId()).isPresent()){
            return contractorRepository.save(contractor);
        }
        else {
            LOGGER.error("Can not find contractor with ID: "+contractor.getId());
            throw new ContractorNotFoundException();
        }
    }
    public void deleteContractorById(final Long contractorId) throws ContractorNotFoundException {
        if(contractorRepository.findById(contractorId).isPresent()){
            contractorRepository.deleteById(contractorId);
            LOGGER.info("Successfully deleted contractor with ID: "+contractorId);
        }
        else {
            LOGGER.error("Can not find contractor with ID: "+contractorId);
            throw new ContractorNotFoundException();
        }
    }
}
