package com.mypayments.controller;

import com.google.gson.Gson;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Disposition;
import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.domain.Payment;
import com.mypayments.domain.Settlement;
import com.mypayments.repository.PaymentRepository;
import com.mypayments.repository.SettlementsRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Payment.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettlementsRepository settlementsRepository;

    @MockBean
    private PaymentController paymentController;

    @MockBean
    private PaymentRepository paymentRepository;

    private Contractor contractor = new Contractor();
    private Settlement settlement = new Settlement();
    private Disposition disposition = new Disposition();
    private Payment examplePayment = new Payment().builder().id(1L).contractor(contractor).dateOfTranfer(LocalDate.now()).amount(new BigDecimal("1")).settlement(settlement).disposition(disposition).build();
    private PaymentDto examplePaymentDto = new PaymentDto().builder().paymentId(1L).contractorId(contractor.getId()).contractorName(contractor.getContractorName()).dateOfTransfer(LocalDate.now().toString()).amount(new BigDecimal("1")).settlementId(settlement.getId()).dispositionId(disposition.getId()).build();
    @Test
    void shouldGetPaymentById() throws Exception {
        //Given
        Payment payment = examplePayment;
        PaymentDto paymentDto = examplePaymentDto;

        when(paymentController.getPayment(1L)).thenReturn(paymentDto);
        //When & Then
        mockMvc.perform(get("/api/payments/" + payment.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("paymentId", "1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("paymentId").value("1"))
                .andExpect(jsonPath("contractorId").value(contractor.getId()))
                .andExpect(jsonPath("contractorName").value(contractor.getContractorName()))
                .andExpect(jsonPath("dateOfTransfer").value(LocalDate.now().toString()))
                .andExpect(jsonPath("amount").value(new BigDecimal("1")))
                .andExpect(jsonPath("settlementId").value(settlement.getId()));
    }


    @Test
    void shouldGetEmptyPaymentsList() throws Exception {
        //Given
        List<PaymentDto> paymentDtos = new ArrayList<>();
        when(paymentController.getPayments()).thenReturn(paymentDtos);

        //When & Then
        mockMvc.perform(get("/api/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createPayment() throws Exception {
        //Given
        PaymentDto paymentDto = new PaymentDto();
        Gson gson = new Gson();
        String param = gson.toJson(paymentDto);
        //When & Then
        mockMvc.perform(post("/api/payments").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdatePayment() throws Exception {
        //Given
        Payment payment = examplePayment;
        settlementsRepository.save(settlement);
        paymentRepository.save(payment);
        PaymentDto paymentDto = new PaymentDto().builder()
                .paymentId(1L)
                .contractorId(contractor.getId())
                .dateOfTransfer(LocalDate.now().toString())
                .amount(new BigDecimal("2"))
                .settlementId(settlement.getId()).build();
        Gson gson = new Gson();
        String param = gson.toJson(paymentDto);
        when(paymentController.updatePayment(any())).thenReturn(paymentDto);
        System.out.println(payment.toString());
        //When & Then
        mockMvc.perform(put("/api/payments").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.paymentId").value("1"))
                .andExpect(jsonPath("$.contractorId").value(contractor.getId()))
                .andExpect(jsonPath("$.contractorName").value(contractor.getContractorName()))
                .andExpect(jsonPath("$.dateOfTransfer").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("2")))
                .andExpect(jsonPath("$.settlementId").value(settlement.getId()));
    }

    @Test
    void deleteBankAccount() throws Exception {
        //Given
        Payment payment = examplePayment;
        paymentRepository.save(payment);
        //When & Then
        mockMvc.perform(delete("/api/payments/" + payment.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("paymentId", "1"))
                .andExpect(status().is(200));
    }
}
