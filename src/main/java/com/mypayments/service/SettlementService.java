package com.mypayments.service;

import com.mypayments.domain.Dto.SettlementDto;
import com.mypayments.domain.Payment;
import com.mypayments.domain.Settlement;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.SettlementsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettlementService.class);
    private static final String MESSAGE = "Can not find settlement with ID: ";

    @Autowired
    SettlementsRepository settlementsRepository;

    public List<Settlement> getAllSetlements() {
        return settlementsRepository.findAll();
    }

    public Settlement getSettlementById(Long settlementId) throws SettlementNotFoundException {
        if(settlementsRepository.findById(settlementId).isPresent()) {
            return settlementsRepository.findById(settlementId).get();
        }
        else {
            LOGGER.error(MESSAGE + settlementId);
            throw new SettlementNotFoundException();
        }
    }

    public Settlement saveSettlement(final Settlement settlement) {
        LOGGER.info("Successfully saved settlement");
        return settlementsRepository.save(settlement);
    }

    public Settlement updateSettlement(final Settlement settlement) throws SettlementNotFoundException {
        if(settlementsRepository.findById(settlement.getId()).isPresent()) {
            return settlementsRepository.save(settlement);
        }
        else {
            LOGGER.error(MESSAGE + settlement.getId());
            throw new SettlementNotFoundException();
        }
    }

    public void deleteSettlementById(final Long settlementId) throws SettlementNotFoundException {
        if(settlementsRepository.findById(settlementId).isPresent()) {
            settlementsRepository.deleteById(settlementId);
            LOGGER.info("Successfully deleted settlement with ID: " + settlementId);
        }
        else {
            LOGGER.error(MESSAGE + settlementId);
            throw new SettlementNotFoundException();
        }
    }

    public List<Settlement> getSettlementNotPaid(){
        List<Settlement> settlements = settlementsRepository.findAll();
        List<Settlement> resultList = new ArrayList<>();
        for (Settlement s: settlements) {
            if(!isPaid(s)){
                resultList.add(s);
            }
        }
        return resultList;
    }

    public List<Settlement> getSettlementPaid(){
        List<Settlement> settlements = settlementsRepository.findAll();
        List<Settlement> resultList = new ArrayList<>();
        for (Settlement s: settlements) {
            if(isPaid(s)){
                resultList.add(s);
            }
        }
        return resultList;
    }

    public BigDecimal getPaymentsAmount(Settlement settlement) {
        BigDecimal result = settlement.getPayments().stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }

    public Boolean isPaid(Settlement settlement) {
        MathContext mc = new MathContext(4);
        BigDecimal diff = settlement.getAmount().subtract(getPaymentsAmount(settlement), mc);
        if (diff.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        } else {
            return false;
        }
    }
}
