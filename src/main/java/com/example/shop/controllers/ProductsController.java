package com.example.shop.controllers;

import com.example.shop.dto.ProductDto;
import com.example.shop.entities.Product;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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

    @PostMapping(CREATE)
    public String createItem(@ModelAttribute("productDto") @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-product";
        }
        productService.create(productService.toProduct(productDto));
        return REDIRECT_TO_PRODUCT;
    }

    @GetMapping("/add-product")
    public String form(Model model) {
        model.addAttribute("productDto", ProductDto.builder().build());
        return "add-product";
    }

    @GetMapping(PAGE_PATH)
    public String findAll(Model model, @PathVariable(PAGE_NUMBER) int currentPage) {
        Page<Product> page = productService.findPage(currentPage);

        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("products", page.getContent());

        return "product";
    }

    @GetMapping()
    public String getAllPages(Model model){
        return findAll(model, 1);
    }

    @GetMapping(PATH_ID)
    public Product findById(@PathVariable(value = ID) Long id) {
        return productService.findById(id);
    }

    @PatchMapping(PATH_ID)
    public String update(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "edit-product";
        }
        productService.update(product);
        return REDIRECT_TO_PRODUCT;
    }

    @GetMapping("/{id}/edit-product")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute("product", productService.findById(id));
        return "edit-product";
    }

    @DeleteMapping(PATH_ID)
    public String delete(@PathVariable(value = ID) Long id) {
        productService.deleteById(id);
        return REDIRECT_TO_PRODUCT;
    }

    @GetMapping("/productsStockAdjustments")
    public List<Product> productsStockAdjustments(Set<Product> cart) {
        return productService.productsStockAdjustments(cart);
    }

}
