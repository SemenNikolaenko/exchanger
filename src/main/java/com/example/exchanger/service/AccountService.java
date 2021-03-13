package com.example.exchanger.service;

import com.example.exchanger.exception.NotEnoughMoneyException;
import com.example.exchanger.model.Account;
import com.example.exchanger.model.Order;

import java.util.List;

public interface AccountService {
    public Account getAccountById(Long id);
    public double getBalanceFromAccountById(Long id);
    public boolean increaseBalance(double value,Long accountId);
    public boolean reduceBalance(double value,Long accountId) throws NotEnoughMoneyException;
    public List<Order> getAllOrders(Long accountId);
}
