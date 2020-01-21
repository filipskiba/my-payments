package com.mypayments.repository;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ContractorRepository extends CrudRepository<Contractor, Long> {
    @Override
    List<Contractor> findAll();

    @Override
    Contractor save(Contractor contractor);

    @Override
    Optional<Contractor> findById(Long id);

    @Override
    void deleteById(Long id);
}
