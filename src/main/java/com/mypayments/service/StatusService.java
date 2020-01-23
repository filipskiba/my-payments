package com.mypayments.service;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Status;
import com.mypayments.exception.StatusNotFoundException;
import com.mypayments.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);
    private static final String MESSAGE = "Can not find status with ID: ";

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAllStatuses(){
        return statusRepository.findAll();
    }
    public Status getStatusById(final Long statusId) throws StatusNotFoundException {
        if(statusRepository.findById(statusId).isPresent()){
            return statusRepository.findById(statusId).get();
        }
        else {
            LOGGER.error(MESSAGE + statusId);
            throw new StatusNotFoundException();
        }
    }

    public Status saveStatus(final Status status) {
        LOGGER.info("Successfully save status");
        return statusRepository.save(status);
    }

    public Status updateStatus(final Status status) throws StatusNotFoundException {
        if(statusRepository.findById(status.getId()).isPresent()){
            return statusRepository.save(status);
        }
        else {
            LOGGER.info(MESSAGE + status.getId());
            throw new StatusNotFoundException();
        }
    }

    public void deleteStatusById(final Long statusId) throws StatusNotFoundException {
        if(statusRepository.findById(statusId).isPresent()){
            statusRepository.deleteById(statusId);
            LOGGER.info("Successfully deleted status with ID: " + statusId);
        }
        else {
            LOGGER.error(MESSAGE + statusId);
            throw new StatusNotFoundException();
        }
    }
    public boolean checkNipStatus(Contractor contractor, String validationDate){
        return false;
    }
    public boolean checkBankAccount(Contractor contractor, String validationDate){
        return false;
    }
    public boolean checkRegonNumber(Contractor contractor, String validationDate){
        return false;
    }
}
