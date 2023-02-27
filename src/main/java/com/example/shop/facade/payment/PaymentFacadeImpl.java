package com.example.shop.facade.payment;

import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.exceptions.QuantityOfProductsInStockException;
import com.example.shop.facade.payment.PaymentFacade;
import com.example.shop.service.payment.PaymentService;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Set;

import static com.example.shop.util.Constants.CURRENT_PAGE;
import static com.example.shop.util.Constants.TOTAL_ITEMS;
import static com.example.shop.util.Constants.TOTAL_PAGES;

@Component
@AllArgsConstructor
public class PaymentFacadeImpl implements PaymentFacade {

    private PaymentService paymentService;
    private ProductService productService;

    public Payment createPayment(Set<Product> cart, Double amount) {
        if (!productService.checkStock(cart)) {
            throw new QuantityOfProductsInStockException("The quantity of products in the stock is not enough to form this order!");
        }
        return paymentService.create(Payment.builder().amount(amount).build());
    }

    @Override
    public void showPage(Model model, int currentPage) {
        Page<Payment> page = paymentService.findPage(currentPage);
        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("allPayments", page.getContent());
    }

}
