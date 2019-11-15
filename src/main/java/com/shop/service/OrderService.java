package com.shop.service;

import com.shop.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void add(Order order);
    void save(Order order);
    void remove (Order order);
    void confirm(Order order);
    Optional<Order> getOrderById(Long id);
}