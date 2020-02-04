package com.mypayments.repository;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface StatusRepository extends CrudRepository<Status, Long> {
    @Override
    List<Status> findAll();

    @Override
    Optional<Status> findById(Long id);

    @Override
    Status save(Status status);

    @Override
    void deleteById(Long id);

    List<Status> findStatusByContractor_Id(Long id);
}
