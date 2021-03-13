package com.example.exchanger;

import com.example.exchanger.model.Account;
import com.example.exchanger.model.ExchangeAction;
import com.example.exchanger.model.Order;
import com.example.exchanger.model.param.OrderRequestParam;
import com.example.exchanger.repository.OrderRepository;
import com.example.exchanger.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;
    @InjectMocks
    private OrderServiceImpl service;

    @Test
    public void shouldReturnReadyOrderToBuy() {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        OrderRequestParam params = new OrderRequestParam("buy", "usd", 23.2, "eur", 10d);

        when(repository.save(any())).thenReturn(new Order());

        Order order = service.addOrderToBuy(params, account);
        assertAll(
                () -> assertEquals("usd", order.getFromCurrency()),
                () -> assertEquals("eur", order.getToCurrency()),
                () -> assertEquals(10d, order.getAmount()),
                () -> assertEquals(23.2, order.getExchangeRate()),
                () -> assertFalse(order.isFinished()),
                () -> assertEquals(ExchangeAction.BUY, order.getExchangeAction())
        );
    }

    @Test
    public void shouldReturnReadyOrderToSell() {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        OrderRequestParam params = new OrderRequestParam("sell", "usd", 23.2, "eur", 10d);

        when(repository.save(any())).thenReturn(new Order());

        Order order = service.addOrderToSell(params, account);
        assertAll(
                () -> assertEquals("usd", order.getFromCurrency()),
                () -> assertEquals("eur", order.getToCurrency()),
                () -> assertEquals(10d, order.getAmount()),
                () -> assertEquals(23.2, order.getExchangeRate()),
                () -> assertFalse(order.isFinished()),
                () -> assertEquals(ExchangeAction.SELL, order.getExchangeAction())
        );
    }

    @Test
    public void shouldReturnOrderList() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        Order order1 = new Order();
        Order order2 = new Order();
        orderList.add(order);
        orderList.add(order1);
        orderList.add(order2);

        when(repository.findAll()).thenReturn(orderList);

        int listSize = service.getAllOrders().size();

        assertEquals(3, listSize);
    }

    @Test
    public void shouldExecuteOrder() {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        Order orderToBuy = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, account);
        Order orderToSell = new Order(2l, "usd", "eur", 10d, 2, false, ExchangeAction.SELL, account);
        when(repository.findById(1L)).thenReturn(Optional.of(orderToBuy));
        when(repository.findById(2L)).thenReturn(Optional.of(orderToSell));

        double amountOfMoneyToBuy = service.executeOrder(1L);
        double amountOfMoneyToSell = service.executeOrder(2L);

        assertAll(
                () -> assertEquals(20, amountOfMoneyToSell),
                () -> assertEquals(-20, amountOfMoneyToBuy)
        );
    }

    @Test
    public void shouldReturnAllActiveOrders() {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        Order orderToBuy1 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, account);
        Order orderToBuy2 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, account);
        Order orderToBuy3 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, account);
        Order orderToBuy4 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, account);
        List<Order> ordersList = new ArrayList<>();
        ordersList.add(orderToBuy1);
        ordersList.add(orderToBuy2);
        ordersList.add(orderToBuy3);
        ordersList.add(orderToBuy4);
        when(repository.findAll()).thenReturn(ordersList);

        int sizeOfListActiveOrders = service.getAllActiveOrders().size();

        assertEquals(2, sizeOfListActiveOrders);

    }

    @Test
    public void shouldReturnAllFinishedOrders() {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        Order orderToBuy1 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, account);
        Order orderToBuy2 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, account);
        Order orderToBuy3 = new Order(1l, "usd", "eur", 10d, 2, true, ExchangeAction.BUY, account);
        Order orderToBuy4 = new Order(1l, "usd", "eur", 10d, 2, false, ExchangeAction.BUY, account);
        List<Order> ordersList = new ArrayList<>();
        ordersList.add(orderToBuy1);
        ordersList.add(orderToBuy2);
        ordersList.add(orderToBuy3);
        ordersList.add(orderToBuy4);
        when(repository.findAll()).thenReturn(ordersList);

        int sizeOfListActiveOrders = service.getAllFinishedOrders().size();

        assertEquals(2, sizeOfListActiveOrders);

    }
}
