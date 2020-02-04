package com.mypayments.service.domain;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Status;
import com.mypayments.repository.ContractorRepository;
import com.mypayments.repository.StatusRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StatusTestSuite {
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ContractorRepository contractorRepository;

    @Test
    public void testSaveStatus() {
        //Given
        Status status = new Status();

        //When
        statusRepository.save(status);
        Optional<Status> result = statusRepository.findById(status.getId());

        //Then
        Assert.assertTrue(result.isPresent());
        //CleanUp
        statusRepository.deleteById(status.getId());
    }

    @Test
    public void testFindStatusById() {
        //Given
        Status status = new Status();
        statusRepository.save(status);

        //When
        Optional<Status> testStatus = statusRepository.findById(status.getId());

        //Then
        Assert.assertTrue(testStatus.isPresent());

        //CleanUp
        statusRepository.deleteById(status.getId());
    }
    @Test
    public void testFindAllStatuses() {
        //Given
        Status status1 = new Status();
        status1.setIsContractorOnWL(false);
        Status status2 = new Status();
        status2.setIsContractorOnWL(false);
        statusRepository.save(status1);
        statusRepository.save(status2);

        //When
        List<Status> statuses = statusRepository.findAll();

        //Then
        Assert.assertTrue(statuses.stream().map(Status::getId).anyMatch(n->n.equals(status1.getId())));
        Assert.assertTrue(statuses.stream().map(Status::getId).anyMatch(n->n.equals(status2.getId())));

        //CleanUp
        statusRepository.deleteById(status1.getId());
        statusRepository.deleteById(status2.getId());
    }


    @Test
    public void testfindAllStatusesByContractor_ID() {
        //Given
        Contractor contractor = new Contractor();
        Status status1 = new Status();
        status1.setIsContractorOnWL(false);
        Status status2 = new Status();
        status2.setIsContractorOnWL(false);
        status1.setContractor(contractor);
        status2.setContractor(contractor);
        contractor.getStatuses().add(status1);
        contractor.getStatuses().add(status2);
        statusRepository.save(status1);
        statusRepository.save(status2);
        contractorRepository.save(contractor);

        //When
        List<Status> statuses = statusRepository.findStatusByContractor_Id(contractor.getId());

        //Then
        Assert.assertEquals(2,statuses.size());
        Assert.assertTrue(statuses.stream().map(Status::getId).anyMatch(n->n.equals(status1.getId())));
        Assert.assertTrue(statuses.stream().map(Status::getId).anyMatch(n->n.equals(status2.getId())));

        //CleanUp
        statusRepository.deleteById(status1.getId());
        statusRepository.deleteById(status2.getId());
        contractorRepository.deleteById(contractor.getId());
    }

    @Test
    public void testDeleteStatus() {
        //Given
        Status status = new Status();
        statusRepository.save(status);

        //When
        statusRepository.deleteById(status.getId());

        //Then
        Assert.assertFalse(statusRepository.findById(status.getId()).isPresent());

    }

    @Test
    public void testContractorRelation() {

            //Given
            Status status = new Status();
            Contractor contractor = new Contractor();
            contractor.getStatuses().add(status);
            status.setContractor(contractor);
            contractorRepository.save(contractor);
            statusRepository.save(status);

            //When
            Optional<Status> testStatus = statusRepository.findById(status.getId());
            Optional<Contractor> testContractor = contractorRepository.findById(contractor.getId());
            Long contractorId = status.getContractor().getId();

            //Then
            Assert.assertEquals(contractor.getId(),contractorId);
            Assert.assertEquals(1,testContractor.get().getStatuses().size());

            //CleanUp
            contractorRepository.deleteById(contractor.getId());

    }
}
