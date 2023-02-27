package com.example.shop.facade.item;

import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.service.cart.CartService;
import com.example.shop.service.item.ItemService;
import com.example.shop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

import static com.example.shop.util.Constants.CURRENT_PAGE;
import static com.example.shop.util.Constants.TOTAL_ITEMS;
import static com.example.shop.util.Constants.TOTAL_PAGES;

@Component
@AllArgsConstructor
public class ItemFacadeImpl implements ItemFacade{

    private ProductService productService;
    private ItemService itemService;
    private CartService cartService;

    @Override
    public List<Item> saveItem(Set<Product> cart, Payment payment) {
     List<Item> items = itemService.toItemList(cart);
     itemService.setPayment(items, payment);
        return itemService.createForList(items);
    }

    @Override
    public void showPage(Model model, int currentPage) {
        Page<Item> page = itemService.findPage(currentPage);
        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("pageItems", page.getContent());
    }

    @Override
    public void showCheckForOrder(Model model, List<Item> items, String dateTime, Double amount) {
        model.addAttribute("order", items);
        model.addAttribute("date", dateTime);
        model.addAttribute("formatted", itemService.formattedPaymentAmount(amount));
    }

    @Override
    public void postPaymentWarehouseFlow(Set<Product> cart) {
        productService.productsStockAdjustments(cart);
        cartService.resetCart(cart);
    }

}
