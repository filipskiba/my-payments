package com.mypayments.mapper;

import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.domain.Payment;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.ContractorRepository;
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
    ContractorRepository contractorRepository;

    @Autowired
    SettlementsRepository settlementsRepository;

    public Payment mapToPayment(final PaymentDto paymentDto) throws ContractorNotFoundException, SettlementNotFoundException {
        return new Payment().builder()
                .paymentType(paymentDto.getPaymentType())
                .contractor(contractorRepository.findById(paymentDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .dateOfTranfer(LocalDate.parse(paymentDto.getDateOfTransfer()))
                .amount(paymentDto.getAmount())
                .currency(paymentDto.getCurrency())
                .vat(paymentDto.getVat())
                .isSplitPayment(paymentDto.getIsSplitPayment())
                .settlement(settlementsRepository.findById(paymentDto.getSettlementId()).orElseThrow(SettlementNotFoundException::new)).build();
    }

    public PaymentDto mapToPaymentDto(final Payment payment) {
        return new PaymentDto().builder()
                .paymentType(payment.getPaymentType())
                .contractorId(payment.getContractor().getId())
                .dateOfTransfer(payment.getDateOfTranfer().toString())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .vat(payment.getVat())
                .isSplitPayment(payment.getIsSplitPayment())
                .settlementId(payment.getSettlement().getId()).build();
    }

    public List<Payment> mapToPaymentsList(final List<PaymentDto> paymentsDto) throws ContractorNotFoundException, SettlementNotFoundException {
        List<Payment> payments = new ArrayList<>();
        for (PaymentDto p : paymentsDto) {
            if (settlementsRepository.findById(p.getSettlementId()).isPresent() && contractorRepository.findById(p.getContractorId()).isPresent()) {
                payments.add(new Payment().builder()
                        .paymentType(p.getPaymentType())
                        .contractor(contractorRepository.findById(p.getContractorId()).get())
                        .dateOfTranfer(LocalDate.parse(p.getDateOfTransfer()))
                        .amount(p.getAmount())
                        .currency(p.getCurrency())
                        .vat(p.getVat())
                        .isSplitPayment(p.getIsSplitPayment())
                        .settlement(settlementsRepository.findById(p.getSettlementId()).get()).build()
                );
            } else {
                if(!settlementsRepository.findById(p.getSettlementId()).isPresent()){
                    throw new ContractorNotFoundException();
                }
                else {
                    throw new SettlementNotFoundException();
                }
            }
        }
        return payments;
    }

    public List<PaymentDto> mapToPaymentsDtoList(final List<Payment> payments) {
        return payments.stream()
                .map(p -> new PaymentDto().builder()
                        .paymentType(p.getPaymentType())
                        .contractorId(p.getContractor().getId())
                        .dateOfTransfer(p.getDateOfTranfer().toString())
                        .amount(p.getAmount())
                        .currency(p.getCurrency())
                        .vat(p.getVat())
                        .isSplitPayment(p.getIsSplitPayment())
                        .settlementId(p.getSettlement().getId()).build()).collect(Collectors.toList());
    }
}
