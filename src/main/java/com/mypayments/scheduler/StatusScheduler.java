package com.mypayments.scheduler;

import com.mypayments.exception.EmptyDataException;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.gov.GovFacade;
import com.mypayments.mapper.ContractorMapper;
import com.mypayments.service.ContractorService;
import com.mypayments.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class StatusScheduler {

    @Autowired
    private ContractorService contractorService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private GovFacade govFacade;


    @Scheduled(cron = "0 0 12 * * *")
    //@Scheduled(fixedDelay = 10000)
    @Transactional
    public void createDailyReport() {
        statusService.createStatusForAllContractors();
    }


}
