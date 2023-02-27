package com.example.shop.service.product;

import com.example.shop.dto.ProductDto;
import com.example.shop.entities.Product;
import com.example.shop.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.webjars.NotFoundException;
import java.util.List;
import java.util.Set;
import static com.example.shop.util.Constants.CURRENT_PAGE;
import static com.example.shop.util.Constants.TOTAL_ITEMS;
import static com.example.shop.util.Constants.TOTAL_PAGES;
import static com.example.shop.util.Constants.ZERO;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        setAmount(product);
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        (String.format("Not found item with id: %s", id)));
    }

    @Override
    public List<Product> findAll() {
        List<Product> all = productRepository.findAll();
        for (Product product : all) {
            setAmount(product);
        }
        return all;
    }

    @Override
    public Product update(Product product) {
        setAmount(product);
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product toProduct(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();
    }

    @Override
    public void setAmount(Product product) {
        product.setAmount(product.getPrice() * product.getQuantity());
    }

    @Override
    public Boolean checkStock(Set<Product> cartProducts) {
        List<Product> stocks = findAll();
        for (Product stock : stocks) {
            for (Product cartProduct : cartProducts) {
                if (stock.getId().equals(cartProduct.getId()) && (stock.getAmount() - cartProduct.getAmount()) < ZERO) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Product> productsStockAdjustments(Set<Product> cartProducts) {
        List<Product> stocks = findAll();
        for (Product stock : stocks) {
            for (Product cartProduct : cartProducts) {
                if (stock.getId().equals(cartProduct.getId())) {
                    stock.setQuantity(stock.getQuantity() - cartProduct.getQuantity());
                    setAmount(stock);
                    productRepository.save(stock);
                }
            }
        }
        return stocks;
    }

    @Override
    public void showPage(Model model, int currentPage) {
        Page<Product> page = findPage(currentPage);
        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("products", page.getContent());
    }

}
