package com.shop.service.impl;

import com.shop.model.Product;
import com.shop.repository.ProductJpaRepository;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productJpaRepository;

    @Autowired
    public ProductServiceImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public void add(Product product) {
        productJpaRepository.save(product);
    }

    @Override
    public void edit(Product product) {
        productJpaRepository.saveAndFlush(product);
    }

    @Override
    public void remove(Product product) {
        productJpaRepository.delete(product);
    }

    @Override
    public Product getById(Long id) {
        return productJpaRepository.getOne(id);
    }

    @Override
    public List<Product> getAll() {
        return productJpaRepository.findAll();
    }
}
