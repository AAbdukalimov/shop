package com.example.shop.repository.product;

import com.example.shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
Product findByName(String name);
}
