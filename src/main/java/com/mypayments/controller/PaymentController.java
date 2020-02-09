package com.mypayments.controller;

import com.mypayments.domain.Dto.BankAccountDto;
import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.exception.BankAccountNotFoundException;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.PaymentNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.mapper.PaymentMapper;
import com.mypayments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/payments/{paymentId}")
    public PaymentDto getPayment(@PathVariable("paymentId") Long paymentId) throws PaymentNotFoundException {
        return paymentMapper.mapToPaymentDto(paymentService.getPaymentByID(paymentId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/payments")
    public List<PaymentDto> getPayments() {
        return paymentMapper.mapToPaymentsDtoList(paymentService.getAllPayments());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/payments")
    public void createPayment(@RequestBody PaymentDto paymentDto) throws SettlementNotFoundException, ContractorNotFoundException {
        paymentService.savePayment(paymentMapper.mapToPayment(paymentDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/payments")
    public PaymentDto updatePayment(@RequestBody PaymentDto paymentDto) throws ContractorNotFoundException, SettlementNotFoundException, PaymentNotFoundException {
        return paymentMapper.mapToPaymentDto(paymentService.updatePayment(paymentMapper.mapToPayment(paymentDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/payments/{paymentId}")
    public void deletePayment(@PathVariable("paymentId") Long paymentId) throws PaymentNotFoundException {
        paymentService.deletePaymentById(paymentId);
    }

}
