package com.mypayments.service;

import com.mypayments.domain.BankAccount;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.repository.BankAccountRepository;
import com.mypayments.repository.ContractorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    ContractorRepository contractorRepository;


    public List<BankAccount> getContractorBankAccounts(final Long contractorId) throws ContractorNotFoundException {
        if (contractorRepository.findById(contractorId).isPresent()) {
            return bankAccountRepository.findBankAccountByContractor_Id(contractorId);
        } else {
            LOGGER.error("Can not find contractor with ID" + contractorId);
            throw new ContractorNotFoundException();
        }
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }


    public BankAccount getBankAccountById(final Long bankAccountId) throws BankAccountNotFoundException {
        if (bankAccountRepository.findById(bankAccountId).isPresent()) {
            return bankAccountRepository.findById(bankAccountId).get();
        } else {
            LOGGER.error("Can not find bank account with ID: " + bankAccountId);
            throw new BankAccountNotFoundException();
        }
    }

    public BankAccount saveBankAccount(final BankAccount bankAccount) {
        LOGGER.info("Successfully saved bank account");
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount updateBankAccount(BankAccount bankAccount) throws BankAccountNotFoundException {
        if (bankAccountRepository.findById(bankAccount.getId()).isPresent()) {
            return bankAccountRepository.save(bankAccount);
        } else {
            LOGGER.error("Can not find bank account with ID: " + bankAccount.getId());
            throw new BankAccountNotFoundException();
        }
    }

    public void deleteBankAccountById(final Long bankAccountId) throws BankAccountNotFoundException {
        if (bankAccountRepository.findById(bankAccountId).isPresent()) {
            bankAccountRepository.deleteById(bankAccountId);
            LOGGER.info("Successfully deleted bank account with ID: " + bankAccountId);
        } else {
            LOGGER.error("Can not find bank account with ID: " + bankAccountId);
            throw new BankAccountNotFoundException();
        }
    }
}
