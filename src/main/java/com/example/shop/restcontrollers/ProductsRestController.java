package com.example.shop.restcontrollers;

import com.example.shop.dto.ProductDto;
import com.example.shop.entities.Product;
import com.example.shop.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Tag(name = "Products REST controller", description = "Products API")
@RequestMapping("/rest/products")
@RestController
@AllArgsConstructor
public class ProductsRestController {

    private final ProductService productService;

    @PostMapping()
    @Operation(summary = "Create product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductDto productDto) {
        return productService.create(productService.toProduct(productDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find product by id")
    @ResponseStatus(HttpStatus.OK)
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping()
    @Operation(summary = "Find all products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PatchMapping()
    @Operation(summary = "Update product")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@Valid @RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/findByName")
    @Operation(summary = "Find product by name")
    @ResponseStatus(HttpStatus.OK)
    public Product findByName(String name){
        return productService.findByName(name);
    }

    @PostMapping("/checkStock")
    @Operation(summary = "Check products stock")
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkStock(@Valid @RequestBody Set<Product> cart) {
        return productService.checkStock(cart);
    }

    @PostMapping("/productsStockAdjustments")
    @Operation(summary = "Products stock adjustments")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> productsStockAdjustments(@Valid @RequestBody Set<Product>cart) {
        return productService.productsStockAdjustments(cart);
    }
}
