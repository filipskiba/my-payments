package com.mypayments.mapper;


import com.mypayments.domain.Dto.GovSubjectDto;
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
                .statusDate(LocalDate.parse(statusDto.getStatusDate())).build();
    }

    public StatusDto mapToStatusDto(final Status status) {
        return new StatusDto().builder()
                .statusId(status.getId())
                .contractorName(status.getContractor().getContractorName())
                .contractorId(status.getContractor().getId())
                .isContractorOnWL(status.getIsContractorOnWL())
                .statusDate(status.getStatusDate().toString()).build();
    }

    public List<Status> mapToStatusesList(final List<StatusDto> statusesDto) throws ContractorNotFoundException {
        if (statusesDto != null) {
            List<Status> statuses = new ArrayList<>();
            for (StatusDto s : statusesDto) {
                if (contractorRepository.findById(s.getContractorId()).isPresent()) {
                    statuses.add(new Status().builder()
                            .contractor(contractorRepository.findById(s.getContractorId()).get())
                            .isContractorOnWL(s.getIsContractorOnWL())
                            .statusDate(LocalDate.parse(s.getStatusDate())).build()
                    );
                } else {
                    throw new ContractorNotFoundException();
                }
            }
            return statuses;
        } else return new ArrayList<>();
    }

    public List<StatusDto> mapToStatusesDtoList(final List<Status> statuses) {
        return statuses.stream()
                .map(s -> new StatusDto().builder()
                        .statusId(s.getId())
                        .contractorName(s.getContractor().getContractorName())
                        .contractorId(s.getContractor().getId())
                        .isContractorOnWL(s.getIsContractorOnWL())
                        .statusDate(s.getStatusDate().toString()).build()).collect(Collectors.toList());
    }

    public Status mapSubjectToStatus(final GovSubjectDto govSubjectDto) {
        if (govSubjectDto.getResultDto().getAccountAssigned().equals("TAK")) {
            return new Status().builder()
                    .statusDate(LocalDate.now())
                    .isContractorOnWL(true).build();
        } else {
            return new Status().builder()
                    .statusDate(LocalDate.now())
                    .isContractorOnWL(false).build();
        }
    }
}
