package com.mypayments.controller;

import com.google.gson.Gson;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.domain.Payment;
import com.mypayments.domain.Settlement;
import com.mypayments.repository.PaymentRepository;
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
    private PaymentController paymentController;

    @MockBean
    private PaymentRepository paymentRepository;

    @Test
    void shouldGetPaymentById() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        Settlement settlement = new Settlement();
        Payment payment = new Payment(1L, contractor, LocalDate.now(), new BigDecimal("1"), settlement);
        paymentRepository.save(payment);
        PaymentDto paymentDto = new PaymentDto(1L, contractor.getId(), contractor.getContractorName(), LocalDate.now(),
                new BigDecimal("1"), settlement.getId());
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

   /* @Test
    void shouldUpdatePayment() throws Exception {
        //Given
        LocalDate currentTime = LocalDate.now();
        Contractor contractor = new Contractor();
        Settlement settlement = new Settlement();
        Payment payment = new Payment(1L, contractor, currentTime, new BigDecimal("1"), settlement);
        paymentRepository.save(payment);
        PaymentDto paymentDto = new PaymentDto().builder()
                .paymentId(1L)
                .contractorId(contractor.getId())
                .dateOfTransfer(currentTime)
                .amount(new BigDecimal("2"))
                .settlementId(settlement.getId()).build();
        Gson gson = new Gson();
        String param = gson.toJson(paymentDto);
        when(paymentController.updatePayment(any())).thenReturn(paymentDto);
        //When & Then
        mockMvc.perform(put("/api/payments").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200))
                .andExpect(jsonPath("paymentId").value("1"))
                .andExpect(jsonPath("contractorId").value(contractor.getId()))
                .andExpect(jsonPath("contractorName").value(contractor.getContractorName()))
                .andExpect(jsonPath("dateOfTransfer").value(currentTime.toString()))
                .andExpect(jsonPath("amount").value(new BigDecimal("2")))
                .andExpect(jsonPath("settlementId").value(settlement.getId()));
    }*/

    @Test
    void deleteBankAccount() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        Settlement settlement = new Settlement();
        Payment payment = new Payment(1L, contractor, LocalDate.now(), new BigDecimal("1"), settlement);
        paymentRepository.save(payment);
        //When & Then
        mockMvc.perform(delete("/api/payments/" + payment.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("paymentId", "1"))
                .andExpect(status().is(200));
    }
}
