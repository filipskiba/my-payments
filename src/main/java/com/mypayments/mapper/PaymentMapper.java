package com.mypayments.mapper;

import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.domain.Payment;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.DispositionNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.BankAccountRepository;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.repository.DispositionRepository;
import com.mypayments.repository.SettlementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private SettlementsRepository settlementsRepository;

    @Autowired
    private DispositionRepository dispositionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;


    public Payment mapToPayment(final PaymentDto paymentDto) throws ContractorNotFoundException, SettlementNotFoundException, DispositionNotFoundException, BankAccountNotFoundException {
        return new Payment().builder()
                .id(paymentDto.getPaymentId())
                .contractor(contractorRepository.findById(paymentDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .dateOfTranfer(LocalDate.parse(paymentDto.getDateOfTransfer()))
                .amount(paymentDto.getAmount())
                .document(paymentDto.getDocument())
                .settlement(settlementsRepository.findById(paymentDto.getSettlementId()).orElseThrow(SettlementNotFoundException::new))
                .bankAccount(bankAccountRepository.findBankAccountByAccountNumber(paymentDto.getBankAccountNumber()).orElseThrow(BankAccountNotFoundException::new))
                .owner(contractorRepository.findById(paymentDto.getOwnerId()).orElseThrow(ContractorNotFoundException::new))
                .ownerBankAccount(bankAccountRepository.findBankAccountByAccountNumber(paymentDto.getOwnerBankAccountNumber()).orElseThrow(BankAccountNotFoundException::new))
                .vatAmount(paymentDto.getVatAmount())
              //  .disposition(dispositionRepository.findById(paymentDto.getDispositionId()).orElseThrow(DispositionNotFoundException::new))
                .build();
    }

    public PaymentDto mapToPaymentDto(final Payment payment) {
        return new PaymentDto().builder()
                .paymentId(payment.getId())
                .contractorId(payment.getContractor().getId())
                .contractorName(payment.getContractor().getContractorName())
                .dateOfTransfer(payment.getDateOfTranfer().toString())
                .amount(payment.getAmount())
                .document(payment.getDocument())
                .ownerName(payment.getOwner().getContractorName())
                .settlementId(payment.getSettlement().getId())
                .dispositionId(payment.getDisposition().getId())
                .bankAccountNumber(payment.getBankAccount().getAccountNumber())
                .bankAccountNumber(payment.getBankAccount().getAccountNumber())
                .ownerBankAccountNumber(payment.getOwnerBankAccount().getAccountNumber())
                .vatAmount(payment.getVatAmount())
                .build();
    }

    public List<Payment> mapToPaymentsList(final List<PaymentDto> paymentsDto) throws ContractorNotFoundException, SettlementNotFoundException, DispositionNotFoundException {
        if(paymentsDto!=null) {
            List<Payment> payments = new ArrayList<>();
            for (PaymentDto p : paymentsDto) {
                if (settlementsRepository.findById(p.getSettlementId()).isPresent() && contractorRepository.findById(p.getContractorId()).isPresent() && dispositionRepository.findById(p.getDispositionId()).isPresent()) {
                    payments.add(new Payment().builder()
                            .id(p.getPaymentId())
                            .contractor(contractorRepository.findById(p.getContractorId()).get())
                            .dateOfTranfer(LocalDate.parse(p.getDateOfTransfer()))
                            .amount(p.getAmount())
                            .document(p.getDocument())
                            .settlement(settlementsRepository.findById(p.getSettlementId()).get())
                            .disposition(dispositionRepository.findById(p.getDispositionId()).get())
                            .bankAccount(bankAccountRepository.findBankAccountByAccountNumber(p.getBankAccountNumber()).get())
                            .bankAccount(bankAccountRepository.findBankAccountByAccountNumber(p.getBankAccountNumber()).get())
                            .ownerBankAccount(bankAccountRepository.findBankAccountByAccountNumber(p.getOwnerBankAccountNumber()).get())
                            .vatAmount(p.getVatAmount())
                            .build()
                    );
                } else {
                    if (!settlementsRepository.findById(p.getSettlementId()).isPresent()) {
                        throw new ContractorNotFoundException();
                    }
                    else if(!dispositionRepository.findById(p.getDispositionId()).isPresent()){
                        throw new DispositionNotFoundException();
                    }
                    else {
                        throw new SettlementNotFoundException();
                    }
                }
            }
            return payments;
        }
        else return new ArrayList<>();
    }

    public List<PaymentDto> mapToPaymentsDtoList(final List<Payment> payments) {
        return payments.stream()
                .map(p -> new PaymentDto().builder()
                        .paymentId(p.getId())
                        .contractorId(p.getContractor().getId())
                        .contractorName(p.getContractor().getContractorName())
                        .ownerId(p.getOwner().getId())
                        .document(p.getDocument())
                        .ownerBankAccountNumber(p.getOwnerBankAccount().getAccountNumber())
                        .ownerName(p.getOwner().getContractorName())
                        .dateOfTransfer(p.getDateOfTranfer().toString())
                        .amount(p.getAmount())
                        .settlementId(p.getSettlement().getId())
                        .dispositionId(p.getDisposition().getId())
                        .bankAccountNumber(p.getBankAccount().getAccountNumber())
                        .vatAmount(p.getVatAmount())
                        .build())
                        .collect(Collectors.toList());
    }
}
