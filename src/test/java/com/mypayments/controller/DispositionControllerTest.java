package com.mypayments.controller;

import com.google.gson.Gson;
import com.mypayments.domain.*;
import com.mypayments.domain.Dto.DispositionDto;
import com.mypayments.domain.Dto.DispositionsPackageDto;
import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.repository.DispositionRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Disposition.class)
class DispositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DispositionController dispositionController;

    @MockBean
    private DispositionRepository dispositionRepository;

    private Disposition exampleDisposition = new Disposition().builder()
            .id(1L)
            .dateOfExecution(LocalDate.parse("2019-01-01"))
            .isExecuted(false).title("title")
            .amount(new BigDecimal("1"))
            .bankAccount(new BankAccount().builder()
                    .id(1L)
                    .accountNumber("1111")
                    .build())
            .contractor(new Contractor().builder()
                    .id(1L).build())
            .settlement(new Settlement().builder()
                    .id(1l).build())
            .build();
    private DispositionDto exampleDispositionDto = new DispositionDto().builder()
            .dispositionId(1L)
            .dateOfExecution("2019-01-01")
            .isExecuted(false).title("title")
            .amount(new BigDecimal("1"))
            .contractorId(1L)
            .contractorBankAccountNumber("1111")
            .settlementDtoId(1L)
            .build();

    private DispositionsPackageDto dispositionsPackageDto = new DispositionsPackageDto().builder()
            .dateOfExecution("2020-01-01")
            .idsList(Arrays.asList(1L)).build();

    @Test
    void shouldGetDispositionById() throws Exception {
        //Given
        dispositionRepository.save(exampleDisposition);
        when(dispositionController.getDisposition(1L)).thenReturn(exampleDispositionDto);
        mockMvc.perform(get("/api/dispositions/" + exampleDisposition.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("dispositionId", "1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("dispositionId").value("1"))
                .andExpect(jsonPath("dateOfExecution").value("2019-01-01"))
                .andExpect(jsonPath("isExecuted").value(false))
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("amount").value(new BigDecimal("1")))
                .andExpect(jsonPath("contractorId").value(1L))
                .andExpect(jsonPath("contractorBankAccountNumber").value("1111"));


    }

    @Test
    void shouldGetEmptyDispositionsList() throws Exception {
        //Given
        List<DispositionDto> dispositionDtoList = new ArrayList<>();
        when(dispositionController.getAllDispositions()).thenReturn(dispositionDtoList);
        //When & Then
        mockMvc.perform(get("/api/dispositions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createDispositionsFromIdesList() throws Exception {
        //Given
        Gson gson = new Gson();
        String param = gson.toJson(dispositionsPackageDto);
        //When & Then
        mockMvc.perform(post("/api/dispositions/list").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200));
    }



    @Test
    void shouldUpdateDisposition() throws Exception {
        //Given
        dispositionRepository.save(exampleDisposition);
        Gson gson = new Gson();
        String param = gson.toJson(exampleDispositionDto);
        when(dispositionController.updateDisposition(any())).thenReturn(exampleDispositionDto);
        mockMvc.perform(put("/api/dispositions/").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200))
                .andExpect(jsonPath("dispositionId").value("1"))
                .andExpect(jsonPath("dateOfExecution").value("2019-01-01"))
                .andExpect(jsonPath("isExecuted").value(false))
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("amount").value(new BigDecimal("1")))
                .andExpect(jsonPath("contractorId").value(1L))
                .andExpect(jsonPath("contractorBankAccountNumber").value("1111"));

    }

    @Test
    void deleteDisposition() throws Exception {
        //Given
        dispositionRepository.save(exampleDisposition);
        //When & Then
        mockMvc.perform(delete("/api/dispositions/" + exampleDisposition.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("dispositionId", "1"))
                .andExpect(status().is(200));
    }


}