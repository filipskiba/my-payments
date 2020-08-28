package com.mypayments.repository;

import com.mypayments.domain.ContractorType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ContractorTypeRepository extends CrudRepository<ContractorType, Long> {
    @Override
    List<ContractorType> findAll();

    @Override
    ContractorType save(ContractorType contractorType);

    @Override
    Optional<ContractorType> findById(Long id);

    Optional<ContractorType> findByContractorTypeName(String contractorTypeName);

    @Override
    void deleteById(Long id);

}
