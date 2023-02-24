package com.example.shop.restcontrollers;

import com.example.shop.entities.Product;
import com.example.shop.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Set;
import static com.example.shop.util.Constants.*;


@Slf4j
@Tag(name = "Cart products REST controller", description = "Cart products API")
@RequestMapping("/rest/cart")
@RestController
@AllArgsConstructor
public class CartRestController {

    private final CartService cartService;

    @PostMapping()
    @Operation(summary = "Add product to cart")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addToCart(@Valid @RequestBody Product product) {
        return cartService.addToCart(product);
    }

    @GetMapping()
    @Operation(summary = "Find all cart products")
    @ResponseStatus(HttpStatus.OK)
    public Set<Product> findAll() {
        return cartService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find cart product by id")
    @ResponseStatus(HttpStatus.OK)
    public Product findById(@PathVariable Long id) {
        return cartService.findById(id);
    }

    @PatchMapping(PATH_ID)
    @Operation(summary = "Update cart product")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@Valid @RequestBody @PathVariable(value = ID) Long id, Integer quantity) {
        Product product = cartService.findById(id);
        product.setQuantity(quantity);
        return cartService.update(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart product by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @RequestBody @PathVariable(value = "id") Long id) {
        cartService.deleteById(id);
    }

}
