package com.example.exchanger.service.impl;

import com.example.exchanger.exception.NotEnoughMoneyException;
import com.example.exchanger.model.Account;
import com.example.exchanger.model.Order;
import com.example.exchanger.repository.AccountRepository;
import com.example.exchanger.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account getAccountById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public double getBalanceFromAccountById(Long id) {
        return repository.findById(id).get().getBalance();
    }

    @Override
    public boolean increaseBalance(double value, Long accountId) {
        Account account = repository.findById(accountId).get();
        account.setBalance(account.getBalance()+value);
        repository.save(account);
        return  true;
    }

    @Override
    public boolean reduceBalance(double value, Long accountId) throws NotEnoughMoneyException {
        Account account = repository.findById(accountId).get();
        if (account.getBalance()<Math.abs(value))
        {
            throw new NotEnoughMoneyException("Don't enough money for operation");

        }
        else {
            account.setBalance(account.getBalance()+value);
            repository.save(account);
            return  true;
        }

    }

    @Override
    public List<Order> getAllOrders(Long accountId) {
        Account accountWithAllOrdersById = repository.findAccountById(accountId);
        List<Order> orderList = accountWithAllOrdersById.getOrdersList();
        return orderList;

    }
}
