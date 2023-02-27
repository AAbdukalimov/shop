package com.example.shop.service.product;

import com.example.shop.dto.ProductDto;
import com.example.shop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Product create(Product product);

    Product findById(Long id);

    List<Product> findAll();

    Product update(Product product);

    void deleteById(Long id);

    Page<Product> findPage(int pageNumber);

    Product findByName(String name);

    Product toProduct(ProductDto productDto);

    void setAmount(Product product);

    Boolean checkStock(Set<Product> cartProducts);

    List<Product> productsStockAdjustments(Set<Product> cartProducts);
    void showPage(Model model, int currentPage);

}
