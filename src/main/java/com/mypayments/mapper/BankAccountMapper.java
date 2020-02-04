package com.mypayments.mapper;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Dto.BankAccountDto;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.repository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {

    @Autowired
    private ContractorRepository contractorRepository;

    public BankAccount mapToBankAccount(final BankAccountDto bankAccountDto) throws ContractorNotFoundException {
        return new BankAccount().builder()
                .id(bankAccountDto.getBankAccountId())
                .contractor(contractorRepository.findById(bankAccountDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .accountNumber(bankAccountDto.getBankAccountNumber()).build();
    }

    public BankAccountDto mapToBankAccountDto(final BankAccount bankAccount) {
        return new BankAccountDto().builder()
                .bankAccountId(bankAccount.getId())
                .contractorId(bankAccount.getContractor().getId())
                .bankAccountNumber(bankAccount.getAccountNumber()).build();
    }

    public List<BankAccount> mapToBankAccountsList(final List<BankAccountDto> bankAccountsDto) throws ContractorNotFoundException {
        if (bankAccountsDto!=null) {
            List<BankAccount> accounts = new ArrayList<>();
            for (BankAccountDto a : bankAccountsDto) {
                if (contractorRepository.findById(a.getContractorId()).isPresent()) {
                    accounts.add(new BankAccount().builder()
                            .id(a.getBankAccountId())
                            .contractor(contractorRepository.findById(a.getContractorId()).get())
                            .accountNumber(a.getBankAccountNumber()).build());
                } else {
                    throw new ContractorNotFoundException();
                }
            }
            return accounts;
        }
        else return new ArrayList<>();
    }

    public List<BankAccountDto> mapToBankAccountsDtoList(final List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(b -> new BankAccountDto().builder()
                        .bankAccountId(b.getId())
                        .contractorId(b.getContractor().getId())
                        .bankAccountNumber(b.getAccountNumber()).build())
                .collect(Collectors.toList());

    }
}
