package com.example.shop.service;


import com.example.shop.dto.ProductDto;
import com.example.shop.entities.Product;
import com.example.shop.repository.product.ProductRepository;
import com.example.shop.service.product.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private HttpSession session;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product rawProduct;
    private Product firstProduct;
    private Product secondProduct;
    private Product thirdProduct;
    private List<Product> products;
    private Product firstCartProduct;
    private Product secondCartProduct;
    private Product thirdCartProduct;
    private Set<Product> cart;


    @BeforeEach
    public void init() {
        rawProduct = Product.builder()
                .id(null)
                .name("test")
                .price(10.99)
                .quantity(10)
                .amount(109.90)
                .build();

        firstProduct = Product.builder()
                .id(1L)
                .name("test1")
                .price(10.5)
                .quantity(10)
                .amount(105.0)
                .build();

        secondProduct = Product.builder()
                .id(2L)
                .name("test2")
                .price(20.0)
                .quantity(10)
                .amount(200.0)
                .build();

        thirdProduct = Product.builder()
                .id(3L)
                .name("test3")
                .price(30.0)
                .quantity(10)
                .amount(300.0)
                .build();

        products = List.of(firstProduct, secondProduct, thirdProduct);

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
    public void testCreate() {
        when(productRepository.save(rawProduct)).thenReturn(firstProduct);
        Product actual = productService.create(rawProduct);
        assertEquals(firstProduct, actual);
    }

    @Test
    void testFindById() {
        when(productRepository.findById(firstProduct.getId())).thenReturn(Optional.of(firstProduct));
        Product actual = productService.findById(firstProduct.getId());
        assertEquals(firstProduct, actual);
    }

    @Test
    public void testFindAll() {
        when(productRepository.findAll()).thenReturn(products);
        List<Product> actual = productService.findAll();
        assertEquals(products, actual);
    }

    @Test
    public void testUpdate() {
        Product updatedProduct = Product.builder()
                .id(1L)
                .name("test1")
                .price(10.0)
                .quantity(20)
                .build();

        when(productRepository.save(firstProduct)).thenReturn(updatedProduct);
        Product actual = productService.update(firstProduct);
        assertEquals(updatedProduct, actual);
    }

    @Test
    public void testDeleteById() {
        productService.deleteById(firstProduct.getId());
        verify(productRepository, times(1)).deleteById(anyLong());
        verify(productRepository, times(1)).deleteById(eq(firstProduct.getId()));
    }

    @Test
    public void testFindByName() {
        when(productRepository.findByName(firstProduct.getName())).thenReturn(firstProduct);
        Product actual = productService.findByName(firstProduct.getName());
        assertEquals(firstProduct, actual);
    }

    @Test
    public void testToProduct() {
        ProductDto productDto = ProductDto.builder()
                .name("test")
                .price(10.99)
                .quantity(10)
                .build();

        Product actual = productService.toProduct(productDto);

        if (rawProduct.getName().equals(actual.getName())
                && rawProduct.getPrice().equals(actual.getPrice())
                && rawProduct.getQuantity().equals(actual.getQuantity())) {
            actual = rawProduct;
        }
        assertEquals(rawProduct, actual);
    }

    @Test
    public void testCheckStock() {
        when(productRepository.findAll()).thenReturn(products);

        Boolean expected = true;
        Boolean actual = productService.checkStock(cart);

        assertEquals(expected, actual);
    }

    @Test
    public void testProductsStockAdjustments() {
        when(productRepository.findAll()).thenReturn(products);

        firstProduct.setQuantity(firstProduct.getQuantity() - firstCartProduct.getQuantity());
        secondProduct.setQuantity(secondProduct.getQuantity() - secondCartProduct.getQuantity());
        thirdProduct.setQuantity(thirdProduct.getQuantity() - thirdCartProduct.getQuantity());

        List<Product> expected = List.of(firstProduct, secondProduct, thirdProduct);
        List<Product> actual = productService.productsStockAdjustments(cart);

        assertEquals(expected, actual);
    }

}


