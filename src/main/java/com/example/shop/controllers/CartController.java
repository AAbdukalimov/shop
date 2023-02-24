package com.example.shop.controllers;

import com.example.shop.entities.Product;
import com.example.shop.service.cart.CartService;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
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
        cartService.addToCart(productService.findById(id));
        cart = (Set<Product>) session.getAttribute("cart");
        cartService.findAll();
        session.setAttribute("total", cartService.totalAmountForPayment(cart));
        return "redirect:/index";
    }

    @GetMapping()
    public String findAll(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("formattedTotal", cartService.formattedTotalAmountForPayment(cart));
        return "cart";
    }

    @PatchMapping(PATH_ID)
    public String update(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-cart-product";
        }
        cartService.update(product);
        return REDIRECT_TO_CART;
    }

    @GetMapping("/{id}/edit-cart-product")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute("product", cartService.findById(id));
        return "edit-cart-product";
    }

    @DeleteMapping(PATH_ID)
    public String delete(@PathVariable(value = ID) Long id) {
        cartService.deleteById(id);
        return REDIRECT_TO_CART;
    }

    @GetMapping("/resetCart")
    public void resetCart() {
        cartService.resetCart();
    }

}
