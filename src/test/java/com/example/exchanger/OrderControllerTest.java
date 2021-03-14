package com.example.exchanger;

import com.example.exchanger.controller.rest.OrderController;
import com.example.exchanger.model.Account;
import com.example.exchanger.model.ExchangeAction;
import com.example.exchanger.model.Order;
import com.example.exchanger.model.param.OrderRequestParam;
import com.example.exchanger.service.AccountService;
import com.example.exchanger.service.OrderService;
import com.example.exchanger.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
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
    private OrderService orderService;
    @MockBean
    private AccountService accountService;


    @Test
    public void shouldReturnNewOrder() throws Exception {
        OrderRequestParam paramsForBuy = new OrderRequestParam("BUY", "usd", 23.2, "eur", 10d);
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        Order orderToBuy = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, account);


        when(accountService.getAccountById(any())).thenReturn(account);
        when(orderService.addOrderToBuy(any(OrderRequestParam.class), any(Account.class))).thenReturn(orderToBuy);

        mockMvc.perform(post("/orders/create")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(paramsForBuy)).queryParam("accountId", String.valueOf(1l)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderToBuy)));

    }

    @Test
    public void shouldReturnAmountOfMoneyAndChangeBalancePositive() throws Exception {
        when(orderService.executeOrder(1L)).thenReturn(200D);
        when(accountService.increaseBalance(200, 1l)).thenReturn(true);
        mockMvc.perform(get("/orders/1")
                .queryParam("orderId", String.valueOf(1))
                .queryParam("accountId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order executed success"));
    }

    @Test
    public void shouldReturnAmountOfMoneyAndChangeBalanceNegative() throws Exception {
        when(orderService.executeOrder(1L)).thenReturn(200D);
        when(accountService.reduceBalance(-200, 1l)).thenReturn(true);
        mockMvc.perform(get("/orders/1")
                .queryParam("orderId", String.valueOf(1))
                .queryParam("accountId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order executed success"));
    }

    @Test
    public void shouldReturnAllFinishedOrders() throws Exception {
        Order order1 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, null);
        Order order2 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, null);
        Order order3 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, null);
        Order order4 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, null);
        List<Order> orderList = new ArrayList<>();
        List<Order> listFinishedOrder = new ArrayList<>();
        listFinishedOrder.add(order2);
        listFinishedOrder.add(order4);
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderList.add(order4);

        when(orderService.getAllFinishedOrders()).thenReturn(listFinishedOrder);

        mockMvc.perform(get("/orders/finished"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listFinishedOrder)));


    }

    @Test
    public void shouldReturnAllActiveOrders() throws Exception {
        Order order1 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, null);
        Order order2 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, null);
        Order order3 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, null);
        Order order4 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, null);
        List<Order> orderList = new ArrayList<>();
        List<Order> listActiveOrder = new ArrayList<>();
        listActiveOrder.add(order1);
        listActiveOrder.add(order3);
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderList.add(order4);

        when(orderService.getAllFinishedOrders()).thenReturn(listActiveOrder);

        mockMvc.perform(get("/orders/finished"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listActiveOrder)));


    }
}
