package com.mypayments.service.domain;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Payment;
import com.mypayments.domain.Settlement;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SettlementTestSuite {
    @Autowired
    ContractorRepository contractorRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    SettlementsRepository settlementsRepository;

    @Test
    public void testGetPaymentsAmount() {
        //Given
        Settlement settlement = new Settlement();
        settlement.setAmount(new BigDecimal(1250));
        Payment payment1 = new Payment();
        payment1.setAmount(new BigDecimal("600"));
        Payment payment2 = new Payment();
        payment2.setAmount(new BigDecimal("650"));
        settlement.getPayments().add(payment1);
        settlement.getPayments().add(payment2);
        //When
        BigDecimal paymentsAmount = new BigDecimal("1250");
        //Then
       // Assert.assertEquals(paymentsAmount, settlement.getPaymentsAmount());
      //  Assert.assertTrue(settlement.isPaid());

    }

    @Test
    public void testSaveSettlement() {
        //Given
        Settlement settlement = new Settlement();
        settlementsRepository.save(settlement);

        //When
        Optional<Settlement> testSettlement = settlementsRepository.findById(settlement.getId());

        //Then
        Assert.assertTrue(testSettlement.isPresent());

        //CleanUp
        settlementsRepository.deleteById(settlement.getId());
    }

    @Test
    public void testFindSettlementById() {
        //Given
        Settlement settlement = new Settlement();
        settlementsRepository.save(settlement);

        //When
        Optional<Settlement> testSettlement = settlementsRepository.findById(settlement.getId());

        //Then
        Assert.assertTrue(testSettlement.isPresent());

        //CleanUp
        settlementsRepository.deleteById(settlement.getId());
    }

    @Test
    public void testFindAllSetlementsByContractor_ID() {
        //Given
        Settlement settlement = new Settlement();
        Contractor contractor = new Contractor();
        settlement.setContractor(contractor);
        contractor.getSettlements().add(settlement);
        settlementsRepository.save(settlement);
        contractorRepository.save(contractor);

        //When
        List<Settlement> settlements = settlementsRepository.findByContractor_Id(contractor.getId());

        //Then
        Assert.assertTrue(settlements.stream().map(Settlement::getId).anyMatch(n -> n.equals(settlement.getId())));
        Assert.assertEquals(1, settlements.size());
        //CleanUp
        settlementsRepository.deleteById(settlement.getId());
        contractorRepository.deleteById(contractor.getId());
    }

    @Test
    public void testDeleteSettlement() {
        //Given
        Settlement settlement = new Settlement();
        settlementsRepository.save(settlement);

        //When
        settlementsRepository.deleteById(settlement.getId());
        Optional<Settlement> testSettlement = settlementsRepository.findById(settlement.getId());

        //Then
        Assert.assertFalse(testSettlement.isPresent());

    }

    @Test
    public void testContractorRelation() {
        //Given
        Settlement settlement = new Settlement();
        Contractor contractor = new Contractor();
        contractor.getSettlements().add(settlement);
        settlement.setContractor(contractor);
        contractorRepository.save(contractor);
        settlementsRepository.save(settlement);

        //When
        Optional<Settlement> testSettlement = settlementsRepository.findById(settlement.getId());
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
        Long contractorId = settlement.getContractor().getId();

        //Then
        Assert.assertEquals(contractor.getId(), contractorId);
        Assert.assertEquals(1, testContractor.get().getSettlements().size());

        //CleanUp
        settlementsRepository.deleteById(settlement.getId());
        contractorRepository.deleteById(contractor.getId());
    }

    @Test
    public void testPaymentRelation() {
        //Given
        Settlement settlement = new Settlement();
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        settlement.getPayments().add(payment1);
        settlement.getPayments().add(payment2);
        payment1.setSettlement(settlement);
        payment2.setSettlement(settlement);
        settlementsRepository.save(settlement);
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        //When
        Optional<Settlement> testSettlement = settlementsRepository.findById(settlement.getId());
        Optional<Payment> testPayment = paymentRepository.findById(payment1.getId());

        List<Payment> payments = testSettlement.get().getPayments();
        Long settlementId = testPayment.get().getSettlement().getId();


        //Then
        Assert.assertEquals(2, payments.size());
        Assert.assertEquals(settlement.getId(), settlementId);

        //CleanUp
        paymentRepository.deleteById(payment1.getId());
        paymentRepository.deleteById(payment2.getId());
        settlementsRepository.deleteById(settlement.getId());
    }


}
