package com.example.shop.controllers;

import com.example.shop.entities.Product;
import com.example.shop.service.cart.CartService;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Set;
import static com.example.shop.util.Constants.*;

@Slf4j
@Controller
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private ProductService productService;
    private CartService cartService;
    private HttpSession session;
    private Set<Product> cart;


    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable(value = ID) Long id) {
        session.setAttribute("cart", cart);
        cartService.addToCart(productService.findById(id), cart);
        Double totalAmountForPayment = cartService.totalAmountForPayment(cartService.findAll(cart));
        session.setAttribute("total", totalAmountForPayment);

        return "redirect:/index";
    }

    @GetMapping()
    public String findAll(Model model) {
        cartService.modelViewForCart(model, cart);
        return "cart";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-cart-product";
        }
        cartService.update(product, cart);
        return "redirect:/carts";
    }

    @GetMapping("/{id}/edit-cart-product")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute("product", cartService.findById(id, cart));
        return "edit-cart-product";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = ID) Long id) {
        cartService.deleteById(id, cart);
        return "redirect:/carts";
    }

    @GetMapping("/resetCart")
    public void resetCart() {
        cartService.resetCart(cart);
    }

}
