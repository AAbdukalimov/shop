package com.example.shop.controllers;

import com.example.shop.entities.Product;
import com.example.shop.service.cart.CartService;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.Set;
import static com.example.shop.util.Constants.*;

@Slf4j
@Controller
@RequestMapping()
@AllArgsConstructor
public class MainController {

    private ProductService productService;
    private CartService cartService;
    private HttpSession session;
    private Set<Product>cart;


    @GetMapping("/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") int currentPage) {
        cart = (Set<Product>) session.getAttribute("cart");
        productService.showPage(model, currentPage);
        model.addAttribute("numberOfCartProduct", cartService.numberProductsOfCart(cart));
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
