package com.mypayments.controller;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dispositions;
import com.mypayments.domain.Dto.DispositionDto;
import com.mypayments.repository.DispositionRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(value = Dispositions.class)
class DispositionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DispositionController dispositionController;

    @Autowired
    private DispositionRepository dispositionRepository;

    @Test
    void shouldGetDispositionById() throws Exception {
        //Given
        Dispositions dispositions = new Dispositions(1L, LocalDate.parse("2019-01-01"), false, "title", new BigDecimal("1"), new Contractor().builder().id(1L).build(), new BankAccount().builder().id(1L).build(), new ArrayList<>());
        dispositionRepository.save(dispositions);
        DispositionDto dispositionDto = new DispositionDto(1L, "2019-01-01", false, "title", new BigDecimal("1"), 1L, 1L, new ArrayList<>());
        when(dispositionController.getDisposition(1L)).thenReturn(dispositionDto);
        mockMvc.perform(get("/api/disposition/" + dispositions.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("dispositionId", "1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("dispositionId").value("1"))
                .andExpect(jsonPath("dateOfExecution").value("2019-01-01"))
                .andExpect(jsonPath("isExecuted").value(false))
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("amount").value(new BigDecimal("1")))
                .andExpect(jsonPath("contractorId").value(1L))
                .andExpect(jsonPath("bankAccountId").value(1L))
                .andExpect(jsonPath("paymentDtoList").value(new ArrayList<>()));


    }

    @Test
    void shouldGetEmptyDispositionsList() throws Exception {

    }

    @Test
    void createDisposition() throws Exception {

    }

    @Test
    void shouldUpdateDisposition() throws Exception {

    }

    @Test
    void deleteDisposition() throws Exception {

    }
}