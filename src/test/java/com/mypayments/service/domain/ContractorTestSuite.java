package com.mypayments.service.domain;

import com.mypayments.domain.*;
import com.mypayments.repository.*;
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
public class ContractorTestSuite {
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    ContractorRepository contractorRepository;
    @Autowired
    SettlementsRepository settlementsRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void testSaveContractor() {
        //Given
        Contractor contractor = new Contractor();
        //When
        contractorRepository.save(contractor);
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
        //Then
        Assert.assertTrue(testContractor.isPresent());
        //cleanUp
        contractorRepository.deleteById(contractor.getId());
    }

    @Test
    public void testFindContractorById() {
        //Given
        Contractor contractor = new Contractor();
        contractorRepository.save(contractor);

        //When
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());

        //Then
        Assert.assertTrue(testContractor.isPresent());

        //CleanUp
        contractorRepository.deleteById(contractor.getId());
    }

    @Test
    public void testFindAllContractors() {
        //Given
        Contractor contractor1 = new Contractor();
        Contractor contractor2 = new Contractor();
        contractorRepository.save(contractor1);
        contractorRepository.save(contractor2);
        //When
        List<Contractor> contractors = contractorRepository.findAll();

        //Then
        Assert.assertTrue(contractors.stream().map(Contractor::getId).anyMatch(n -> n.equals(contractor1.getId())));
        Assert.assertTrue(contractors.stream().map(Contractor::getId).anyMatch(n -> n.equals(contractor2.getId())));

        //CleanUp
        contractorRepository.deleteById(contractor1.getId());
        contractorRepository.deleteById(contractor2.getId());
    }

    @Test
    public void testDeleteContractorById() {
        //Given
        Contractor contractor = new Contractor();
        contractorRepository.save(contractor);

        //When
        contractorRepository.deleteById(contractor.getId());

        //Then
        Assert.assertFalse(contractorRepository.findById(contractor.getId()).isPresent());
    }

    @Test
    public void testBankAccountRelation() {
        //Given
        BankAccount bankAccount = new BankAccount();
        Contractor contractor = new Contractor();
        contractor.getBankAccounts().add(bankAccount);
        bankAccount.setContractor(contractor);
        contractorRepository.save(contractor);
        bankAccountRepository.save(bankAccount);

        //When
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
        Optional<BankAccount> testBankAccount = bankAccountRepository.findById(bankAccount.getId());
        Long contractorId = testBankAccount.get().getContractor().getId();

        //Then
        Assert.assertEquals(contractor.getId(), contractorId);
        Assert.assertEquals(1, testContractor.get().getBankAccounts().size());
    }

    @Test
    public void testStatusRelation() {
        //Given
        Status status = new Status();
        Contractor contractor = new Contractor();
        contractor.getStatuses().add(status);
        status.setContractor(contractor);
        contractorRepository.save(contractor);
        statusRepository.save(status);

        //When
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
        Optional<Status> testStatus = statusRepository.findById(status.getId());
        Long contractorId = testStatus.get().getContractor().getId();

        //Then
        Assert.assertEquals(contractor.getId(), contractorId);
        Assert.assertEquals(1, testContractor.get().getStatuses().size());

    }

    @Test
    public void testSettlementRelation() {
        //Given
        Settlement settlement = new Settlement();
        Contractor contractor = new Contractor();
        contractor.getSettlements().add(settlement);
        settlement.setContractor(contractor);
        contractorRepository.save(contractor);
        settlementsRepository.save(settlement);

        //When
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
        Optional<Settlement> testSettlement = settlementsRepository.findById(settlement.getId());
        Long contractorId = testSettlement.get().getContractor().getId();

        //Then
        Assert.assertEquals(contractor.getId(), contractorId);
        Assert.assertEquals(1, testContractor.get().getSettlements().size());
    }

    @Test
    public void testPaymentRelation() {
        //Given
        Payment payment = new Payment();
        Contractor contractor = new Contractor();
        contractor.getPayments().add(payment);
        payment.setContractor(contractor);
        contractorRepository.save(contractor);
        paymentRepository.save(payment);

        //When
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
        Optional<Payment> testPayment = paymentRepository.findById(payment.getId());
        Long contractorId = testPayment.get().getContractor().getId();

        //Then
        Assert.assertEquals(contractor.getId(), contractorId);
        Assert.assertEquals(1, testContractor.get().getPayments().size());
    }
}
