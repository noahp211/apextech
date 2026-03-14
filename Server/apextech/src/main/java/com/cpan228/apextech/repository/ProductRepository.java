package com.cpan228.apextech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cpan228.apextech.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}