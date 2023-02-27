package com.example.shop.controllers;

import com.example.shop.dto.ProductDto;
import com.example.shop.entities.Product;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import static com.example.shop.util.Constants.*;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

    private ProductService productService;

    @PostMapping("/create")
    public String createProduct(@ModelAttribute("productDto") @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-product";
        }
        productService.create(productService.toProduct(productDto));
        return "redirect:/products";
    }

    @GetMapping("/add-product")
    public String form(Model model) {
        model.addAttribute("productDto", ProductDto.builder().build());
        return "add-product";
    }

    @GetMapping("/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") int currentPage) {
        productService.showPage(model, currentPage);
        return "product";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable(value = ID) Long id) {
        return productService.findById(id);
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-product";
        }
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit-product")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute("product", productService.findById(id));
        return "edit-product";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = ID) Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/productsStockAdjustments")
    public List<Product> productsStockAdjustments(Set<Product> cart) {
        return productService.productsStockAdjustments(cart);
    }

}
