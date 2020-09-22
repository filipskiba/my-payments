package com.mypayments.service;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.repository.BankAccountRepository;
import com.mypayments.repository.ContractorRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private ContractorRepository contractorRepository;

    @InjectMocks
    private BankAccountService bankAccountService;


    private BankAccount exampleBankAccount1 = new BankAccount().builder()
            .id(1L)
            .accountNumber("1111 1111")
            .build();

    private BankAccount exampleBankAccount2 = new BankAccount().builder()
            .id(2L)
            .accountNumber("2222 2222")
            .build();

    private BankAccount exampleBankAccount3 = new BankAccount().builder()
            .id(3L)
            .accountNumber("3333 3333")
            .build();


    private Contractor exampleContractor = new Contractor().builder()
            .id(1L)
            .contractorName("contractor")
            .bankAccounts(Arrays.asList(exampleBankAccount1, exampleBankAccount3))
            .build();
    private Contractor exampleContractor2 = new Contractor().builder()
            .id(2L)
            .contractorName("contractor2")
            .bankAccounts(Arrays.asList(exampleBankAccount2))
            .build();


    @Test
    public void getContractorBankAccounts() throws ContractorNotFoundException {
        exampleBankAccount1.setContractor(exampleContractor);
        exampleBankAccount2.setContractor(exampleContractor2);
        exampleBankAccount3.setContractor(exampleContractor);
        List<BankAccount> allBankAccounts = Arrays.asList(exampleBankAccount1, exampleBankAccount2, exampleBankAccount3);
        when(contractorRepository.findById(1L)).thenReturn(Optional.ofNullable(exampleContractor));
        doReturn(allBankAccounts.stream().filter(s -> s.getContractor().getId() == 1L).collect(Collectors.toList())).when(bankAccountRepository).findBankAccountByContractor_Id(1L);

        // when
        List<BankAccount> actualBankAccounts = bankAccountService.getContractorBankAccounts(1L);
        // then
        assertThat(2).isEqualTo(actualBankAccounts.size());
        assertThat(actualBankAccounts.contains(exampleBankAccount2)).isFalse();
    }

    @Test
    public void getAllBankAccounts() {
        // given
        List<BankAccount> expectedBankAccounts = Arrays.asList(exampleBankAccount1, exampleBankAccount2, exampleBankAccount3);

        doReturn(expectedBankAccounts).when(bankAccountRepository).findAll();
        // when
        List<BankAccount> actualBankAccounts = bankAccountService.getAllBankAccounts();
        // then
        assertThat(actualBankAccounts).isEqualTo(expectedBankAccounts);
    }

    @Test
    public void getBankAccountById() throws BankAccountNotFoundException {
        final Long id = 1L;

        given(bankAccountRepository.findById(exampleBankAccount1.getId())).willReturn(Optional.ofNullable(exampleBankAccount1));
        final Optional<BankAccount> expected = Optional.ofNullable(bankAccountService.getBankAccountById(id));

        assertThat(expected).isNotNull();
    }

    @Test
    public void saveBankAccount() {
        given(bankAccountRepository.findById(exampleBankAccount1.getId())).willReturn(Optional.empty());
        given(bankAccountRepository.save(exampleBankAccount1)).willAnswer(invocation -> invocation.getArgument(0));

        BankAccount savedBankAccount = bankAccountService.saveBankAccount(exampleBankAccount1);

        assertThat(savedBankAccount).isNotNull();

        verify(bankAccountRepository).save(any(BankAccount.class));
    }

    @Test
    public void updateBankAccount() throws BankAccountNotFoundException {
        given(bankAccountRepository.findById(exampleBankAccount1.getId())).willReturn(Optional.of(exampleBankAccount1));
        given(bankAccountRepository.save(exampleBankAccount1)).willReturn(exampleBankAccount1);
        final BankAccount expected = bankAccountService.updateBankAccount(exampleBankAccount1);

        assertThat(expected).isNotNull();

        verify(bankAccountRepository).save(any(BankAccount.class));
    }

    @Test
    public void deleteBankAccountById() throws BankAccountNotFoundException {
        final Long id = 1L;
        given(bankAccountRepository.findById(exampleBankAccount1.getId())).willReturn(Optional.of(exampleBankAccount1));
        bankAccountService.deleteBankAccountById(id);
        bankAccountService.deleteBankAccountById(id);

        verify(bankAccountRepository, times(2)).deleteById(id);
    }
}