package com.example.shop.service;


import com.example.shop.entities.Product;
import com.example.shop.service.cart.CartServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {


    @Mock
    private Set<Product> cart;
    @Mock
    private HttpSession session;
    @InjectMocks
    private CartServiceImpl cartService;
    private Product firstProduct;
    private Product firstCartProduct;
    private Product secondCartProduct;
    private Product thirdCartProduct;


    @BeforeEach
    public void init() {
        firstProduct = Product.builder()
                .id(1L)
                .name("test1")
                .price(10.5)
                .quantity(10)
                .amount(105.0)
                .build();

        firstCartProduct = Product.builder()
                .id(1L)
                .name("test1")
                .price(10.5)
                .quantity(1)
                .amount(10.5)
                .build();

        secondCartProduct = Product.builder()
                .id(2L)
                .name("test2")
                .price(20.0)
                .quantity(2)
                .amount(40.0)
                .build();

        thirdCartProduct = Product.builder()
                .id(3L)
                .name("test3")
                .price(30.0)
                .quantity(3)
                .amount(90.0)
                .build();

        cart = Set.of(firstCartProduct, secondCartProduct, thirdCartProduct);
        session.setAttribute("cart", cart);

    }

    @Test
    public void testAddToCart() {
        Product actual = cartService.addToCart(firstProduct, cart);
        assertEquals(firstCartProduct, actual);
    }

    @Test
    public void testFindAll() {
        List<Product> expected = List.of(firstCartProduct, secondCartProduct, thirdCartProduct);
        List<Product> actual = new ArrayList<>(cart);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindById() {
        when((Set<Product>) session.getAttribute("cart")).thenReturn(cart);
        Product expected = firstCartProduct;
        Product actual = cartService.findById(firstCartProduct.getId(), cart);
        assertEquals(expected, actual);
    }

}
