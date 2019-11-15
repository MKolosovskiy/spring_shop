package com.shop.service.impl;

import com.shop.model.Basket;
import com.shop.model.Product;
import com.shop.model.User;
import com.shop.repository.BasketJpaRepository;
import com.shop.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private final BasketJpaRepository basketJpaRepository;

    @Autowired
    public BasketServiceImpl(BasketJpaRepository basketJpaRepository) {
        this.basketJpaRepository = basketJpaRepository;
    }

    @Override
    public void add(Basket basket) {
        basketJpaRepository.save(basket);
    }

    @Override
    public void add(Basket basket, Product product) {
        basket.getProducts().add(product);
        basketJpaRepository.save(basket);
    }

    @Override
    public void remove(Basket basket) {
        basketJpaRepository.delete(basket);
    }

    @Override
    public void remove(Basket basket, Product product) {
        basket.getProducts().remove(product);
        basketJpaRepository.save(basket);
    }

    @Override
    public void clear(User user) {
       Basket basket = getBasketByUser(user);
       basket.getProducts().clear();
       basketJpaRepository.save(basket);
    }

    @Override
    public Basket getBasketByUser(User user) {
        List<Basket> baskets = basketJpaRepository.getBasketsByUser(user);
        if (baskets.size() > 0){
            return baskets.get(baskets.size() - 1);
        }else {
            return new Basket(user, new ArrayList<>());
        }
    }
}
