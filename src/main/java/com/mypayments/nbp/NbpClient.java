package com.mypayments.nbp;

import com.mypayments.domain.Dto.ExchangeratesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class NbpClient {
    private final String NBP_API_ENDPOINT = "http://api.nbp.pl/api/exchangerates/tables/A";

    @Autowired
    private RestTemplate restTemplate;

    private URI getNbpUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(NBP_API_ENDPOINT)
                .build().encode().toUri();
        return url;
    }

    public List<ExchangeratesDto> getExchangeRates() {
        try {
            ExchangeratesDto[] exchangeratesResponse = restTemplate.getForObject(getNbpUrl(), ExchangeratesDto[].class);
            return Arrays.asList(ofNullable(exchangeratesResponse).orElse(new ExchangeratesDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

}
