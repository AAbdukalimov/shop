package com.example.shop.controllers;

import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.service.payment.PaymentService;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.Set;
import static com.example.shop.util.Constants.CREATE;
import static com.example.shop.util.Constants.CURRENT_PAGE;
import static com.example.shop.util.Constants.ID;
import static com.example.shop.util.Constants.PAGE_NUMBER;
import static com.example.shop.util.Constants.PAGE_PATH;
import static com.example.shop.util.Constants.PATH_ID;
import static com.example.shop.util.Constants.REDIRECT_TO_PRODUCT;
import static com.example.shop.util.Constants.TOTAL_ITEMS;
import static com.example.shop.util.Constants.TOTAL_PAGES;

@Controller
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;
    private ProductService productService;
    private ItemController itemController;
    private HttpSession session;


    @PostMapping(CREATE)
    public String create() {
        Set<Product> cart = (Set<Product>) session.getAttribute("cart");
        if (!productService.checkStock(cart)) {
            return "products-quantity-error";
        }
        Payment payment = paymentService.create(Payment.builder().build());
        session.setAttribute("payment", payment);
        session.setAttribute("date", paymentService.dateFormatter(payment.getDate()));
        return itemController.create();
    }

    @GetMapping(PAGE_PATH)
    public String findAll(Model model, @PathVariable(PAGE_NUMBER) int currentPage) {
        Page<Payment> page = paymentService.findPage(currentPage);
        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("allPayments", page.getContent());
        return "payments";
    }

    @DeleteMapping(PATH_ID)
    public String delete(@PathVariable(value = ID) Long id) {
        paymentService.deleteById(id);
        return "payments";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

}
