package com.mypayments.service;

import com.mypayments.domain.Dto.RatesDto;
import com.mypayments.nbp.NbpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NbpService {
    @Autowired
    private NbpClient nbpClient;

    public List<RatesDto> getRatesDto() {
        return nbpClient.getExchangeRates().stream().
                flatMap(n -> n.getRatesDtoList().stream()
                        .map(s -> new RatesDto().builder()
                                .currency(s.getCurrency())
                                .mid(s.getMid())
                                .code(s.getCode()).build())).collect(Collectors.toList());
    }
}
