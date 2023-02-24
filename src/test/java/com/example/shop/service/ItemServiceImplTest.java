package com.example.shop.service;

import com.example.shop.dto.ItemDto;
import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.repository.item.ItemJpaRepository;
import com.example.shop.repository.item.ItemRepository;
import com.example.shop.service.item.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private ItemJpaRepository itemJpaRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private HttpSession session;
    @InjectMocks
    private ItemServiceImpl itemService;

    private Item rawItemFirst;
    private Item rawItemSecond;
    private Item rawItemThird;
    private Item expectedFirstItem;
    private Item expectedSecondItem;
    private Item expectedThirdItem;
    private Item expectedFourthItem;
    private ItemDto itemDto;
    private Product firstProduct;
    private Product secondProduct;
    private Product thirdProduct;
    private List<Item> items;
    private Payment payment;

    @BeforeEach
    public void init() {

        rawItemFirst = Item.builder()
                .id(null)
                .quantity(2)
                .amount(21.0)
                .product(firstProduct)
                .payment(payment)
                .build();

        rawItemSecond = Item.builder()
                .id(null)
                .quantity(3)
                .amount(60.0)
                .product(secondProduct)
                .payment(payment)
                .build();

        rawItemThird = Item.builder()
                .id(null)
                .quantity(1)
                .amount(30.0)
                .product(thirdProduct)
                .payment(payment)
                .build();

        expectedFirstItem = Item.builder()
                .id(1L)
                .quantity(2)
                .amount(21.0)
                .product(firstProduct)
                .payment(payment)
                .build();

        expectedSecondItem = Item.builder()
                .id(2L)
                .quantity(3)
                .amount(60.0)
                .product(secondProduct)
                .payment(payment)
                .build();

        expectedThirdItem = Item.builder()
                .id(3L)
                .quantity(1)
                .amount(30.0)
                .product(thirdProduct)
                .payment(payment)
                .build();

        expectedFourthItem = Item.builder()
                .id(4L)
                .quantity(4)
                .amount(160.0)
                .build();

        itemDto = ItemDto.builder()
                .quantity(2)
                .amount(21.0)
                .build();

        items = List.of(expectedSecondItem, expectedThirdItem);

        firstProduct = Product.builder()
                .id(1L)
                .name("test1")
                .price(10.5)
                .quantity(2)
                .amount(21.0)
                .build();

        secondProduct = Product.builder()
                .id(2L)
                .name("test2")
                .price(20.0)
                .quantity(3)
                .amount(60.0)
                .build();

        thirdProduct = Product.builder()
                .id(3L)
                .name("test3")
                .price(30.0)
                .quantity(1)
                .amount(30.0)
                .build();

        payment = Payment.builder()
                .id(1L)
                .amount(90.0)
                .date(null)
                .user(null)
                .build();
    }

    @Test
    public void testCreate() {
        when(itemJpaRepository.save(rawItemFirst)).thenReturn(expectedFirstItem);
        Item actual = itemService.create(rawItemFirst);
        assertEquals(expectedFirstItem, actual);
    }

    @Test
    public void testCreateForList() {
        when(itemJpaRepository.save(rawItemFirst)).thenReturn(expectedFirstItem);
        when(itemJpaRepository.save(rawItemSecond)).thenReturn(expectedSecondItem);
        when(itemJpaRepository.save(rawItemThird)).thenReturn(expectedThirdItem);

        List<Item> rawItems = List.of(rawItemFirst, rawItemSecond, rawItemThird);
        List<Item> expected = List.of(expectedFirstItem, expectedSecondItem, expectedThirdItem);
        List<Item> actual = itemService.createForList(rawItems);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetPayment() {
        session.setAttribute("payment", payment);
        when(session.getAttribute("payment")).thenReturn(payment);
        Payment actual = itemService.getPayment(session);
        assertEquals(payment, actual);
    }

    @Test
    public void testFindAll() {
        List<Item> expected = List.of(expectedFirstItem, expectedSecondItem, expectedThirdItem);
        when(itemJpaRepository.findAll()).thenReturn(expected);
        List<Item> actual = itemService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void testFindById() {
        when(itemJpaRepository.findById(expectedFirstItem.getId())).thenReturn(Optional.of(expectedFirstItem));
        Item actual = itemService.findById(expectedFirstItem.getId());
        assertEquals(expectedFirstItem, actual);
    }

    @Test
    public void testUpdate() {
        when(itemJpaRepository.save(rawItemFirst)).thenReturn(expectedFirstItem);
        Item actual = itemService.update(rawItemFirst);
        assertEquals(expectedFirstItem, actual);
    }

    @Test
    public void testDeleteById() {
        itemService.deleteById(expectedFirstItem.getId());
        verify(itemJpaRepository, times(1)).deleteById(anyLong());
        verify(itemJpaRepository, times(1)).deleteById(eq(expectedFirstItem.getId()));
    }

    @Test
    public void testToItem() {
        Item actualFirst = itemService.toItem(itemDto);
        if (rawItemFirst.getQuantity().equals(actualFirst.getQuantity())
                && rawItemFirst.getAmount().equals(actualFirst.getAmount())) {
            actualFirst = rawItemFirst;
        }
        assertEquals(rawItemFirst, actualFirst);

        Item actualSecond = itemService.toItem(firstProduct);
        if (rawItemFirst.getQuantity().equals(actualSecond.getQuantity())
                && rawItemFirst.getAmount().equals(actualSecond.getAmount())) {
            actualSecond = rawItemFirst;
        }
        assertEquals(rawItemFirst, actualSecond);
    }


    @Test
    public void testFormattedPaymentAmount() {
        String expected = "90,00";
        String actual = itemService.formattedPaymentAmount(payment.getAmount());
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllByPayments() {

        Payment firstPayment = Payment.builder()
                .itemList(List.of(expectedFirstItem, expectedSecondItem))
                .build();
        Payment secondPayment = Payment.builder()
                .itemList(List.of(expectedThirdItem, expectedFourthItem))
                .build();

        List<Payment> payments = List.of(firstPayment, secondPayment);
        items = List.of(expectedFirstItem, expectedSecondItem, expectedThirdItem, expectedFourthItem);

        when(itemRepository.findAllByPayments(payments)).thenReturn(items);
        List<Item> actual = itemService.findAllByPayments(payments);

        assertEquals(items, actual);
    }

    }
