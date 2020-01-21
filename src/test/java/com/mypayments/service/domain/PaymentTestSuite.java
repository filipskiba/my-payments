package com.mypayments.service.domain;

import com.mypayments.domain.Payment;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.repository.PaymentRepository;
import com.mypayments.repository.SettlementsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PaymentTestSuite {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private SettlementsRepository settlementsRepository;

    @Test
    public void testSavePayment() {
        //Given
        Payment payment = new Payment();
        paymentRepository.save(payment);

        //When
        Optional<Payment> testPayment = paymentRepository.findById(payment.getId());

        //Then
        Assert.assertTrue(testPayment.isPresent());

        //CleanUp
        paymentRepository.deleteById(payment.getId());

    }
    @Test
    public void testFindPaymentById() {
        //Given
        Payment payment = new Payment();
        paymentRepository.save(payment);

        //When
        Optional<Payment> testPayment = paymentRepository.findById(payment.getId());

        //Then
        Assert.assertTrue(testPayment.isPresent());

        //CleanUp
        paymentRepository.deleteById(payment.getId());
    }
    @Test
    public void testFindAllPayments() {
        //Given
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        //When
        List<Payment> payments = paymentRepository.findAll();

        //Then
        Assert.assertTrue(payments.stream().map(Payment::getId).anyMatch(n->n.equals(payment1.getId())));
        Assert.assertTrue(payments.stream().map(Payment::getId).anyMatch(n->n.equals(payment2.getId())));

        //CleanUp
        paymentRepository.deleteById(payment1.getId());
        paymentRepository.deleteById(payment2.getId());
    }

    @Test
    public void testDeletePaymentById(){
        //Given
        Payment payment = new Payment();
        paymentRepository.save(payment);

        //When
        paymentRepository.deleteById(payment.getId());

        //Then
        Assert.assertFalse(paymentRepository.findById(payment.getId()).isPresent());
    }


}
