package com.mypayments.repository;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Settlement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Transactional
@Repository
public interface SettlementsRepository extends CrudRepository<Settlement, Long> {
    @Override
    List<Settlement> findAll();

    @Override
    Settlement save(Settlement settlement);

    @Override
    Optional<Settlement> findById(Long id);

    @Override
    void deleteById(Long id);

    List<Settlement> findByContractor_Id(Long id);
    //List<Settlement> findAllByPaidIsFalseAndDateOfPaymentBefore(Timestamp timestamp);
}
