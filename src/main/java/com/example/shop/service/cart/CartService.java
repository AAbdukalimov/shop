package com.example.shop.service.cart;


import com.example.shop.entities.Product;
import org.springframework.ui.Model;

import java.util.Set;

public interface CartService {

    Product addToCart(Product product, Set<Product> cart);
    Set<Product> findAll(Set<Product>cart);
    Product findById(Long id, Set<Product>cart);
    Product update(Product product, Set<Product>cart);
    void deleteById(Long id, Set<Product>cart);
    Double totalAmountForPayment(Set<Product> products);
    String formattedTotalAmountForPayment(Set<Product> products);
    void setDefaultQuantity(Product product);
    void modelViewForCart(Model model, Set<Product>cart);
    Integer numberProductsOfCart(Set<Product>cart);
    void resetCart(Set<Product>cart);

}
