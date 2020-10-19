package com.mypayments.mapper;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dto.ContractorDto;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.DispositionNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.ContractorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractorMapper {

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private SettlementMapper settlementMapper;

    @Autowired
    private ContractorTypeRepository contractorTypeRepository;


    public Contractor mapToContractor(final ContractorDto contractorDto) throws ContractorNotFoundException, SettlementNotFoundException, DispositionNotFoundException, BankAccountNotFoundException {
        return new Contractor().builder()
                .id(contractorDto.getContractorId())
                .contractorName(contractorDto.getContractorName())
                .nipId(contractorDto.getNipId())
                .address(contractorDto.getAddress())
                .city(contractorDto.getCity())
                .zipCode(contractorDto.getZipCode())
                .contractorType(contractorTypeRepository.findByContractorTypeName(contractorDto.getContractorType()).orElseThrow(ContractorNotFoundException::new))
                .bankAccounts(bankAccountMapper.mapToBankAccountsList(contractorDto.getBankAccountDtos()))
                .payments(paymentMapper.mapToPaymentsList(contractorDto.getPaymentDtos()))
                .settlements(settlementMapper.mapToSettlementsList(contractorDto.getSettlementDtos()))
                .statuses(statusMapper.mapToStatusesList(contractorDto.getStatusDtos()))
                .build();
    }
    public ContractorDto mapToContractorDto(final Contractor contractor) {
        return new ContractorDto().builder()
                .contractorId(contractor.getId())
                .contractorName(contractor.getContractorName())
                .nipId(contractor.getNipId())
                .address(contractor.getAddress())
                .city(contractor.getCity())
                .zipCode(contractor.getZipCode())
                .contractorType(contractor.getContractorType().getContractorTypeName())
                .bankAccountDtos(bankAccountMapper.mapToBankAccountsDtoList(contractor.getBankAccounts()))
                .paymentDtos(paymentMapper.mapToPaymentsDtoList(contractor.getPayments()))
                .settlementDtos(settlementMapper.mapToSettlementDtoList(contractor.getSettlements()))
                .statusDtos(statusMapper.mapToStatusesDtoList(contractor.getStatuses()))
                .build();
    }
    public List<ContractorDto> mapToContractorsDtoList(final List<Contractor> contractors){
        return contractors.stream()
                .map(c-> new ContractorDto().builder()
                .contractorId(c.getId())
                .contractorName(c.getContractorName())
                .nipId(c.getNipId())
                .address(c.getAddress())
                .city(c.getCity())
                .zipCode(c.getZipCode())
                .contractorType(c.getContractorType().getContractorTypeName())
                .bankAccountDtos(bankAccountMapper.mapToBankAccountsDtoList(c.getBankAccounts()))
                .paymentDtos(paymentMapper.mapToPaymentsDtoList(c.getPayments()))
                .settlementDtos(settlementMapper.mapToSettlementDtoList(c.getSettlements()))
                .statusDtos(statusMapper.mapToStatusesDtoList(c.getStatuses())).build()
                ).collect(Collectors.toList());
    }

}
