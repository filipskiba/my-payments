package com.mypayments.service;

import com.mypayments.domain.Disposition;
import com.mypayments.domain.Payment;
import com.mypayments.domain.Settlement;
import com.mypayments.exception.DispositionNotFoundException;
import com.mypayments.exception.InvalidDataFormatException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.DispositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private PaymentService paymentService;

    public List<Disposition> getDispositionList(List<Long> idList) throws DispositionNotFoundException {
        List<Disposition> dispositionList = new ArrayList<>();
        for (Long id : idList) {
            dispositionList.add(getDispositionById(id));
        }
        return dispositionList;
    }

    public String createDispositionFile(List<Long> idList) throws DispositionNotFoundException, InvalidDataFormatException {
        List<Disposition> dispositionList = getDispositionList(idList);
        for (Disposition d : dispositionList) {
            d.setIsExecuted(true);
            updateDisposition(d);
            if(d.getPayments().size()==0) {
                Payment payment = new Payment().builder()
                        .contractor(d.getContractor())
                        .document(d.getTitle())
                        .owner(d.getOwner())
                        .ownerBankAccount(d.getOwnerBankAccount())
                        .dateOfTranfer(d.getDateOfExecution())
                        .amount(d.getAmount())
                        .vatAmount(d.getVatAmount())
                        .bankAccount(d.getBankAccount())
                        .settlement(d.getSettlement())
                        .disposition(d)
                        .build();
                paymentService.savePayment(payment);
                d.getPayments().add(payment);
                dispositionRepository.save(d);
            }
        }
        return dispositionFileService.createDispositionFileFromList(dispositionList);
    }

    public List<Disposition> getAllDispositions() {
        return dispositionRepository.findAll();
    }

    public Disposition getDispositionById(final Long dispositionId) throws DispositionNotFoundException {
        if (dispositionRepository.findById(dispositionId).isPresent()) {
            return dispositionRepository.findById(dispositionId).get();
        } else {
            LOGGER.error("Can not find disposition with ID: " + dispositionId);
            throw new DispositionNotFoundException();
        }
    }

    public Disposition saveDisposition(final Disposition disposition) {
        LOGGER.info("Successfully saved disposition");
        return dispositionRepository.save(disposition);
    }

    public Disposition updateDisposition(final Disposition disposition) throws DispositionNotFoundException {
        if (dispositionRepository.findById(disposition.getId()).isPresent()) {
            return dispositionRepository.save(disposition);
        } else {
            LOGGER.error("Can not find disposition with ID: " + disposition.getId());
            throw new DispositionNotFoundException();
        }
    }

    public void deleteDispositionById(final Long dispositionId) throws DispositionNotFoundException {
        if (dispositionRepository.findById(dispositionId).isPresent()) {
            dispositionRepository.deleteById(dispositionId);
            LOGGER.info("Successfully deleted disposition with ID: " + dispositionId);
        } else {
            LOGGER.error("Can not find disposition with ID: " + dispositionId);
            throw new DispositionNotFoundException();
        }
    }

    public void saveDispositionsList(List<Disposition> dispositions) {
        dispositions.stream().forEach(s -> saveDisposition(s));
    }


    public List<Disposition> createDispositionsListBySettlements(final List<Long> settlementsId, LocalDate dateOfExecution) throws SettlementNotFoundException {
        List<Settlement> settlements = settlementService.getSettlementListById(settlementsId);
        List<Disposition> dispositions = new ArrayList<>();
        for (Settlement settlement : settlements) {
            Disposition disposition = new Disposition().builder()
                    .dateOfExecution(dateOfExecution)
                    .title(settlement.getTitle())
                    .amount(settlement.getAmount())
                    .vatAmount(settlement.getVatAmount())
                    .contractor(settlement.getContractor())
                    .owner(settlement.getOwner())
                    .bankAccount(settlement.getBankAccount())
                    .ownerBankAccount(settlement.getOwnerBankAccount())
                    .settlement(settlement)
                    .build();
            dispositions.add(disposition);
        }
        saveDispositionsList(dispositions);
        LOGGER.info("Zapisano listę dyspozycji");
        return dispositions;
    }
}
