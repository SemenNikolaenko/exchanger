package com.example.exchanger;

import com.example.exchanger.controller.rest.AccountController;
import com.example.exchanger.model.Account;
import com.example.exchanger.model.Order;
import com.example.exchanger.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private AccountService accountService;


    @Test
    public void shouldReturnAccountById() throws Exception {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());

        when(accountService.getAccountById(anyLong())).thenReturn(account);

        mockMvc.perform(get("/account").queryParam("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(account)));
    }
    @Test
    public void shouldReturnBalance() throws Exception {
        when(accountService.getBalanceFromAccountById(anyLong())).thenReturn(200d);

        mockMvc.perform(get("/account/balance").queryParam("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(String.valueOf(200d).getBytes()));
    }
    @Test
    public void shouldReturnOrderForAccount() throws Exception {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        Order order1 = new Order();
        Order order2 = new Order();
        orderList.add(order);
        orderList.add(order1);
        orderList.add(order2);
        when(accountService.getAllOrders(anyLong())).thenReturn(orderList);

        mockMvc.perform(get("/account/1/orders").queryParam("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderList)));
    }
}
