package com.mypayments.service;

import com.mypayments.domain.Disposition;
import com.mypayments.domain.Payment;
import com.mypayments.exception.DispositionNotFoundException;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.repository.DispositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DispositionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private DispositionRepository dispositionRepository;

    @Autowired
    private DispositionFileService dispositionFileService;

    public List<Disposition> getDispositionList(List<Long> idList) throws DispositionNotFoundException {
        List<Disposition> dispositionList = new ArrayList<>();
        for(Long id: idList){
          dispositionList.add(getDispositionById(id));
        }
        return dispositionList;
    }

    public String createDispositionFile(List<Long> idList) throws DispositionNotFoundException, InvalidDataFormatException {
        List<Disposition> dispositionList = getDispositionList(idList);
        for(Disposition d: dispositionList){
            d.setIsExecuted(true);
            updateDisposition(d);
        }
        return dispositionFileService.createDispositionFileFromList(dispositionList);
    }

    public List<Disposition> getAllDispositions() {
        return dispositionRepository.findAll();
    }

    public Disposition getDispositionById(final Long dispositionId) throws DispositionNotFoundException {
        if(dispositionRepository.findById(dispositionId).isPresent()){
            return dispositionRepository.findById(dispositionId).get();
        }
        else {
            LOGGER.error("Can not find disposition with ID: " + dispositionId);
            throw new DispositionNotFoundException();
        }
    }

    public Disposition saveDisposition(final Disposition disposition) {
        LOGGER.info("Successfully saved disposition");
        return dispositionRepository.save(disposition);
    }

    public Disposition updateDisposition(final Disposition disposition) throws DispositionNotFoundException {
        if(dispositionRepository.findById(disposition.getId()).isPresent()){
            return dispositionRepository.save(disposition);
        }
        else {
            LOGGER.error("Can not find disposition with ID: " + disposition.getId());
            throw new DispositionNotFoundException();
        }
    }

    public void deleteDispositionById(final Long dispositionId) throws DispositionNotFoundException {
        if(dispositionRepository.findById(dispositionId).isPresent()) {
            dispositionRepository.deleteById(dispositionId);
            LOGGER.info("Successfully deleted disposition with ID: " +dispositionId);
        }
        else {
            LOGGER.error("Can not find disposition with ID: " +dispositionId);
            throw new DispositionNotFoundException();
        }
    }

    public Disposition saveDispositionByPayment(final Payment payment){
        Disposition disposition = new Disposition().builder()
                .dateOfExecution(payment.getDateOfTranfer())
                .isExecuted(false)
                .title(payment.getSettlement().getDocument())
                .amount(payment.getAmount())
                .owner(payment.getOwner())
                .bankAccount(payment.getBankAccount())
                .title(payment.getDocument())
                .ownerBankAccount(payment.getOwnerBankAccount())
                .contractor(payment.getContractor())
                .build();
        dispositionRepository.save(disposition);
        return disposition;
    }
}
