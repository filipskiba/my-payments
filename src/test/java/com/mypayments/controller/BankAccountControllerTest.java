package com.mypayments.controller;

import com.google.gson.Gson;
import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Dto.BankAccountDto;
import com.mypayments.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BankAccount.class)
 class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountController bankAccountController;

    @MockBean
    private BankAccountRepository bankAccountRepository;


    @Test
    void shouldGetEmptyBankAccountList() throws Exception {
        //Given
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        when(bankAccountController.getContractorBankAccounts(1L)).thenReturn(bankAccountDtos);

        //When & Then
        mockMvc.perform(get("/api/bankAccounts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createBankAccount() throws Exception {
        //Given
        BankAccountDto bankAccountDto = new BankAccountDto();
        Gson gson = new Gson();
        String param = gson.toJson(bankAccountDto);
        //When & Then
        mockMvc.perform(post("/api/bankAccounts").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateBankAccount() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        BankAccount bankAccount = new BankAccount(1L, "06 2490 0005 0000 4530 6049 9844", contractor);
        bankAccountRepository.save(bankAccount);
        BankAccountDto bankAccountDto = new BankAccountDto(1L, contractor.getId(), "06 2490 0005 0000 4530 6049 9845");
        Gson gson = new Gson();
        String param = gson.toJson(bankAccountDto);
        when(bankAccountController.updateBankAccount(any())).thenReturn(bankAccountDto);
        //When & Then
        mockMvc.perform(put("/api/bankAccounts").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.bankAccountId").value("1"))
                .andExpect(jsonPath("$.contractorId").value(contractor.getId()))
                .andExpect(jsonPath("$.bankAccountNumber").value("06 2490 0005 0000 4530 6049 9845"));
    }

    @Test
    void deleteBankAccount() throws Exception {
        //Given
        Contractor contractor = new Contractor();
        BankAccount bankAccount = new BankAccount(1L, "06 2490 0005 0000 4530 6049 9844", contractor);
        bankAccountRepository.save(bankAccount);
        //When & Then
        mockMvc.perform(delete("/api/bankAccounts/" + bankAccount.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("bankAccountId", "1"))
                .andExpect(status().is(200));
    }
}
