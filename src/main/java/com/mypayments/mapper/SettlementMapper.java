package com.mypayments.mapper;

import com.mypayments.domain.Dto.SettlementDto;
import com.mypayments.domain.Settlement;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SettlementMapper {

    @Autowired
    ContractorRepository contractorRepository;

    @Autowired
    PaymentMapper paymentMapper;

    public Settlement mapToSettlement(final SettlementDto settlementDto) throws ContractorNotFoundException, SettlementNotFoundException {
        return new Settlement().builder()
                .settementType(settlementDto.getSettlementType())
                .document(settlementDto.getDocument())
                .contractor(contractorRepository.findById(settlementDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .dateOfIssue(LocalDate.parse(settlementDto.getDateOfIssue()))
                .dateOfPayment(LocalDate.parse(settlementDto.getDateOfPayment()))
                .amount(settlementDto.getAmount())
                .currency(settlementDto.getCurrency())
                .vat(settlementDto.getVat())
                .isSplitPayment(settlementDto.getIsSplitPayment())
                .payments(paymentMapper.mapToPaymentsList(settlementDto.getPaymentDtoList())).build();

    }

    public SettlementDto mapToSettlementDto(final Settlement settlement) {
        return new SettlementDto().builder()
                .settlementType(settlement.getSettementType())
                .document(settlement.getDocument())
                .contractorId(settlement.getContractor().getId())
                .dateOfIssue(settlement.getDateOfIssue().toString())
                .dateOfPayment(settlement.getDateOfPayment().toString())
                .amount(settlement.getAmount())
                .currency(settlement.getCurrency())
                .vat(settlement.getVat())
                .isSplitPayment(settlement.getIsSplitPayment())
                .paymentDtoList(paymentMapper.mapToPaymentsDtoList(settlement.getPayments())).build();
    }

    public List<Settlement> mapToSettlementsList(final List<SettlementDto> settlementsDto) throws ContractorNotFoundException, SettlementNotFoundException {
        List<Settlement> settlements = new ArrayList<>();
        for (SettlementDto s : settlementsDto) {
            if (contractorRepository.findById(s.getContractorId()).isPresent()) {
                settlements.add(new Settlement().builder()
                        .settementType(s.getSettlementType())
                        .document(s.getDocument())
                        .contractor(contractorRepository.findById(s.getContractorId()).get())
                        .dateOfIssue(LocalDate.parse(s.getDateOfIssue()))
                        .dateOfPayment(LocalDate.parse(s.getDateOfPayment()))
                        .amount(s.getAmount())
                        .currency(s.getCurrency())
                        .vat(s.getVat())
                        .isSplitPayment(s.getIsSplitPayment())
                        .payments(paymentMapper.mapToPaymentsList(s.getPaymentDtoList())).build()
                );
            } else {
                throw new ContractorNotFoundException();
            }
        }
        return settlements;
    }

    public List<SettlementDto> mapToSettlementDtoList(final List<Settlement> settlements) {
        return settlements.stream()
                .map(s -> new SettlementDto().builder()
                        .settlementType(s.getSettementType())
                        .document(s.getDocument())
                        .contractorId(s.getContractor().getId())
                        .dateOfIssue(s.getDateOfIssue().toString())
                        .dateOfPayment(s.getDateOfPayment().toString())
                        .amount(s.getAmount())
                        .currency(s.getCurrency())
                        .vat(s.getVat())
                        .isSplitPayment(s.getIsSplitPayment())
                        .paymentDtoList(paymentMapper.mapToPaymentsDtoList(s.getPayments())).build()).collect(Collectors.toList());
    }
}
