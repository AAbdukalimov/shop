package com.example.shop.repository.item;

import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import java.util.List;

public interface ItemRepository {
    List<Item> findAllByPayments(List<Payment>payments);
}
