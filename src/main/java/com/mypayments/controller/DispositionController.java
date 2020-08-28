package com.mypayments.controller;

import com.mypayments.domain.Dto.DispositionDto;
import com.mypayments.exception.*;
import com.mypayments.mapper.DispositionMapper;
import com.mypayments.service.DispositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class DispositionController {

    @Autowired
    private DispositionService dispositionService;

    @Autowired
    private DispositionMapper dispositionMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/disposition/{dispositionId}")
    public DispositionDto getDisposition(@PathVariable("dispositionId") Long dispositionId) throws DispositionNotFoundException {
        return dispositionMapper.mapToDispositionDto(dispositionService.getDispositionById(dispositionId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/disposition")
    public List<DispositionDto> getDispositions() {
        return dispositionMapper.mapToDispositionDtoList(dispositionService.getAllDispositions());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/disposition")
    public void createDisposition(@RequestBody DispositionDto dispositionDto) throws SettlementNotFoundException, ContractorNotFoundException, BankAccountNotFoundException {
        dispositionService.saveDisposition(dispositionMapper.mapToDisposition(dispositionDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/disposition")
    public DispositionDto updateDisposition(@RequestBody DispositionDto dispositionDto) throws ContractorNotFoundException, SettlementNotFoundException, BankAccountNotFoundException, DispositionNotFoundException {
        return dispositionMapper.mapToDispositionDto(dispositionService.updateDisposition(dispositionMapper.mapToDisposition(dispositionDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/disposition/{dispositionId}")
    public void deleteDisposition(@PathVariable("dispositionId") Long dispositionId) throws DispositionNotFoundException {
        dispositionService.deleteDispositionById(dispositionId);
    }

}
