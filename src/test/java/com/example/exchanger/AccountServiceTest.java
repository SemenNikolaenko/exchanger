package com.example.exchanger;

import com.example.exchanger.exception.NotEnoughMoneyException;
import com.example.exchanger.model.Account;
import com.example.exchanger.model.Order;
import com.example.exchanger.repository.AccountRepository;
import com.example.exchanger.service.AccountService;
import com.example.exchanger.service.impl.AccountServiceImpl;
import org.aspectj.weaver.ast.Or;
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
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountServiceImpl service;

    @Test
    public void shouldReturnAccountById() {
        Account account = new Account(1l, "username", "password", 0.0, Collections.emptyList());
        when(repository.findById(any())).thenReturn(Optional.of(account));

        Account requiredAccount = service.getAccountById(1l);

        assertEquals(account, requiredAccount);
    }

    @Test
    public void shouldReturnBalanceFromAccount() {
        Account account = new Account(1l, "username", "password", 275d, Collections.emptyList());
        when(repository.findById(any())).thenReturn(Optional.of(account));

        double balance = service.getBalanceFromAccountById(1l);

        assertEquals(275d, balance);
    }

    @Test
    public void shouldIncreaseBalance() {

        Account account = new Account(1l, "username", "password", 275d, Collections.emptyList());
        when(repository.findById(1l)).thenReturn(Optional.of(account));
        when(repository.save(any())).thenReturn(account);

        service.increaseBalance(125d, 1l);
        double balanceAfterIncrease = account.getBalance();
        double expectedBalance = 400d;

        assertEquals(expectedBalance, balanceAfterIncrease);
    }

    @Test
    public void shouldReduceBalance() throws NotEnoughMoneyException {
        Account account = new Account(1l, "username", "password", 275d, Collections.emptyList());
        when(repository.findById(1l)).thenReturn(Optional.of(account));
        when(repository.save(any())).thenReturn(account);

        service.reduceBalance(-175d, 1l);
        double balanceAfterReduce = account.getBalance();
        double expectedBalance = 100d;

        assertEquals(expectedBalance, balanceAfterReduce);
    }

    @Test
    public void shouldThrowNotEnoughMoneyException() throws NotEnoughMoneyException {
        Account account = new Account(1l, "username", "password", 275d, Collections.emptyList());
        when(repository.findById(1l)).thenReturn(Optional.of(account));


        assertThrows(NotEnoughMoneyException.class, () -> service.reduceBalance(400d, 1l));

    }

    @Test
    public void shouldReturnAllOrdersByAccountId() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        Order order1 = new Order();
        Order order2 = new Order();
        orderList.add(order);
        orderList.add(order1);
        orderList.add(order2);

        Account account = new Account(1l, "username", "password", 275d,orderList);
        when(repository.findAccountById(any())).thenReturn(account);

        order1.setOrderCreator(account);
        order.setOrderCreator(account);
        order2.setOrderCreator(account);

        int sizeOfOrderList = service.getAllOrders(1l).size();

        assertEquals(3, sizeOfOrderList);
    }
}
