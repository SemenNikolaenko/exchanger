package com.example.exchanger.controller.rest;

import com.example.exchanger.exception.NotEnoughMoneyException;
import com.example.exchanger.model.Order;
import com.example.exchanger.model.param.OrderRequestParam;
import com.example.exchanger.service.AccountService;
import com.example.exchanger.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private AccountService accountService;


    @Autowired
    public OrderController(OrderService orderService, AccountService accountService) {
        this.orderService = orderService;
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity createOrder(OrderRequestParam params, Long accountId) {
        Order newOrder = null;
        if (params.getExchangeAction().equalsIgnoreCase("BUY")) {
            newOrder = orderService.addOrderToBuy(params, accountService.getAccountById(accountId));
        }
        if (params.getExchangeAction().equalsIgnoreCase("SELL")) {
            newOrder = orderService.addOrderToSell(params, accountService.getAccountById(accountId));
        }

        if (newOrder != null) return ResponseEntity.ok().body(newOrder);
        else return new ResponseEntity("Error with creating order", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity getAllOrders() {
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok().body(allOrders);
    }

    @GetMapping("/finished")
    public ResponseEntity getAllFinishedOrder() {
        List<Order> allOrders = orderService.getAllFinishedOrders();
        return ResponseEntity.ok().body(allOrders);
    }

    @GetMapping("/active")
    public ResponseEntity getAllActiveOrder() {
        List<Order> allOrders = orderService.getAllActiveOrders();
        return ResponseEntity.ok().body(allOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity executeOrder(@PathVariable("id") Long orderId, Long accountId) throws NotEnoughMoneyException {
        double totalAmountMoneyFromOrder = orderService.executeOrder(orderId);
        if (totalAmountMoneyFromOrder > 0) {
            accountService.increaseBalance(totalAmountMoneyFromOrder, accountId);
            return ResponseEntity.ok().body("Order executed success");

        } else if (totalAmountMoneyFromOrder < 0) {
            accountService.reduceBalance(totalAmountMoneyFromOrder, accountId);
            return ResponseEntity.ok().body("Order executed success");
        } else return ResponseEntity.badRequest().build();
    }
}
