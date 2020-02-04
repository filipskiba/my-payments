package com.mypayments.service;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Status;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.EmptyDataException;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.exception.StatusNotFoundException;
import com.mypayments.gov.GovFacade;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {
    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private GovFacade govFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);
    private static final String MESSAGE = "Can not find status with ID: ";

    @Autowired
    private StatusRepository statusRepository;

    public Status createStatusForContractor(Long contractorId) throws InvalidDataFormatException, EmptyDataException, ContractorNotFoundException {
        if (contractorRepository.findById(contractorId).isPresent()) {
            Contractor contractor = contractorRepository.findById(contractorId).get();
            if(contractor.getBankAccounts().isEmpty()){
                throw new EmptyDataException();
            }
            else {
                Status status = govFacade.createStatus(contractor);
                return statusRepository.save(status);
            }
        } else {
            LOGGER.error("Can not find contractor with ID: " + contractorId);
            throw new ContractorNotFoundException();
        }
    }

    public List<Status> createStatusForAllContractors() {
        List<Status> statuses = new ArrayList<>();
        contractorRepository.findAll().stream().forEach(n -> {
            try {
                if(n.getBankAccounts().isEmpty()){
                    throw new EmptyDataException();
                }
                else {
                    Status status = govFacade.createStatus(n);
                    statusRepository.save(status);
                    statuses.add(status);
                }
            } catch (InvalidDataFormatException e) {
                e.printStackTrace();
            } catch (EmptyDataException e) {
                e.printStackTrace();
            }
        });
        return statuses;
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Status getStatusById(final Long statusId) throws StatusNotFoundException {
        if (statusRepository.findById(statusId).isPresent()) {
            return statusRepository.findById(statusId).get();
        } else {
            LOGGER.error(MESSAGE + statusId);
            throw new StatusNotFoundException();
        }
    }

    public Status saveStatus(final Status status) {
        LOGGER.info("Successfully save status");
        return statusRepository.save(status);
    }

    public Status updateStatus(final Status status) throws StatusNotFoundException {
        if (statusRepository.findById(status.getId()).isPresent()) {
            return statusRepository.save(status);
        } else {
            LOGGER.info(MESSAGE + status.getId());
            throw new StatusNotFoundException();
        }
    }

    public void deleteStatusById(final Long statusId) throws StatusNotFoundException {
        if (statusRepository.findById(statusId).isPresent()) {
            statusRepository.deleteById(statusId);
            LOGGER.info("Successfully deleted status with ID: " + statusId);
        } else {
            LOGGER.error(MESSAGE + statusId);
            throw new StatusNotFoundException();
        }
    }
}
