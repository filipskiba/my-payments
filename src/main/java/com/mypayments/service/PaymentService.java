package com.mypayments.service;

import com.mypayments.domain.Disposition;
import com.mypayments.domain.Payment;
import com.mypayments.exception.PaymentNotFoundException;
import com.mypayments.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DispositionService dispositionService;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentByID(final Long paymentId) throws PaymentNotFoundException {
        if(paymentRepository.findById(paymentId).isPresent()){
            return paymentRepository.findById(paymentId).get();
        }
        else {
            LOGGER.error("Can not find payment with ID: " + paymentId);
            throw new PaymentNotFoundException();
        }
    }

    public Payment savePayment(final Payment payment) {
        LOGGER.info("Successfully saved payment and disposition");
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(final Payment payment) throws PaymentNotFoundException {
        if(paymentRepository.findById(payment.getId()).isPresent()){
            return paymentRepository.save(payment);
        }
        else {
            LOGGER.error("Can not find payment with ID: " + payment.getId());
            throw new PaymentNotFoundException();
        }
    }

    public void deletePaymentById(final Long paymentId) throws PaymentNotFoundException {
        if(paymentRepository.findById(paymentId).isPresent()) {
            paymentRepository.deleteById(paymentId);
            LOGGER.info("Successfully deleted payment with ID: " +paymentId);
        }
        else {
            LOGGER.error("Can not find payment with ID: " +paymentId);
            throw new PaymentNotFoundException();
        }
    }
}
