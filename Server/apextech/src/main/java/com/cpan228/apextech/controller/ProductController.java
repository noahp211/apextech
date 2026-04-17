package com.cpan228.apextech.controller;

import com.cpan228.apextech.model.Product;
import com.cpan228.apextech.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    private static final List<String> BRANDS = List.of("Apple", "Samsung", "Lenovo", "Sony");
    private static final List<String> CATEGORIES = List.of("Laptop", "Phone", "Tablet", "Accessory");

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/about")
    public String about() {
        return "About";
    }

    @GetMapping("/products")
    public String viewProducts(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String brand,
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "nameAsc") String sortBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            Model model) {

        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (!keyword.isBlank()) {
            String searchValue = "%" + keyword.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name").as(String.class)), searchValue));
        }

        if (!brand.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("brand").as(String.class)), brand.toLowerCase()));
        }

        if (!category.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("category").as(String.class)), category.toLowerCase()));
        }

        Sort sort = getSort(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(spec, pageable);

        model.addAttribute("productPage", productPage);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("brand", brand);
        model.addAttribute("category", category);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("brands", BRANDS);
        model.addAttribute("categories", CATEGORIES);

        return "Products";
    }

    @GetMapping("/products/add")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("brands", BRANDS);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("errors", new LinkedHashMap<String, String>());
        return "AddProduct";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product, Model model) {
        Map<String, String> errors = validateProduct(product);

        if (!errors.isEmpty()) {
            model.addAttribute("product", product);
            model.addAttribute("brands", BRANDS);
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("errors", errors);
            return "AddProduct";
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/admin/products")
    public String adminProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "AdminProducts";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();

        model.addAttribute("product", product);
        model.addAttribute("brands", BRANDS);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("errors", new LinkedHashMap<String, String>());

        return "EditProduct";
    }

    @PostMapping("/admin/products/edit")
    public String updateProduct(@ModelAttribute Product product, Model model) {
        Map<String, String> errors = validateProduct(product);

        if (!errors.isEmpty()) {
            model.addAttribute("product", product);
            model.addAttribute("brands", BRANDS);
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("errors", errors);
            return "EditProduct";
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    private Map<String, String> validateProduct(Product product) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            errors.put("name", "Product name is required.");
        }

        if (product.getBrand() == null || !BRANDS.contains(product.getBrand())) {
            errors.put("brand", "Please select a valid brand.");
        }

        if (product.getCategory() == null || !CATEGORIES.contains(product.getCategory())) {
            errors.put("category", "Please select a valid category.");
        }

        if (product.getPrice() <= 0) {
            errors.put("price", "Price must be greater than 0.");
        } else if (product.getPrice() > 10000) {
            errors.put("price", "Price must be 10000 or less.");
        }

        if (product.getStock() < 0) {
            errors.put("stock", "Stock cannot be negative.");
        } else if (product.getStock() > 1000) {
            errors.put("stock", "Stock must be 1000 or less.");
        }

        return errors;
    }

    private Sort getSort(String sortBy) {
        switch (sortBy) {
            case "nameDesc":
                return Sort.by("name").descending();
            case "priceAsc":
                return Sort.by("price").ascending();
            case "priceDesc":
                return Sort.by("price").descending();
            case "stockAsc":
                return Sort.by("stock").ascending();
            case "stockDesc":
                return Sort.by("stock").descending();
            default:
                return Sort.by("name").ascending();
        }
    }
}