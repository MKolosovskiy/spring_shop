package com.shop.service;

import com.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);
    void edit(User user);
    void remove(User user);
    List<User> getAll();
    User getById(Long id);
    Optional<User> getByEmail(String email);
}
