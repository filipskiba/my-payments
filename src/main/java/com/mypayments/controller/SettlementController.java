package com.mypayments.controller;

import com.mypayments.domain.Dto.SettlementDto;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.mapper.SettlementMapper;
import com.mypayments.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private SettlementMapper settlementMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/settlements/{settlementId}")
    public SettlementDto getSettlement(@PathVariable("settlementId") Long settlementId) throws SettlementNotFoundException, ContractorNotFoundException {
        return settlementMapper.mapToSettlementDto(settlementService.getSettlementById(settlementId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/settlements")
    public List<SettlementDto> getAllSettlements() {
        return settlementMapper.mapToSettlementDtoList(settlementService.getAllSetlements());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/settlements")
    public void createSettlement(@RequestBody SettlementDto settlementDto) throws ContractorNotFoundException, SettlementNotFoundException {
        settlementService.saveSettlement(settlementMapper.mapToSettlement(settlementDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/settlements")
    public SettlementDto updateSettlement(@RequestBody SettlementDto settlementDto) throws ContractorNotFoundException, SettlementNotFoundException {
        return settlementMapper.mapToSettlementDto(settlementService.updateSettlement(settlementMapper.mapToSettlement(settlementDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/settlements/{settlementId}")
    public void deleteSettlement(@PathVariable("settlementId") Long settlementId) throws SettlementNotFoundException {
        settlementService.deleteSettlementById(settlementId);
    }

}
