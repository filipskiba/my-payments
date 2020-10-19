package com.mypayments.mapper;

import com.mypayments.domain.Disposition;
import com.mypayments.domain.Dto.DispositionDto;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.DispositionNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.BankAccountRepository;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.repository.SettlementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DispositionMapper {

    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private SettlementsRepository settlementsRepository;

    public Disposition mapToDisposition(final DispositionDto dispositionDto) throws ContractorNotFoundException, BankAccountNotFoundException, SettlementNotFoundException, DispositionNotFoundException {
        return new Disposition().builder()
                .id(dispositionDto.getDispositionId())
                .dateOfExecution(LocalDate.parse(dispositionDto.getDateOfExecution()))
                .isExecuted(dispositionDto.getIsExecuted())
                .title(dispositionDto.getTitle())
                .amount(dispositionDto.getAmount())
                .contractor(contractorRepository.findById(dispositionDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .bankAccount(bankAccountRepository.findBankAccountByAccountNumber(dispositionDto.getContractorBankAccountNumber()).orElseThrow(BankAccountNotFoundException::new))
                .payments(paymentMapper.mapToPaymentsList(dispositionDto.getPaymentDtoList()))
                .settlement(settlementsRepository.findById(dispositionDto.getSettlementDtoId()).orElseThrow(SettlementNotFoundException::new))
                .owner(contractorRepository.findById(dispositionDto.getOwnerId()).orElseThrow(ContractorNotFoundException::new))
                .ownerBankAccount(bankAccountRepository.findBankAccountByAccountNumber(dispositionDto.getOwnerBankAccountNumber()).orElseThrow(BankAccountNotFoundException::new))
                .vatAmount(dispositionDto.getVatAmount())
                .build();

    }

    public DispositionDto mapToDispositionDto(final Disposition disposition) {
        return new DispositionDto().builder()
                .dispositionId(disposition.getId())
                .dateOfExecution(disposition.getDateOfExecution().toString())
                .isExecuted(disposition.getIsExecuted())
                .title(disposition.getTitle())
                .ownerName(disposition.getOwner().getContractorName())
                .ownerBankAccountNumber(disposition.getOwnerBankAccount().getAccountNumber())
                .ownerId(disposition.getOwner().getId())
                .amount(disposition.getAmount())
                .contractorId(disposition.getContractor().getId())
                .contractorName(disposition.getContractor().getContractorName())
                .contractorBankAccountNumber(disposition.getBankAccount().getAccountNumber())
                .settlementDtoId(disposition.getSettlement().getId())
                .vatAmount(disposition.getVatAmount())
                .paymentDtoList(paymentMapper.mapToPaymentsDtoList(disposition.getPayments()))
                .build();
    }

    public List<Disposition> mapToDispositionList(final List<DispositionDto> dispositionDtoList) throws ContractorNotFoundException, SettlementNotFoundException, BankAccountNotFoundException, DispositionNotFoundException {
        if (dispositionDtoList != null) {
            List<Disposition> dispositions = new ArrayList<>();
            for (DispositionDto s : dispositionDtoList) {
                if (contractorRepository.findById(s.getContractorId()).isPresent()) {
                    dispositions.add(new Disposition().builder()
                            .id(s.getDispositionId())
                            .dateOfExecution(LocalDate.parse(s.getDateOfExecution()))
                            .isExecuted(s.getIsExecuted())
                            .title(s.getTitle())
                            .amount(s.getAmount())
                            .contractor(contractorRepository.findById(s.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                            .bankAccount(bankAccountRepository.findBankAccountByAccountNumber(s.getContractorBankAccountNumber()).orElseThrow(BankAccountNotFoundException::new))
                            .payments(paymentMapper.mapToPaymentsList(s.getPaymentDtoList()))
                            .settlement(settlementsRepository.findById(s.getSettlementDtoId()).orElseThrow(SettlementNotFoundException::new))
                            .owner(contractorRepository.findById(s.getOwnerId()).get())
                            .vatAmount(s.getVatAmount())
                            .ownerBankAccount(bankAccountRepository.findBankAccountByAccountNumber(s.getOwnerBankAccountNumber()).get())
                            .build()
                    );
                } else {
                    throw new ContractorNotFoundException();
                }
            }
            return dispositions;
        } else return new ArrayList<>();
    }

    public List<DispositionDto> mapToDispositionDtoList(final List<Disposition> dispositions) {
        return dispositions.stream()
                .map(s -> new DispositionDto().builder()
                        .dispositionId(s.getId())
                        .dateOfExecution(s.getDateOfExecution().toString())
                        .isExecuted(s.getIsExecuted())
                        .ownerId(s.getOwner().getId())
                        .ownerName(s.getOwner().getContractorName())
                        .ownerBankAccountNumber(s.getOwnerBankAccount().getAccountNumber())
                        .title(s.getTitle())
                        .amount(s.getAmount())
                        .contractorId(s.getContractor().getId())
                        .contractorName(s.getContractor().getContractorName())
                        .contractorBankAccountNumber(s.getBankAccount().getAccountNumber())
                        .paymentDtoList(paymentMapper.mapToPaymentsDtoList(s.getPayments()))
                        .settlementDtoId(s.getSettlement().getId())
                        .vatAmount(s.getVatAmount())
                        .build()).collect(Collectors.toList());
    }

}
