package com.mypayments.controller;

import com.google.gson.Gson;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dto.ContractorDto;
import com.mypayments.domain.Dto.SettlementDto;
import com.mypayments.domain.Payment;
import com.mypayments.domain.Settlement;
import com.mypayments.repository.ContractorRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Settlement.class)
class SettlementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettlementController settlementController;

    @MockBean
    private SettlementsRepository settlementsRepository;

    @Test
    void shouldGetSettlementById() throws Exception {
        //Given
        Contractor contractor = new Contractor();

        Settlement settlement = new Settlement(1L, "testDoc", contractor, LocalDate.now(), LocalDate.now(), new BigDecimal("1"), new ArrayList<>());
        settlementsRepository.save(settlement);
        SettlementDto settlementDto = new SettlementDto(1L, "testDoc", contractor.getId(), contractor.getContractorName(), LocalDate.now(), LocalDate.now(), new BigDecimal("1"), new ArrayList<>(), false, new BigDecimal("1"));
        when(settlementController.getSettlement(1L)).thenReturn(settlementDto);
        //When & Then
        mockMvc.perform(get("/api/settlements/" + settlement.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("settlementId", "1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("settlementId").value("1"))
                .andExpect(jsonPath("document").value("testDoc"))
                .andExpect(jsonPath("contractorId").value(contractor.getId()))
                .andExpect(jsonPath("contractorName").value(contractor.getContractorName()))
                .andExpect(jsonPath("dateOfIssue").value(LocalDate.now().toString()))
                .andExpect(jsonPath("dateOfPayment").value(LocalDate.now().toString()))
                .andExpect(jsonPath("amount").value(new BigDecimal("1")))
                .andExpect(jsonPath("paymentDtoList").value(new ArrayList<>()))
                .andExpect(jsonPath("isPaid").value(false))
                .andExpect(jsonPath("paidAmount").value(new BigDecimal("1")));
    }

    @Test
    void shouldGetEmptySettlementList() throws Exception {
        //Given
        List<SettlementDto> settlementDtos = new ArrayList<>();
        when(settlementController.getAllSettlements()).thenReturn(settlementDtos);

        //When & Then
        mockMvc.perform(get("/api/settlements").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

/*    @Test
    void createSettlement() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        SettlementDto settlementDto = new SettlementDto(1L, "testDoc", contractor.getId(), contractor.getContractorName(), LocalDate.now(), LocalDate.now(), new BigDecimal("1"), new ArrayList<>(), false, new BigDecimal("1"));
        Gson gson = new Gson();
        String param = gson.toJson(settlementDto);
        //When & Then
        mockMvc.perform(post("/api/settlements").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateSettlement() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        Settlement settlement = new Settlement(1L, "testDoc", contractor, LocalDate.now(), LocalDate.now(), new BigDecimal("1"), new ArrayList<>());
        settlementsRepository.save(settlement);
        SettlementDto settlementDto = new SettlementDto(1L, "testDocument", contractor.getId(), contractor.getContractorName(), LocalDate.now(), LocalDate.now(), new BigDecimal("1"), new ArrayList<>(), false, new BigDecimal("1"));
        Gson gson = new Gson();
        String param = gson.toJson(settlementDto);
        when(settlementController.updateSettlement(any())).thenReturn(settlementDto);
        //When & Then
        mockMvc.perform(put("/api/settlements").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200))
                .andExpect(jsonPath("settlementId").value("1"))
                .andExpect(jsonPath("document").value("testDocument"))
                .andExpect(jsonPath("contractorId").value(contractor.getId()))
                .andExpect(jsonPath("contractorName").value(contractor.getContractorName()))
                .andExpect(jsonPath("dateOfIssue").value(LocalDate.now().toString()))
                .andExpect(jsonPath("dateOfPayment").value(LocalDate.now().toString()))
                .andExpect(jsonPath("amount").value(new BigDecimal("1")))
                .andExpect(jsonPath("paymentDtoList").value(new ArrayList<>()))
                .andExpect(jsonPath("isPaid").value(false))
                .andExpect(jsonPath("paidAmount").value(new BigDecimal("1")));
    }*/

    @Test
    void deleteSettlement() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        Settlement settlement = new Settlement(1L, "testDoc", contractor, LocalDate.now(), LocalDate.now(), new BigDecimal("1"), new ArrayList<>());
        settlementsRepository.save(settlement);
        //When & Then
        mockMvc.perform(delete("/api/settlements/" + settlement.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("settlementId", "1"))
                .andExpect(status().is(200));
    }
}