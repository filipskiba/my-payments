package com.mypayments.service;

import com.mypayments.domain.Dto.ExchangeratesDto;
import com.mypayments.domain.Dto.RatesDto;
import com.mypayments.nbp.NbpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class NbpServiceTest {

    @Mock
    private NbpClient nbpClient;

    @InjectMocks
    private NbpService nbpService;

    private RatesDto ratesDto1 = new RatesDto();
    private RatesDto ratesDto2 = new RatesDto();
    private RatesDto ratesDto3 = new RatesDto();
    private List<RatesDto> ratesDtoList = Arrays.asList(ratesDto1, ratesDto2, ratesDto3);
    private ExchangeratesDto exchangeratesDto = new ExchangeratesDto().builder()
            .effectiveDate("2020-01-01")
            .ratesDtoList(ratesDtoList).build();
    private final List<ExchangeratesDto> exchangeratesDtoList = Arrays.asList(exchangeratesDto);

    @Test
    public void getRatesTest() {
        doReturn(exchangeratesDtoList).when(nbpClient).getExchangeRates();

        List<RatesDto> expectedList = nbpService.getRatesDto();

        assertThat(expectedList.size() == exchangeratesDtoList.get(0).getRatesDtoList().size());
    }
}
