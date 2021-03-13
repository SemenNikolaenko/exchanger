package com.example.exchanger;

import com.example.exchanger.controller.rest.AccountController;
import com.example.exchanger.model.Account;
import com.example.exchanger.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


import java.util.Collections;

@ExtendWith(SpringExtension.class)
public class AccountControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

    }

    @Test
    public void shouldReturnAccountById() throws Exception {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        when(accountService.getAccountById(anyLong())).thenReturn(account);

        mockMvc.perform(get("/account"))
                .andExpect(status().isOk());
    }
}
