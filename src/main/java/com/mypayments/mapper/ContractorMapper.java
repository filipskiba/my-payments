package com.mypayments.mapper;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dto.ContractorDto;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractorMapper {

    @Autowired
    BankAccountMapper bankAccountMapper;

    @Autowired
    PaymentMapper paymentMapper;

    @Autowired
    StatusMapper statusMapper;

    @Autowired
    SettlementMapper settlementMapper;


    public Contractor mapToContractor(final ContractorDto contractorDto) throws ContractorNotFoundException, SettlementNotFoundException {
        return new Contractor().builder()
                .contractorName(contractorDto.getContractorName())
                .nipId(contractorDto.getNipId())
                .peselId(contractorDto.getPeselId())
                .regonId(contractorDto.getRegonId())
                .bankAccounts(bankAccountMapper.mapToBankAccountsList(contractorDto.getBankAccountDtos()))
                .payments(paymentMapper.mapToPaymentsList(contractorDto.getPaymentDtos()))
                .settlements(settlementMapper.mapToSettlementsList(contractorDto.getSettlementDtos()))
                .statuses(statusMapper.mapToStatusesList(contractorDto.getStatusDtos())).build();
    }
    public ContractorDto mapToContractorDto(final Contractor contractor) {
        return new ContractorDto().builder()
                .contractorName(contractor.getContractorName())
                .nipId(contractor.getNipId())
                .peselId(contractor.getPeselId())
                .regonId(contractor.getRegonId())
                .bankAccountDtos(bankAccountMapper.mapToBankAccountsDtoList(contractor.getBankAccounts()))
                .paymentDtos(paymentMapper.mapToPaymentsDtoList(contractor.getPayments()))
                .settlementDtos(settlementMapper.mapToSettlementDtoList(contractor.getSettlements()))
                .statusDtos(statusMapper.mapToStatusesDtoList(contractor.getStatuses())).build();
    }
    public List<ContractorDto> mapToContractorsDtoList(final List<Contractor> contractors){
        return contractors.stream()
                .map(c-> new ContractorDto().builder()
                .contractorName(c.getContractorName())
                .nipId(c.getNipId())
                .peselId(c.getPeselId())
                .regonId(c.getRegonId())
                .bankAccountDtos(bankAccountMapper.mapToBankAccountsDtoList(c.getBankAccounts()))
                .paymentDtos(paymentMapper.mapToPaymentsDtoList(c.getPayments()))
                .settlementDtos(settlementMapper.mapToSettlementDtoList(c.getSettlements()))
                .statusDtos(statusMapper.mapToStatusesDtoList(c.getStatuses())).build()
                ).collect(Collectors.toList());
    }

}
