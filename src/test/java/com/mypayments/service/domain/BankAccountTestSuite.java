package com.mypayments.service.domain;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.repository.BankAccountRepository;
import com.mypayments.repository.ContractorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BankAccountTestSuite {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private ContractorRepository contractorRepository;

    @Test
    public void testSaveBankAccount() {
        //Given
        BankAccount bankAccount = new BankAccount();

        //When
        assertNull(bankAccount.getId());
        bankAccountRepository.save(bankAccount);
        assertNotNull(bankAccount.getId());
        Optional<BankAccount> testBankAccount = bankAccountRepository.findById(bankAccount.getId());

        //Then
        Assert.assertTrue(testBankAccount.isPresent());

        //CleanUp
        bankAccountRepository.deleteById(bankAccount.getId());
    }
    @Test
    public void testBankAccountFindById() {
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccountRepository.save(bankAccount);

        //When
        Optional<BankAccount> testBankAccount = bankAccountRepository.findById(bankAccount.getId());

        //Then
        Assert.assertTrue(testBankAccount.isPresent());

        //CleanUp
        bankAccountRepository.deleteById(bankAccount.getId());
    }
    @Test
    public void testFindAllBankAccounts() {
        //Given
        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        bankAccountRepository.save(bankAccount1);
        bankAccountRepository.save(bankAccount2);

        //When
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        //Then
        Assert.assertTrue(bankAccounts.stream().map(BankAccount::getId).anyMatch(n->n.equals(bankAccount1.getId())));
        Assert.assertTrue(bankAccounts.stream().map(BankAccount::getId).anyMatch(n->n.equals(bankAccount2.getId())));

        //CleanUp
        bankAccountRepository.deleteById(bankAccount1.getId());
        bankAccountRepository.deleteById(bankAccount2.getId());

    }
    @Test
    public void testFindBankAccountsByContractorId() {
        //Given
        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        BankAccount bankAccount3 = new BankAccount();

        Contractor contractor1 = new Contractor();
        Contractor contractor2 = new Contractor();

        contractor1.getBankAccounts().add(bankAccount1);
        contractor1.getBankAccounts().add(bankAccount2);
        contractor2.getBankAccounts().add(bankAccount3);

        bankAccount1.setContractor(contractor1);
        bankAccount2.setContractor(contractor1);
        bankAccount3.setContractor(contractor2);

        contractorRepository.save(contractor1);
        contractorRepository.save(contractor2);
        bankAccountRepository.save(bankAccount1);
        bankAccountRepository.save(bankAccount2);
        bankAccountRepository.save(bankAccount3);

        //When

        List<BankAccount> bankAccounts1 = bankAccountRepository.findBankAccountByContractor_Id(contractor1.getId());
        List<BankAccount> bankAccounts2 = bankAccountRepository.findBankAccountByContractor_Id(contractor2.getId());

        //Then
        Assert.assertTrue(bankAccounts1.stream().map(BankAccount::getId).anyMatch(n->n.equals(bankAccount1.getId())));
        Assert.assertTrue(bankAccounts1.stream().map(BankAccount::getId).anyMatch(n->n.equals(bankAccount2.getId())));
        Assert.assertTrue(bankAccounts2.stream().map(BankAccount::getId).anyMatch(n->n.equals(bankAccount3.getId())));

        Assert.assertEquals(2,bankAccounts1.size());
        Assert.assertEquals(1,bankAccounts2.size());

        //CleanUp
        bankAccountRepository.deleteById(bankAccount1.getId());
        bankAccountRepository.deleteById(bankAccount2.getId());
        bankAccountRepository.deleteById(bankAccount3.getId());
        contractorRepository.deleteById(contractor1.getId());
        contractorRepository.deleteById(contractor2.getId());


    }
    @Test
    public void testDeleteBankAccountById() {
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccountRepository.save(bankAccount);

        //When
        bankAccountRepository.deleteById(bankAccount.getId());

        //Then
        Assert.assertFalse(bankAccountRepository.findById(bankAccount.getId()).isPresent());
    }
    @Test
    public void testContractorRelation() {
        //Given
        BankAccount bankAccount = new BankAccount();
        Contractor contractor = new Contractor();
        contractor.getBankAccounts().add(bankAccount);
        bankAccount.setContractor(contractor);
        contractorRepository.save(contractor);
        bankAccountRepository.save(bankAccount);

        //When
        Optional<BankAccount> testBankAccount = bankAccountRepository.findById(bankAccount.getId());
        Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
        Long contractorId = bankAccount.getContractor().getId();

        //Then
        Assert.assertEquals(contractor.getId(),contractorId);
        Assert.assertEquals(1,testContractor.get().getBankAccounts().size());

        //CleanUp
        bankAccountRepository.deleteById(bankAccount.getId());
        contractorRepository.deleteById(contractor.getId());

    }
}
