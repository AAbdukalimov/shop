package com.example.shop.service.cart;


import com.example.shop.entities.Product;
import java.util.Set;

public interface CartService {

    Product addToCart(Product product);

    Set<Product> findAll();

    Product findById(Long id);

    Product update(Product product);

    void deleteById(Long id);

    Double totalAmountForPayment(Set<Product> products);

    String formattedTotalAmountForPayment(Set<Product> products);

    void setDefaultQuantity(Product product);

    Integer numberProductsOfCart();

    void resetCart();

}
