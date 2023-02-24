package com.example.shop.service.cart;

import com.example.shop.entities.Product;
import com.example.shop.repository.cart.CartJpaRepository;
import com.example.shop.repository.cart.CartRepository;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.Set;
import static com.example.shop.util.Constants.*;

@Slf4j
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private ProductService productService;
    private HttpSession session;
    private Set<Product> cart;

    @Override
    public Product addToCart(Product product) {
        for (Product p : findAll()) {
            if (p.getId().equals(product.getId())) {
                p.setQuantity(p.getQuantity() + ONE_PIECE);
                return p;
            }
        }
        setDefaultQuantity(product);
        productService.setAmount(product);

        cart.add(product);
        session.setAttribute("cart", cart);

        return product;
    }


    @Override
    public Set<Product> findAll() {
        for (Product product : cart) {
            productService.setAmount(product);
        }
        session.setAttribute("cart", cart);
        return cart;
    }

    @Override
    public Product findById(Long id) {
        return cart.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() ->
                        new NotFoundException(String.format("Not found product in cart with id: %s", id)));
    }

    @Override
    public Product update(Product product) {
        product.setAmount(product.getPrice() * product.getQuantity());
        cart.removeIf(p -> p.getId().equals(product.getId()));
        cart.add(product);
        return product;
    }

    @Override
    public void deleteById(Long id) {
        Product toDelete = cart.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException(String.format("Not found product in cart with id: %s", id)));

        cart.remove(toDelete);
    }

    @Override
    public Double totalAmountForPayment(Set<Product> products) {
        return products
                .stream()
                .mapToDouble(Product::getAmount)
                .sum();
    }

    @Override
    public String formattedTotalAmountForPayment(Set<Product> products) {
        return new DecimalFormat(TWO_DIGITS_AFTER_DOT).format(totalAmountForPayment(products));
    }

    @Override
    public void setDefaultQuantity(Product product) {
        product.setQuantity(ONE_PIECE);
    }

    @Override
    public Integer numberProductsOfCart() {
        return findAll()
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();

    }

    @Override
    public void resetCart() {
        cart.clear();
    }

}
