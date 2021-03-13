package com.example.exchanger.controller.rest;

import com.example.exchanger.model.Account;
import com.example.exchanger.model.Order;
import com.example.exchanger.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Account getAccount(@RequestParam Long id) {
        Account accountById = accountService.getAccountById(id);
        return accountById;
    }


    @GetMapping("/balance")
    public ResponseEntity getBalanceFromAccount(@RequestParam Long id) {
        double balance = accountService.getBalanceFromAccountById(id);
        return ResponseEntity.ok()
                .body(balance);
    }
    @GetMapping("/{id}/orders")
    public ResponseEntity getOrdersForAccount(@PathVariable Long id) {
        List<Order> allOrders = accountService.getAllOrders(id);
        return ResponseEntity.ok().body(allOrders);
    }


}
