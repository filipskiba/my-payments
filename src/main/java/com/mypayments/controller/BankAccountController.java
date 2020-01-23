package com.mypayments.controller;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Dto.BankAccountDto;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.mapper.BankAccountMapper;
import com.mypayments.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/bankAccounts/{bankAccountId}")
    public BankAccountDto getBankAccount(@PathVariable("bankAccountId") Long bankAccountId) throws BankAccountNotFoundException {
        return bankAccountMapper.mapToBankAccountDto(bankAccountService.getBankAccountById(bankAccountId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bankAccounts")
    public List<BankAccountDto> getContractorBankAccounts() {
        return bankAccountMapper.mapToBankAccountsDtoList(bankAccountService.getAllBankAccounts());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bankAccounts")
    public void createBankAccount(@RequestBody BankAccountDto bankAccountDto) throws ContractorNotFoundException {
        bankAccountService.saveBankAccount(bankAccountMapper.mapToBankAccount(bankAccountDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/bankAccounts")
    public BankAccountDto updateBankAccount(@RequestBody BankAccountDto bankAccountDto) throws ContractorNotFoundException, BankAccountNotFoundException {
        return bankAccountMapper.mapToBankAccountDto(bankAccountService.updateBankAccount(bankAccountMapper.mapToBankAccount(bankAccountDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/bankAccounts/{bankAccountId}")
    public void deleteBankAccount(@PathVariable("bankAccountId") Long bankAccountId) throws BankAccountNotFoundException {
        bankAccountService.deleteBankAccountById(bankAccountId);
    }


}
