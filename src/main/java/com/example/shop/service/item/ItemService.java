package com.example.shop.service.item;


import com.example.shop.dto.ItemDto;
import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public interface ItemService {

    Item create(Item item);
    List<Item> createForList(List<Item> items);
    List<Item>setPayment(List<Item>items, Payment payment);
    List<Item> findAll();
    Item findById(Long id);
    Item update(Item item);
    void deleteById(Long id);
    Page<Item> findPage(int pageNumber);
    Item toItem(ItemDto itemDto);
    Item toItem(Product product);
    List<Item> toItemList(Set<Product>products);
    String formattedPaymentAmount(Double amount);
    List<Item> findAllByPayments(List<Payment> payments);

}
