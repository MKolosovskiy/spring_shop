package com.shop.controller;

import com.shop.model.Product;
import com.shop.dto.ProductDTO;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String add() {
        return "add_product";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute ProductDTO productDTO, Model model) {
        Product product = new Product(productDTO);
        if (product.getName().isEmpty() || product.getDescription().isEmpty()) {
            model.addAttribute("addProductMessage", "An empty field/s");
            model.addAttribute("product", product);
            return "edit_product";
        } else if (product.getPrice() < 0) {
            model.addAttribute("addProductMessage", "The price must be bigger or equals 0");
            model.addAttribute("product", product);
            return "edit_product";
        } else {
            productService.add(product);
            return "redirect:/admin/product/all";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute ProductDTO productDTO, @PathVariable("id") Long id, Model model) {
        Product product = new Product(productDTO);
        model.addAttribute("id", id);
        if (product.getName().isEmpty() || product.getDescription().isEmpty()) {
            model.addAttribute("editProductMessage", "An empty field/s");
            model.addAttribute("product", product);
            return "edit_product";
        } else if (product.getPrice() < 0) {
            model.addAttribute("editProductMessage", "The price must be bigger or equals 0");
            model.addAttribute("product", product);
            return "edit_product";
        } else {
            productService.edit(product);
            return "redirect:/admin/product/all";
        }
    }

    @GetMapping("/all")
    public String products(Model model) {
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        Product product = productService.getById(id);
        productService.remove(product);
        return "redirect:/admin/product/all";
    }
}
