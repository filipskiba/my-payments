package com.mypayments.service;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Disposition;
import com.mypayments.domain.Payment;
import com.mypayments.exception.DispositionNotFoundException;
import com.mypayments.repository.DispositionRepository;
import com.mypayments.repository.PaymentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DispositionServiceTest {

    @Mock
    private DispositionRepository dispositionRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private DispositionService dispositionService;

    private Disposition exampleDisposition1 = new Disposition().builder()
            .id(1L).build();
    private Disposition exampleDisposition2 = new Disposition().builder()
            .id(2L).build();
    private Disposition exampleDisposition3 = new Disposition().builder()
            .id(3L).build();

    private Payment examplePayment = new Payment().builder()
            .id(1L).build();


    @Test
    public void getAllDispositions() {
        // given
        List<Disposition> expectedDispositions = Arrays.asList(exampleDisposition1, exampleDisposition2, exampleDisposition3);

        doReturn(expectedDispositions).when(dispositionRepository).findAll();
        // when
        List<Disposition> actualDispositions = dispositionService.getAllDispositions();
        // then
        assertThat(actualDispositions).isEqualTo(expectedDispositions);
    }

    @Test
    public void getDispositionById() throws DispositionNotFoundException {
        final Long id = 1L;

        given(dispositionRepository.findById(exampleDisposition1.getId())).willReturn(Optional.ofNullable(exampleDisposition1));
        final Optional<Disposition> expected = Optional.ofNullable(dispositionService.getDispositionById(id));

        assertThat(expected).isNotNull();
    }

    @Test
    public void saveDisposition() {
        given(dispositionRepository.findById(exampleDisposition1.getId())).willReturn(Optional.empty());
        given(dispositionRepository.save(exampleDisposition1)).willAnswer(invocation -> invocation.getArgument(0));

        Disposition saveDisposition = dispositionService.saveDisposition(exampleDisposition1);

        assertThat(saveDisposition).isNotNull();

        verify(dispositionRepository).save(any(Disposition.class));
    }

    @Test
    public void updateDisposition() throws DispositionNotFoundException {
        given(dispositionRepository.findById(exampleDisposition1.getId())).willReturn(Optional.of(exampleDisposition1));
        given(dispositionRepository.save(exampleDisposition1)).willReturn(exampleDisposition1);
        final Disposition expected = dispositionService.updateDisposition(exampleDisposition1);

        assertThat(expected).isNotNull();

        verify(dispositionRepository).save(any(Disposition.class));
    }

    @Test
    public void deleteDispositionById() throws DispositionNotFoundException {
        final Long id = 1L;
        given(dispositionRepository.findById(exampleDisposition1.getId())).willReturn(Optional.of(exampleDisposition1));
        dispositionService.deleteDispositionById(id);
        dispositionService.deleteDispositionById(id);

        verify(dispositionRepository, times(2)).deleteById(id);
    }

}