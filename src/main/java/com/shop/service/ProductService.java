package com.shop.service;

import com.shop.model.Product;
import java.util.List;

public interface ProductService {
    void add(Product product);
    void edit(Product product);
    void remove(Product product);
    List<Product> getAll();
    Product getById(Long id);
}
