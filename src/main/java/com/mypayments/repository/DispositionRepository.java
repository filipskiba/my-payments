package com.mypayments.repository;


import com.mypayments.domain.Disposition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DispositionRepository  extends CrudRepository<Disposition, Long> {
    @Override
    List<Disposition> findAll();

    @Override
    Disposition save(Disposition bankAccount);

    @Override
    Optional<Disposition> findById(Long id);

    @Override
    void deleteById(Long id);

}
