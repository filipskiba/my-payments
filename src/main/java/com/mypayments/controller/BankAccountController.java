package com.mypayments.controller;

import com.mypayments.domain.Dto.BankAccountDto;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.mapper.BankAccountMapper;
import com.mypayments.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountMapper bankAccountMapper;

   /* @RequestMapping(method = RequestMethod.GET, value = "/bankAccounts/{bankAccountId}")
    public BankAccountDto getBankAccount(@PathVariable("bankAccountId") Long bankAccountId) throws BankAccountNotFoundException {
        return bankAccountMapper.mapToBankAccountDto(bankAccountService.getBankAccountById(bankAccountId));
    }*/

    @RequestMapping(method = RequestMethod.GET, value = "/bankAccounts/{contractorId}")
    public List<BankAccountDto> getContractorBankAccounts(@PathVariable("contractorId") Long contractorId) throws ContractorNotFoundException {
        return bankAccountMapper.mapToBankAccountsDtoList(bankAccountService.getContractorBankAccounts(contractorId));
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
