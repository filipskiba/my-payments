package com.mypayments.repository;

import com.mypayments.domain.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    @Override
    List<BankAccount> findAll();

    @Override
    BankAccount save(BankAccount bankAccount);

    @Override
    Optional<BankAccount> findById(Long id);

    @Override
    void deleteById(Long id);

    List<BankAccount> findBankAccountByContractor_Id(Long id);


}
