package com.cpan228.apextech.controller;

import com.cpan228.apextech.model.Product;
import com.cpan228.apextech.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/products")
    public String viewProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortBy,
            Model model) {

        List<Product> products = productRepository.findAll();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            products = products.stream()
                    .filter(product -> product.getName() != null
                            && product.getName().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        }

        if ("name".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(
                    Product::getName,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
            ));
        } else if ("priceLowHigh".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else if ("priceHighLow".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else if ("stock".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getStock).reversed());
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);

        return "products";
    }

    @GetMapping("/products/add")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        return "addproduct";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }
}