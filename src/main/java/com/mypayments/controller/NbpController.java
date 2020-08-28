package com.mypayments.controller;


import com.mypayments.domain.Dto.RatesDto;
import com.mypayments.service.NbpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class NbpController {
    @Autowired
    private NbpService nbpService;

    @RequestMapping(method = RequestMethod.GET, value = "/rates")
    public List<RatesDto> getRatesDto() {
        return nbpService.getRatesDto();
    }
}
