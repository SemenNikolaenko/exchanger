package com.example.exchanger.service;

import com.example.exchanger.model.Account;
import com.example.exchanger.model.Order;
import com.example.exchanger.model.param.OrderRequestParam;

import java.util.List;

public interface OrderService {
    public Order addOrderToBuy(OrderRequestParam order,Account account);
    public Order addOrderToSell(OrderRequestParam order, Account account);
    public List<Order> getAllOrders();
    public double executeOrder(Long orderId);
    public List<Order> getAllFinishedOrders();
    public List<Order> getAllActiveOrders();


}
