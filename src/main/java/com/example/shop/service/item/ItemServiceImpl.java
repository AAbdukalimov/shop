package com.example.shop.service.item;

import com.example.shop.dto.ItemDto;
import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.repository.item.ItemJpaRepository;
import com.example.shop.repository.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import static com.example.shop.util.Constants.*;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemJpaRepository itemJpaRepository;
    private ItemRepository itemRepository;


    @Override
    @Transactional
    public Item create(Item item) {
        return itemJpaRepository.save(item);
    }

    @Override
    public List<Item> createForList(List<Item> items) {
        return items.stream()
                .map(this::create)
                .toList();
    }

    @Override
    public List<Item> setPayment(List<Item> items, Payment payment) {
        for (Item item : items) {
            item.setPayment(payment);
        }
        return items;
    }

    @Override
    public List<Item> findAll() {
        return itemJpaRepository.findAll();
    }

    @Override
    public Item findById(Long id) {
        return itemJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found item with id: %s", id)));
    }

    @Override
    public Item update(Item item) {
        return itemJpaRepository.save(item);
    }

    @Override
    public void deleteById(Long id) {
        itemJpaRepository.deleteById(id);
    }

    @Override
    public Page<Item> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return itemJpaRepository.findAll(pageable);
    }

    @Override
    public Item toItem(ItemDto itemDto) {
        return Item.builder()
                .quantity(itemDto.getQuantity())
                .amount(itemDto.getAmount())
                .build();
    }

    @Override
    public Item toItem(Product product) {
        return Item.builder()
                .product(product)
                .quantity(product.getQuantity())
                .amount(product.getAmount())
                .build();
    }

    @Override
    public List<Item> toItemList(Set<Product> products) {
        return products.stream()
                .map(this::toItem)
                .toList();
    }

    @Override
    public String formattedPaymentAmount(Double amount) {
        return new DecimalFormat(TWO_DIGITS_AFTER_DOT).format(amount);
    }

    @Override
    public List<Item> findAllByPayments(List<Payment> payments) {
        return itemRepository.findAllByPayments(payments);
    }

}

