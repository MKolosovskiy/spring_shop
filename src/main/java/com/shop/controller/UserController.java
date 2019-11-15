package com.shop.controller;

import com.shop.model.Basket;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.dto.UserDTO;
import com.shop.service.BasketService;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final BasketService basketService;

    @Autowired
    public UserController(UserService userService,  OrderService orderService,
                          BasketService basketService) {
        this.userService = userService;
        this.orderService = orderService;
        this.basketService = basketService;
    }

    @GetMapping("/add")
    public String add() {
        return "add_user";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute UserDTO userDTO, Model model) {
        User user = new User(userDTO);
        String role = user.getRole().toUpperCase();
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty()
                || user.getEmail().isEmpty() || user.getAddress().isEmpty()
                || user.getPassword().isEmpty() || user.getRole().isEmpty()) {
            model.addAttribute("addUserMessage", "An empty field/s");
            model.addAttribute("user", user);
            return "add_user";
        } else if (!(role.equals("ROLE_USER") || role.equals("ROLE_ADMIN"))) {
            model.addAttribute("addUserMessage", "The field 'role' should be named as" +
                    " 'ROLE_ADMIN' or 'ROLE_USER'");
            model.addAttribute("user", user);
            return "add_user";
        } else {
            userService.add(user);
            return "redirect:/admin/user/all";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        model.addAttribute("repeatPassword", user.getPassword());
        return "edit_user";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute UserDTO userDTO, @PathVariable("id") Long id,
                       @RequestParam String repeatPassword, Model model) {
        User user = new User(userDTO);
        String role = user.getRole().toUpperCase();
        model.addAttribute("id", id);
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty()
                || user.getEmail().isEmpty() || user.getAddress().isEmpty()
                || user.getPassword().isEmpty() || user.getRole().isEmpty()) {
            model.addAttribute("editUserMessage", "An empty field/s");
            model.addAttribute("user", user);
            return "edit_user";
        }else if (!(role.equals("ROLE_USER") || role.equals("ROLE_ADMIN"))) {
                model.addAttribute("editUserMessage", "The field 'role' should be named as" +
                        " 'ROLE_ADMIN' or 'ROLE_USER'");
                model.addAttribute("user", user);
                return "add_user";
        } else if (!user.getPassword().equals(repeatPassword)) {
            model.addAttribute("editUserMessage", "These passwords are different");
            model.addAttribute("user", user);
            model.addAttribute("repeatPassword", repeatPassword);
            return "edit_user";
        } else {
            userService.edit(user);
            return "redirect:/admin/user/all";
        }
    }

    @GetMapping("/all")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        Optional<Order> optionalOrder = orderService.getOrderById(user.getId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            orderService.remove(order);
        }
        Basket basket = basketService.getBasketByUser(user);
        basketService.remove(basket);
        userService.remove(user);
        return "redirect:/admin/user/all";
    }
}
