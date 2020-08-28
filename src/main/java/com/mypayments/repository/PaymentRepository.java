package com.mypayments.repository;

import com.mypayments.domain.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    @Override
    List<Payment> findAll();

    @Override
    Payment save(Payment payment);

    @Override
    Optional<Payment> findById(Long id);

    @Override
    void deleteById(Long id);
}
