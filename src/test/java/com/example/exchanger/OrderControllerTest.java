package com.example.exchanger;

import com.example.exchanger.controller.rest.OrderController;
import com.example.exchanger.model.Account;
import com.example.exchanger.model.ExchangeAction;
import com.example.exchanger.model.Order;
import com.example.exchanger.model.param.OrderRequestParam;
import com.example.exchanger.service.AccountService;
import com.example.exchanger.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private AccountService accountService;
    @MockBean
    private OrderService orderService;

    @Test
    public void shouldReturnNewOrder() throws Exception {
        OrderRequestParam paramsForSale = new OrderRequestParam("sell", "usd", 23.2, "eur", 10d);
        OrderRequestParam paramsForBuy = new OrderRequestParam("buy", "usd", 23.2, "eur", 10d);
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        Order orderToBuy = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, account);
        Order orderToSell = new Order(2l, "usd", "eur", 10d, 2, false, ExchangeAction.SELL, account);


        when(orderService.addOrderToBuy(paramsForBuy, account)).thenReturn(orderToBuy);
        when(orderService.addOrderToSell(paramsForSale, account)).thenReturn(orderToSell);

        mockMvc.perform(post("/orders/create")
                .queryParam("exchangeAction", paramsForBuy.getExchangeAction())
                .queryParam("convertFrom", paramsForBuy.getConvertFrom())
                .queryParam("exchangeRate", String.valueOf(paramsForBuy.getExchangeRate()))
                .queryParam("convertTo", paramsForBuy.getConvertTo())
                .queryParam("amount", String.valueOf(paramsForBuy.getAmount()))
                .queryParam("accountId", String.valueOf(1)))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnAmountOfMoneyAndChangeBalance(){}
}
