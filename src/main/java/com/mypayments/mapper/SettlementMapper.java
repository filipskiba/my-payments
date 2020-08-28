package com.mypayments.mapper;

import com.mypayments.domain.Dto.SettlementDto;
import com.mypayments.domain.Settlement;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.DispositionNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.BankAccountRepository;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SettlementMapper {

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private BankAccountRepository bankAccountRepository;


    public Settlement mapToSettlement(final SettlementDto settlementDto) throws ContractorNotFoundException, SettlementNotFoundException, DispositionNotFoundException, BankAccountNotFoundException {
        return new Settlement().builder()
                .id(settlementDto.getSettlementId())
                .document(settlementDto.getDocument())
                .contractor(contractorRepository.findById(settlementDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .dateOfIssue(LocalDate.parse(settlementDto.getDateOfIssue()))
                .dateOfPayment(LocalDate.parse(settlementDto.getDateOfPayment()))
                .amount(settlementDto.getAmount())
                .payments(paymentMapper.mapToPaymentsList(settlementDto.getPaymentDtoList()))
                .bankAccount(bankAccountRepository.findBankAccountByAccountNumber(settlementDto.getBankAccountNumber()).orElseThrow(BankAccountNotFoundException::new))
                .owner(contractorRepository.findById(settlementDto.getOwnerId()).orElseThrow(ContractorNotFoundException::new))
                .ownerBankAccount(bankAccountRepository.findBankAccountByAccountNumber(settlementDto.getOwnerBankAccountNumber()).orElseThrow(BankAccountNotFoundException::new))
                .build();

    }

    public SettlementDto mapToSettlementDto(final Settlement settlement) {
            return new SettlementDto().builder()
                    .settlementId(settlement.getId())
                    .document(settlement.getDocument())
                    .contractorName(settlement.getContractor().getContractorName())
                    .ownerName(settlement.getOwner().getContractorName())
                    .contractorId(settlement.getContractor().getId())
                    .ownerId(settlement.getOwner().getId())
                    .dateOfIssue(settlement.getDateOfIssue().toString())
                    .dateOfPayment(settlement.getDateOfPayment().toString())
                    .amount(settlement.getAmount())
                    .paymentDtoList(paymentMapper.mapToPaymentsDtoList(settlement.getPayments()))
                    .paidAmount(settlementService.getPaymentsAmount(settlement))
                    .isPaid(settlementService.isPaid(settlement))
                    .bankAccountNumber(settlement.getBankAccount().getAccountNumber())
                    .ownerBankAccountNumber(settlement.getOwnerBankAccount().getAccountNumber())
                    .build();
    }

    public List<Settlement> mapToSettlementsList(final List<SettlementDto> settlementsDto) throws ContractorNotFoundException, SettlementNotFoundException, DispositionNotFoundException {
        if (settlementsDto != null) {
            List<Settlement> settlements = new ArrayList<>();
            for (SettlementDto s : settlementsDto) {
                if (contractorRepository.findById(s.getContractorId()).isPresent()) {
                    settlements.add(new Settlement().builder()
                            .id(s.getSettlementId())
                            .document(s.getDocument())
                            .contractor(contractorRepository.findById(s.getContractorId()).get())
                            .owner(contractorRepository.findById(s.getOwnerId()).get())
                            .dateOfIssue(LocalDate.parse(s.getDateOfIssue()))
                            .dateOfPayment(LocalDate.parse(s.getDateOfPayment()))
                            .amount(s.getAmount())
                            .payments(paymentMapper.mapToPaymentsList(s.getPaymentDtoList()))
                            .bankAccount(bankAccountRepository.findBankAccountByAccountNumber(s.getBankAccountNumber()).get())
                            .ownerBankAccount(bankAccountRepository.findBankAccountByAccountNumber(s.getOwnerBankAccountNumber()).get())
                            .build()
                    );
                } else {
                    throw new ContractorNotFoundException();
                }
            }
            return settlements;
        } else return new ArrayList<>();
    }

    public List<SettlementDto> mapToSettlementDtoList(final List<Settlement> settlements) {
        return settlements.stream()
                .map(s -> new SettlementDto().builder()
                        .settlementId(s.getId())
                        .document(s.getDocument())
                        .contractorId(s.getContractor().getId())
                        .ownerId(s.getOwner().getId())
                        .contractorName(s.getContractor().getContractorName())
                        .ownerName(s.getOwner().getContractorName())
                        .ownerBankAccountNumber(s.getOwnerBankAccount().getAccountNumber())
                        .dateOfIssue(s.getDateOfIssue().toString())
                        .dateOfPayment(s.getDateOfPayment().toString())
                        .amount(s.getAmount())
                        .paymentDtoList(paymentMapper.mapToPaymentsDtoList(s.getPayments()))
                        .paidAmount(settlementService.getPaymentsAmount(s))
                        .isPaid(settlementService.isPaid(s))
                        .bankAccountNumber(s.getBankAccount().getAccountNumber())
                        .build()).collect(Collectors.toList());
    }
}
