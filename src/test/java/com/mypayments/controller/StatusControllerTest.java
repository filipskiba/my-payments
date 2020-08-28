package com.mypayments.controller;

import com.google.gson.Gson;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dto.SettlementDto;
import com.mypayments.domain.Dto.StatusDto;
import com.mypayments.domain.Settlement;
import com.mypayments.domain.Status;
import com.mypayments.repository.StatusRepository;
import org.junit.Test;
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
@WebMvcTest(Status.class)
public class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatusController statusController;

    @MockBean
    private StatusRepository statusRepository;

    @Test
    public void shouldGetStatusById() throws Exception {
        LocalDate date = LocalDate.parse("2020-01-01");
        Contractor contractor = new Contractor().builder().id(1L).contractorName("test").build();
        Status status = new Status(1L,contractor,false,date);

        statusRepository.save(status);

        StatusDto statusDto = new StatusDto(1L,1L,false,"2020-01-01","test");
        when(statusController.getStatus(1L)).thenReturn(statusDto);
        mockMvc.perform(get("/api/statuses/" + status.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("statusId").value("1"))
                .andExpect(jsonPath("contractorId").value(contractor.getId()))
                .andExpect(jsonPath("isContractorOnWL").value(false))
                .andExpect(jsonPath("statusDate").value(date.toString()))
                .andExpect(jsonPath("contractorName").value(contractor.getContractorName()));

    }
    @Test
    public void shouldGetEmptyStatusList() throws Exception {
        //Given
        List<StatusDto> statuses = new ArrayList<>();
        when(statusController.getAllStatuses()).thenReturn(statuses);

        //When & Then
        mockMvc.perform(get("/api/statuses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createStatusForContractor() throws Exception {
        //Given
        LocalDate date = LocalDate.parse("2020-01-01");
        Contractor contractor = new Contractor().builder().id(1L).contractorName("test").build();
        StatusDto statusDto = new StatusDto(1L,1L,false,date.toString(),"test");
        Gson gson = new Gson();
        String param = gson.toJson(statusDto);
        System.out.println(param);
        //When & Then
        mockMvc.perform(post("/api/statuses/" +contractor.getId()).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200));
    }


    @Test
    public void deleteStatus() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        LocalDate date = LocalDate.parse("2020-01-01");
        Status status = new Status(1L,contractor,false,date);
        statusRepository.save(status);
        //When & Then
        mockMvc.perform(delete("/api/statuses/" + status.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("statusId", "1"))
                .andExpect(status().is(200));
    }
}
