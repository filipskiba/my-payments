package com.mypayments.mapper;

import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.domain.Payment;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.repository.SettlementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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


    public Payment mapToPayment(final PaymentDto paymentDto) throws ContractorNotFoundException, SettlementNotFoundException{
        return new Payment().builder()
                .id(paymentDto.getPaymentId())
                .contractor(contractorRepository.findById(paymentDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .dateOfTranfer(paymentDto.getDateOfTransfer())
                .amount(paymentDto.getAmount())
                .settlement(settlementsRepository.findById(paymentDto.getSettlementId()).orElseThrow(SettlementNotFoundException::new)).build();
    }

    public PaymentDto mapToPaymentDto(final Payment payment) {
        return new PaymentDto().builder()
                .paymentId(payment.getId())
                .contractorId(payment.getContractor().getId())
                .contractorName(payment.getContractor().getContractorName())
                .dateOfTransfer(payment.getDateOfTranfer())
                .amount(payment.getAmount())
                .settlementId(payment.getSettlement().getId()).build();
    }

    public List<Payment> mapToPaymentsList(final List<PaymentDto> paymentsDto) throws ContractorNotFoundException, SettlementNotFoundException {
        if(paymentsDto!=null) {
            List<Payment> payments = new ArrayList<>();
            for (PaymentDto p : paymentsDto) {
                if (settlementsRepository.findById(p.getSettlementId()).isPresent() && contractorRepository.findById(p.getContractorId()).isPresent()) {
                    payments.add(new Payment().builder()
                            .id(p.getPaymentId())
                            .contractor(contractorRepository.findById(p.getContractorId()).get())
                            .dateOfTranfer(p.getDateOfTransfer())
                            .amount(p.getAmount())
                            .settlement(settlementsRepository.findById(p.getSettlementId()).get()).build()
                    );
                } else {
                    if (!settlementsRepository.findById(p.getSettlementId()).isPresent()) {
                        throw new ContractorNotFoundException();
                    } else {
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
                        .dateOfTransfer(p.getDateOfTranfer())
                        .amount(p.getAmount())
                        .settlementId(p.getSettlement().getId()).build()).collect(Collectors.toList());
    }
}
