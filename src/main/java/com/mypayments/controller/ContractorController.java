package com.mypayments.controller;

import com.mypayments.domain.Dto.ContractorDto;
import com.mypayments.exception.*;
import com.mypayments.mapper.ContractorMapper;
import com.mypayments.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ContractorController {

    @Autowired
    private ContractorService contractorService;

    @Autowired
    ContractorMapper contractorMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/contractors/{contractorId}")
    public ContractorDto getContractor(@PathVariable("contractorId") Long contractorId) throws ContractorNotFoundException {
        return contractorMapper.mapToContractorDto(contractorService.getContractorById(contractorId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contractors")
    public List<ContractorDto> getContractors() {
        return contractorMapper.mapToContractorsDtoList(contractorService.getAllContractors());
    }
    @RequestMapping(method = RequestMethod.GET, value = "/contractors/owners")
    public List<ContractorDto> getOwners() {
        return contractorMapper.mapToContractorsDtoList(contractorService.getAllOwners());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contractors")
    public void createContractor(@RequestBody ContractorDto contractorDto) throws SettlementNotFoundException, ContractorNotFoundException, DispositionNotFoundException, BankAccountNotFoundException {
        System.out.println(contractorDto);
        contractorService.saveContractor(contractorMapper.mapToContractor(contractorDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/contractors")
    public ContractorDto updateContractor(@RequestBody ContractorDto contractorDto) throws SettlementNotFoundException, ContractorNotFoundException, DispositionNotFoundException, BankAccountNotFoundException {
        return contractorMapper.mapToContractorDto(contractorService.updateContractor(contractorMapper.mapToContractor(contractorDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/contractors/{contractorId}")
    public void deleteContractor(@PathVariable("contractorId") Long contractorId) throws ContractorNotFoundException {
        contractorService.deleteContractorById(contractorId);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/contractors/status/{contractorId}")
    public Boolean getContractorStatus(@PathVariable("contractorId") Long contractorId) throws InvalidDataFormatException, EmptyDataException, ContractorNotFoundException {
       return contractorService.checkStatus(contractorId);
    }

}
