package com.mypayments.gov;

import com.mypayments.domain.Dto.GovSubjectDto;
import com.mypayments.exception.InvalidDataFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Component
public class GovClient {

    @Value("${gov.api.endpoint}")
    private String govEndpoint;

    @Autowired
    private RestTemplate restTemplate;


    private URI getGovUrl(String nipId, String bankAccount, LocalDate date) {
        URI url = UriComponentsBuilder.fromHttpUrl(govEndpoint)
                .path("/check/nip/" + nipId + "/bank-account/" + bankAccount)
                .queryParam("date", date.toString())
                .build().encode().toUri();
        return url;
    }

    public GovSubjectDto getGovSubject(String nipId, String bankAccount, LocalDate date) throws InvalidDataFormatException {
        try {
            GovSubjectDto govSubjectDto = restTemplate.getForObject(getGovUrl(nipId, bankAccount, date), GovSubjectDto.class);
            return govSubjectDto;
        } catch (RestClientException e) {
            throw new InvalidDataFormatException();
        }
    }
}
