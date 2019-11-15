package com.shop.service.impl;

import com.shop.model.Basket;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.repository.OrderJpaRepository;
import com.shop.service.BasketService;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final BasketService basketService;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderJpaRepository orderJpaRepository, BasketService basketService,
                            UserService userService) {
        this.orderJpaRepository = orderJpaRepository;
        this.basketService = basketService;
        this.userService = userService;
    }

    @Override
    public void add(Order order) {
        orderJpaRepository.save(order);
    }

    @Override
    public void save(Order order) {
        orderJpaRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        List<Order> orders = orderJpaRepository.getOrdersById(id); //0
        if (orders.size() > 0) {
            return Optional.of(orders.get(orders.size() - 1));
        }
        else {
            User user = userService.getById(id);
            Basket basket = basketService.getBasketByUser(user);
            return Optional.of(new Order(basket, ""));
        }
    }

    @Override
    public void remove(Order order) {
        orderJpaRepository.delete(order);
    }

    @Override
    public void confirm(Order order) {
        order.setConfirm(true);
        orderJpaRepository.save(order);
    }
}
