package com.example.shop.controllers;

import com.example.shop.entities.Payment;
import com.example.shop.entities.User;
import com.example.shop.service.item.ItemService;
import com.example.shop.service.payment.PaymentService;
import com.example.shop.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static com.example.shop.util.Constants.ID;
import static com.example.shop.util.Constants.REDIRECT_TO_ACCOUNT;
import static com.example.shop.util.Constants.USER;

@Slf4j
@Controller
@RequestMapping("/account")
@AllArgsConstructor
public class AccountsController {
    private UserService userService;
    private PaymentService paymentService;
    private ItemService itemService;


    @GetMapping()
    public String account(Model currentUser) {
        User user = userService.findByUserName(userService.getCurrentUsername());
        currentUser.addAttribute(USER, user);
        return "account";
    }

    @GetMapping("/payments")
    public String getPayments(Model model) {
        User user = userService.findByUserName(userService.getCurrentUsername());
        model.addAttribute("accountPayments", paymentService.findAllByUserId(user.getId()));
        return "account-payments";
    }

    @GetMapping("/items")
    public String findAllAccountItems(Model model) {
        User user = userService.findByUserName(userService.getCurrentUsername());
        List<Payment> payments = paymentService.findAllByUserId(user.getId());
        model.addAttribute("accountItems", itemService.findAllByPayments(payments));

        return "account-items";
    }

    @PatchMapping()
    public String update(@ModelAttribute(USER) @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-user";
        }
        userService.update(user);
        return REDIRECT_TO_ACCOUNT;
    }

    @GetMapping("/{id}/edit-user")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute(USER, userService.findById(id));
        return "edit-user";
    }

}
