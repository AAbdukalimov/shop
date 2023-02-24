package com.example.shop.controllers;

import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.service.item.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.example.shop.util.Constants.*;

@Controller
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private CartController cartController;
    private ProductsController productsController;
    private Set<Product> cart;
    private ItemService itemService;
    private HttpSession session;
    private List<Item> items;


    @PostMapping(CREATE)
    public String create() {
        cart = (Set<Product>) session.getAttribute("cart");

        items = itemService.createForList(itemService.toItemList(cart));
        session.setAttribute("items", items);
        session.setAttribute("order", items);

        return "redirect:/items/check";
    }

    @GetMapping("/check")
    public String showCheck(Model model) {
        Payment payment = (Payment) session.getAttribute("payment");
        payment.setItemList(items);
        model.addAttribute("order", session.getAttribute("order"));

        model.addAttribute("date", session.getAttribute("date"));
        model.addAttribute("formatted", itemService.formattedPaymentAmount(payment.getAmount()));

        productsController.productsStockAdjustments(cart);
        cartController.resetCart();

        return "order";
    }

    @GetMapping(PAGE_PATH)
    public String findAll(Model model, @PathVariable(PAGE_NUMBER) int currentPage) {
        Page<Item> page = itemService.findPage(currentPage);
        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("pageItems", page.getContent());
        return "items";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

    @GetMapping(PATH_ID)
    public Item findById(@PathVariable(value = ID) Long id) {
        return itemService.findById(id);
    }

    @DeleteMapping(PATH_ID)
    public String deleteById(@PathVariable(value = ID) Long id) {
        itemService.deleteById(id);
        return REDIRECT_TO_ITEMS;
    }

    @PatchMapping(PATH_ID)
    public String update(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items";
        }
        itemService.update(item);
        return REDIRECT_TO_ITEMS;
    }

    @GetMapping("/{id}/edit-item")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute("item", itemService.findById(id));
        return "edit-item";
    }

}
