package com.mypayments.controller;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dto.ContractorDto;
import com.mypayments.exception.ContractorNotFoundException;
import com.mypayments.exception.SettlementNotFoundException;
import com.mypayments.repository.ContractorRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import java.util.List;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Contractor.class)
class ContractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractorController contractorController;

    @MockBean
    private ContractorRepository contractorRepository;

    @Test
    void shouldGetContractorById() throws Exception {
        //Given
        Contractor contractor = new Contractor(1L, "testName", "5555555555", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        contractorRepository.save(contractor);
        ContractorDto contractorDto = new ContractorDto(1L, "testName", "5555555555", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        when(contractorController.getContractor(1L)).thenReturn(contractorDto);
        //When & Then
        mockMvc.perform(get("/api/contractors/"+contractor.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("contractorId","1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("contractorId").value("1"))
                .andExpect(jsonPath("contractorName").value("testName"))
                .andExpect(jsonPath("nipId").value("5555555555"));
    }

    @Test
    void shouldGetEmptyContratorsList() throws Exception {
        //Given
        List<ContractorDto> contractorDtos = new ArrayList<>();
        when(contractorController.getContractors()).thenReturn(contractorDtos);

        //When & Then
        mockMvc.perform(get("/api/contractors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createContractor() throws Exception {
        //Given
        ContractorDto contractorDto = new ContractorDto(1L, "testName", "5555555555", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Gson gson = new Gson();
        String param = gson.toJson(contractorDto);
        //When & Then
        mockMvc.perform(post("/api/contractors").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateContractor() throws Exception {
        //Given
        Contractor contractor = new Contractor(1L, "testName", "5555555555",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        contractorRepository.save(contractor);
        ContractorDto contractorDto = new ContractorDto(1L, "updatedName", "5555555555",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Gson gson = new Gson();
        String param = gson.toJson(contractorDto);
        when(contractorController.updateContractor(any())).thenReturn(contractorDto);
        //When & Then
        mockMvc.perform(put("/api/contractors").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.contractorId").value("1"))
                .andExpect(jsonPath("$.contractorName").value("updatedName"))
                .andExpect(jsonPath("$.nipId").value("5555555555"));
    }

    @Test
    void deleteContractor() throws Exception {
        //Given
        Contractor contractor = new Contractor(1L, "testName", "5555555555",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        contractorRepository.save(contractor);
        //When & Then
        mockMvc.perform(delete("/api/contractors/" + contractor.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("contractorId", "1"))
                .andExpect(status().is(200));
    }
}