package com.mypayments.service;

import com.mypayments.domain.Contractor;
import com.mypayments.domain.ContractorType;
import com.mypayments.exception.ContractorNotFoundException;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
public class ContractorServiceTest {
    @Mock
    private ContractorRepository contractorRepository;

    @InjectMocks
    private ContractorService contractorService;

    private final Contractor exampleContractor = Contractor.builder()
            .id(1L)
            .nipId("555-210-10-26")
            .contractorName("TestName")
            .address("TestAdress")
            .city("TestCity")
            .zipCode("TestZipCode")
            .contractorType(new ContractorType().builder().contractorTypeName("właściciel").build())
            .build();

    private final Contractor exampleContractor2 = Contractor.builder()
            .id(1L)
            .nipId("555-210-10-26")
            .contractorName("TestName")
            .address("TestAdress")
            .city("TestCity")
            .zipCode("TestZipCode")
            .contractorType(new ContractorType().builder().contractorTypeName("klient").build())
            .build();


    @Test
    public void whenGetAllOwnersThenReturnContractorsList(){
        List<Contractor> expectedContractors = Arrays.asList(exampleContractor,exampleContractor2);

        doReturn(expectedContractors.stream().filter(s->s.getContractorType().getContractorTypeName().equals("właściciel")).collect(Collectors.toList())).when(contractorRepository).findContractorByContractorTypeContractorTypeName("właściciel");
        // when
        List<Contractor> actualContractors = contractorService.getAllOwners();
        // then
        assertThat(1).isEqualTo(actualContractors.size());
        assertThat(actualContractors.contains(exampleContractor2)).isFalse();
    }
    @Test
    public void whenGetAllContractors_thenReturnContractorsList() {
        // given
        List<Contractor> expectedContractors = Arrays.asList(exampleContractor);

        doReturn(expectedContractors).when(contractorRepository).findAll();
        // when
        List<Contractor> actualContractors = contractorService.getAllContractors();
        // then
        assertThat(actualContractors).isEqualTo(expectedContractors);
    }

    @Test
    public void shouldSavedContractorSuccessfully() {
        given(contractorRepository.findById(exampleContractor.getId())).willReturn(Optional.empty());
        given(contractorRepository.save(exampleContractor)).willAnswer(invocation -> invocation.getArgument(0));

        Contractor savedContractor = contractorService.saveContractor(exampleContractor);

        assertThat(savedContractor).isNotNull();

        verify(contractorRepository).save(any(Contractor.class));
    }

    @Test
    public void shouldUpdateContractor() throws ContractorNotFoundException {
        given(contractorRepository.findById(exampleContractor.getId())).willReturn(Optional.of(exampleContractor));
        given(contractorRepository.save(exampleContractor)).willReturn(exampleContractor);

        final Contractor expected = contractorService.updateContractor(exampleContractor);

        assertThat(expected).isNotNull();

        verify(contractorRepository).save(any(Contractor.class));
    }

    @Test
    public void findContractorById() throws ContractorNotFoundException {
        final Long id = 1L;

        given(contractorRepository.findById(exampleContractor.getId())).willReturn(Optional.of(exampleContractor));
        final Optional<Contractor> expected = Optional.ofNullable(contractorService.getContractorById(id));

        assertThat(expected).isNotNull();
    }

    @Test
    public void shouldBeDelete() throws ContractorNotFoundException {
        final Long id = 1L;
        given(contractorRepository.findById(exampleContractor.getId())).willReturn(Optional.of(exampleContractor));
        contractorService.deleteContractorById(id);
        contractorService.deleteContractorById(id);

        verify(contractorRepository, times(2)).deleteById(id);
    }
}

