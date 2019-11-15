package com.shop.controller;

import com.shop.model.Basket;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.service.BasketService;
import com.shop.service.MailService;
import com.shop.service.OrderService;
import com.shop.util.CodeGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class OrderController {

    private final MailService mailService;
    private final OrderService orderService;
    private final BasketService basketService;

    @Autowired
    public OrderController(MailService mailService, OrderService orderService,
                           BasketService basketService) {
        this.mailService = mailService;
        this.orderService = orderService;
        this.basketService = basketService;
    }

    @GetMapping("/order")
    public String order(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("address", user.getAddress());
        return "confirm_order";
    }

    @PostMapping("/order")
    public String order(@AuthenticationPrincipal User user) {
        String code = CodeGeneratorUtil.generate();
        mailService.sendCode(code, user.getEmail());
        Basket basket = basketService.getBasketByUser(user);
        Order order = new Order(basket, code);
        orderService.add(order);
        return "redirect:/user/code/send";
    }
}
