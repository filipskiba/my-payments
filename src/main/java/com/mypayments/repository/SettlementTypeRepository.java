package com.mypayments.repository;

import com.mypayments.domain.SettlementType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface SettlementTypeRepository extends CrudRepository<SettlementType, Long> {
    @Override
    List<SettlementType> findAll();

    @Override
    SettlementType save(SettlementType settlementType);

    @Override
    Optional<SettlementType> findById(Long id);

    Optional<SettlementType> findBySettlementTypeName(String settlementTypeName);

    @Override
    void deleteById(Long id);
}
