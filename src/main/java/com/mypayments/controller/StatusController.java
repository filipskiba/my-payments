package com.mypayments.controller;

import com.mypayments.domain.Dto.StatusDto;
import com.mypayments.domain.Status;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.EmptyDataException;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.exception.StatusNotFoundException;
import com.mypayments.mapper.StatusMapper;
import com.mypayments.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private StatusMapper statusMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/statuses/{statusId}")
    public StatusDto getStatus(@PathVariable("statusId") Long statusId) throws StatusNotFoundException {
        return statusMapper.mapToStatusDto(statusService.getStatusById(statusId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/statuses")
    public List<StatusDto> getAllStatuses() {
        return statusMapper.mapToStatusesDtoList(statusService.getAllStatuses());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/statuses/{contractorId}")
    public StatusDto createStatusForContractor(@PathVariable Long contractorId) throws ContractorNotFoundException, InvalidDataFormatException, EmptyDataException {
       return statusMapper.mapToStatusDto(statusService.createStatusForContractor(contractorId));
    }
    @RequestMapping(method = RequestMethod.GET, value = "/statuses/all")
    public List<StatusDto> createStatusForAllContractors() throws ContractorNotFoundException, InvalidDataFormatException, EmptyDataException {
        return statusMapper.mapToStatusesDtoList(statusService.createStatusForAllContractors());
    }

   /* @RequestMapping(method = RequestMethod.PUT, value = "/statuses")
    public StatusDto updateStatus(@RequestBody StatusDto statusDto) throws ContractorNotFoundException, StatusNotFoundException {
        return statusMapper.mapToStatusDto(statusService.updateStatus(statusMapper.mapToStatus(statusDto)));
    }*/

    @RequestMapping(method = RequestMethod.DELETE, value = "/statuses/{statusId}")
    public void deleteStatus(@PathVariable("statusId") Long statusId) throws StatusNotFoundException {
        statusService.deleteStatusById(statusId);
    }

}
