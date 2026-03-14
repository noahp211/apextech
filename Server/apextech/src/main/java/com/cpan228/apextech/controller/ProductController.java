package com.cpan228.apextech.controller;

import com.cpan228.apextech.model.Product;
import com.cpan228.apextech.repository.ProductRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "Products";
    }

    @GetMapping("/products/add")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        return "AddProduct";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }
}