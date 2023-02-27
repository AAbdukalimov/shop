package com.example.shop.service.cart;

import com.example.shop.entities.Product;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.webjars.NotFoundException;
import java.text.DecimalFormat;
import java.util.Set;
import static com.example.shop.util.Constants.*;

@Slf4j
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private ProductService productService;

    @Override
    public Product addToCart(Product product, Set<Product> cart) {
        for (Product p : cart) {
            if (p.getId().equals(product.getId())) {
                p.setQuantity(p.getQuantity() + ONE_PIECE);
                return p;
            }
        }
        setDefaultQuantity(product);
        productService.setAmount(product);

        cart.add(product);
        return product;
    }

    @Override
    public Set<Product> findAll(Set<Product> cart) {
        for (Product product : cart) {
            productService.setAmount(product);
        }
        return cart;
    }

    @Override
    public Product findById(Long id, Set<Product> cart) {
        return cart.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() ->
                        new NotFoundException(String.format("Not found product in cart with id: %s", id)));
    }

    @Override
    public Product update(Product product, Set<Product> cart) {
        product.setAmount(product.getPrice() * product.getQuantity());
        cart.removeIf(p -> p.getId().equals(product.getId()));
        cart.add(product);
        return product;
    }

    @Override
    public void deleteById(Long id, Set<Product> cart) {
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
    public void modelViewForCart(Model model, Set<Product> cart) {
        model.addAttribute("cart", cart);
        model.addAttribute("formattedTotal", formattedTotalAmountForPayment(cart));
    }

    @Override
    public Integer numberProductsOfCart(Set<Product> cart) {
        if (cart == null) {
            return ZERO;
        }
        return cart.stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    @Override
    public void resetCart(Set<Product> cart) {
        cart.clear();
    }

}
