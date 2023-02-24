package com.example.shop.controllers;

import com.example.shop.entities.Product;
import com.example.shop.service.cart.CartService;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.example.shop.util.Constants.*;

@Controller
@RequestMapping()
@AllArgsConstructor
public class MainController {

    private ProductService productService;
    private CartService cartService;


    @GetMapping(PAGE_PATH)
    public String findAll(Model model, @PathVariable(PAGE_NUMBER) int currentPage) {
        Page<Product> page = productService.findPage(currentPage);

        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("products", page.getContent());
        model.addAttribute("numberOfCartProduct", cartService.numberProductsOfCart());

        return "index";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

    @GetMapping("/index")
    public String index(Model model) {
           return findAll(model, 1);
    }

}
