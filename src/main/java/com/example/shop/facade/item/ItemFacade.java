package com.example.shop.facade.item;

import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Set;

public interface ItemFacade {

    List<Item> saveItem(Set<Product> cart, Payment payment);
    void showPage(Model model, int currentPage);
    void showCheckForOrder(Model model, List<Item>items, String dateTime, Double amount);
    void postPaymentWarehouseFlow(Set<Product> cart);

}
