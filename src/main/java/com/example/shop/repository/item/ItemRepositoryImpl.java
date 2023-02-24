package com.example.shop.repository.item;

import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private EntityManager entityManager;
    @Override
    public List<Item> findAllByPayments(List<Payment> payments) {
        List<Item> items = new ArrayList<>();
        for (Payment payment : payments) {
            Long id = payment.getId();
            Query query = entityManager.createNativeQuery("SELECT * FROM item WHERE payment_id = :paymentId", Item.class);
            query.setParameter("paymentId", id);
            items.addAll(query.getResultList());
        }
        return items;
    }

}
