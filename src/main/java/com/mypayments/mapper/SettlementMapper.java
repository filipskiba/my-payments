package com.mypayments.mapper;

import com.mypayments.domain.Dto.SettlementDto;
import com.mypayments.domain.Settlement;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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


    public Settlement mapToSettlement(final SettlementDto settlementDto) throws ContractorNotFoundException, SettlementNotFoundException {
        return new Settlement().builder()
                .id(settlementDto.getSettlementId())
                .document(settlementDto.getDocument())
                .contractor(contractorRepository.findById(settlementDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .dateOfIssue(settlementDto.getDateOfIssue())
                .dateOfPayment(settlementDto.getDateOfPayment())
                .amount(settlementDto.getAmount())
                .payments(paymentMapper.mapToPaymentsList(settlementDto.getPaymentDtoList())).build();

    }

    public SettlementDto mapToSettlementDto(final Settlement settlement) throws ContractorNotFoundException {
            return new SettlementDto().builder()
                    .settlementId(settlement.getId())
                    .document(settlement.getDocument())
                    .contractorName(settlement.getContractor().getContractorName())
                    .contractorId(settlement.getContractor().getId())
                    .dateOfIssue(settlement.getDateOfIssue())
                    .dateOfPayment(settlement.getDateOfPayment())
                    .amount(settlement.getAmount())
                    .paymentDtoList(paymentMapper.mapToPaymentsDtoList(settlement.getPayments()))
                    .paidAmount(settlementService.getPaymentsAmount(settlement))
                    .isPaid(settlementService.isPaid(settlement))
                    .build();
    }

    public List<Settlement> mapToSettlementsList(final List<SettlementDto> settlementsDto) throws ContractorNotFoundException, SettlementNotFoundException {
        if (settlementsDto != null) {
            List<Settlement> settlements = new ArrayList<>();
            for (SettlementDto s : settlementsDto) {
                if (contractorRepository.findById(s.getContractorId()).isPresent()) {
                    settlements.add(new Settlement().builder()
                            .id(s.getSettlementId())
                            .document(s.getDocument())
                            .contractor(contractorRepository.findById(s.getContractorId()).get())
                            .dateOfIssue(s.getDateOfIssue())
                            .dateOfPayment(s.getDateOfPayment())
                            .amount(s.getAmount())
                            .payments(paymentMapper.mapToPaymentsList(s.getPaymentDtoList())).build()
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
                        .contractorName(s.getContractor().getContractorName())
                        .dateOfIssue(s.getDateOfIssue())
                        .dateOfPayment(s.getDateOfPayment())
                        .amount(s.getAmount())
                        .paymentDtoList(paymentMapper.mapToPaymentsDtoList(s.getPayments()))
                        .paidAmount(settlementService.getPaymentsAmount(s))
                        .isPaid(settlementService.isPaid(s))
                        .build()).collect(Collectors.toList());
    }
}
