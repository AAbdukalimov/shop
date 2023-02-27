package com.example.shop.controllers;

import com.example.shop.entities.Item;
import com.example.shop.entities.Payment;
import com.example.shop.entities.Product;
import com.example.shop.facade.item.ItemFacade;
import com.example.shop.service.item.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {


    private ItemService itemService;
    private ItemFacade itemFacade;
    private HttpSession session;
    private Set<Product> cart;
    private List<Item> items;


    @PostMapping("/create")
    public String create() {
        cart = (Set<Product>) session.getAttribute("cart");
        Payment payment = (Payment) session.getAttribute("payment");

        items = itemFacade.saveItem(cart, payment);
        return "redirect:/items/check";
    }

    @GetMapping("/check")
    public String showCheck(Model model) {
        String dateTime = (String) session.getAttribute("date");
        Double amount = ((Payment) session.getAttribute("payment")).getAmount();

        itemFacade.showCheckForOrder(model, items, dateTime, amount);
        itemFacade.postPaymentWarehouseFlow(cart);

        return "order";
    }

    @GetMapping("/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") int currentPage) {
        itemFacade.showPage(model, currentPage);
        return "items";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable(value = ID) Long id) {
        return itemService.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(value = ID) Long id) {
        itemService.deleteById(id);
        return "redirect:/items";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items";
        }
        itemService.update(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit-item")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute("item", itemService.findById(id));
        return "edit-item";
    }

}
