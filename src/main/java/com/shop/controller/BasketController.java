package com.shop.controller;

import com.shop.model.Basket;
import com.shop.model.Product;
import com.shop.model.User;
import com.shop.service.BasketService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class BasketController {

    private final BasketService basketService;
    private final ProductService productService;

    @Autowired
    public BasketController(BasketService basketService, ProductService productService) {
        this.basketService = basketService;
        this.productService = productService;
    }

    @GetMapping("/basket")
    public String basket(@AuthenticationPrincipal User user, Model model) {
        Basket basket = basketService.getBasketByUser(user);
        model.addAttribute("products", basket.getProducts());
        model.addAttribute("size", basket.getProducts().size());
        return "basket";
    }

    @GetMapping("/product/all")
    public String buy(@AuthenticationPrincipal User user, Model model) {
        Basket basket = basketService.getBasketByUser(user);
        model.addAttribute("size", basket.getProducts().size());
        model.addAttribute("products", productService.getAll());
        return "buy_product";
    }

    @GetMapping("/product/buy/{id}")
    public String buy(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        Product product = productService.getById(id);
        Basket basket = basketService.getBasketByUser(user);
        basketService.add(basket, product);
        return "redirect:/user/product/all";
    }

    @GetMapping("/basket/product/remove/{id}")
    public String remove(@AuthenticationPrincipal User user, @PathVariable("id") Long id, Model model) {
        Basket basket = basketService.getBasketByUser(user);
        Product product = productService.getById(id);
        basketService.remove(basket, product);
        model.addAttribute("products", basket.getProducts());
        model.addAttribute("size", basket.getProducts().size());
        return "basket";
    }
}
