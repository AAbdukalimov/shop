package com.example.shop.facade.payment;

import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import org.springframework.ui.Model;

import java.util.Set;

public interface PaymentFacade {

  Payment createPayment(Set<Product>cart, Double amount);
  void showPage(Model model, int currentPage);
}
