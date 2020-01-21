package com.mypayments.mapper;


import com.mypayments.domain.Dto.StatusDto;
import com.mypayments.domain.Status;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.repository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatusMapper {

    @Autowired
    ContractorRepository contractorRepository;

    public Status mapToStatus(final StatusDto statusDto) throws ContractorNotFoundException {
        return new Status().builder()
                .contractor(contractorRepository.findById(statusDto.getContractorId()).orElseThrow(ContractorNotFoundException::new))
                .isContractorOnWL(statusDto.getIsContractorOnWL())
                .isBankAccountOnWL(statusDto.getIsBankAccountOnWL())
                .statusDate(LocalDate.parse(statusDto.getStatusDate())).build();
    }

    public StatusDto mapToStatusDto(final Status status) {
        return new StatusDto().builder()
                .contractorId(status.getContractor().getId())
                .isContractorOnWL(status.getIsContractorOnWL())
                .isBankAccountOnWL(status.getIsBankAccountOnWL())
                .statusDate(status.getStatusDate().toString()).build();
    }

    public List<Status> mapToStatusesList(final List<StatusDto> statusesDto) throws ContractorNotFoundException {
        List<Status> statuses = new ArrayList<>();
        for (StatusDto s : statusesDto) {
            if (contractorRepository.findById(s.getContractorId()).isPresent()) {
                statuses.add(new Status().builder()
                        .contractor(contractorRepository.findById(s.getContractorId()).get())
                        .isContractorOnWL(s.getIsContractorOnWL())
                        .isBankAccountOnWL(s.getIsBankAccountOnWL())
                        .statusDate(LocalDate.parse(s.getStatusDate())).build()
                );
            } else {
                throw new ContractorNotFoundException();
            }
        }
        return statuses;
    }

    public List<StatusDto> mapToStatusesDtoList(final List<Status> statuses) {
        return statuses.stream()
                .map(s -> new StatusDto().builder()
                        .contractorId(s.getContractor().getId())
                        .isContractorOnWL(s.getIsContractorOnWL())
                        .isBankAccountOnWL(s.getIsBankAccountOnWL())
                        .statusDate(s.getStatusDate().toString()).build()).collect(Collectors.toList());
    }
}
