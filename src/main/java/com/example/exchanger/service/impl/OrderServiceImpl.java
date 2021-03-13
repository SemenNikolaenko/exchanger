package com.example.exchanger.service.impl;

import com.example.exchanger.model.Account;
import com.example.exchanger.model.ExchangeAction;
import com.example.exchanger.model.Order;
import com.example.exchanger.model.param.OrderRequestParam;
import com.example.exchanger.repository.OrderRepository;
import com.example.exchanger.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.orderRepository = repository;
    }

    @Override
    public Order addOrderToBuy(OrderRequestParam params, Account account) {
        Order orderToBuy = new Order(params.getConvertFrom(), params.getConvertTo(), params.getAmount(),params.getExchangeRate(), ExchangeAction.BUY,account);
        orderToBuy.setFinished(false);
        orderRepository.save(orderToBuy);
        return orderToBuy;
    }

    @Override
    public Order addOrderToSell(OrderRequestParam params,Account account) {
        Order orderToSell = new Order(params.getConvertFrom(), params.getConvertTo(), params.getAmount(),params.getExchangeRate(), ExchangeAction.SELL,account);
        orderToSell.setFinished(false);
        orderRepository.save(orderToSell);
        return orderToSell;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll() ;
    }

    @Override
    public double executeOrder(Long orderId) {
        Order requiredOrder = orderRepository.findById(orderId).get();
        if (requiredOrder.getExchangeAction().equals(ExchangeAction.SELL)){
            requiredOrder.setFinished(true);
            return requiredOrder.getAmount()*requiredOrder.getExchangeRate();
        }
        else {
            requiredOrder.setFinished(true);
            return 0-(requiredOrder.getAmount()*requiredOrder.getExchangeRate());
        }

    }

    @Override
    public List<Order> getAllFinishedOrders() {
        return orderRepository.findAll().stream()
                .filter(order->order.isFinished())
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllActiveOrders() {
        return orderRepository.findAll().stream()
                .filter(order->!order.isFinished())
                .collect(Collectors.toList());
    }
}
