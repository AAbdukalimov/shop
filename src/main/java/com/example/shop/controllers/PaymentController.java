package com.example.shop.controllers;

import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.facade.payment.PaymentFacadeImpl;
import com.example.shop.service.cart.CartService;
import com.example.shop.service.payment.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Set;

import static com.example.shop.util.Constants.ID;

@Slf4j
@Controller
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {

    private PaymentFacadeImpl paymentFacade;
    private PaymentService paymentService;
    private CartService cartService;
    private ItemController itemController;
    private HttpSession session;
    private Set<Product> cart;


    @PostMapping("/create")
    public String create() {
        cart = (Set<Product>) session.getAttribute("cart");
        Double total = cartService.totalAmountForPayment(cart);
        Payment payment = paymentFacade.createPayment(cart, total);

        session.setAttribute("payment", payment);
        session.setAttribute("date", paymentService.dateFormatter(payment.getDate()));

        return itemController.create();
    }

    @GetMapping("/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") int currentPage) {
        paymentFacade.showPage(model, currentPage);
        return "payments";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(value = ID) Long id) {
        paymentService.deleteById(id);
        return "redirect:/payments";
    }

}
