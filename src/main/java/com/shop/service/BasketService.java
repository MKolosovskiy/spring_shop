package com.shop.service;

import com.shop.model.Basket;
import com.shop.model.Product;
import com.shop.model.User;

public interface BasketService {
    void add(Basket basket);
    void add(Basket basket, Product product);
    void remove(Basket basket);
    void remove(Basket basket, Product product);
    void clear(User user);
    Basket getBasketByUser(User user);
}
