package com.example.shop.controllers;


import com.example.shop.entities.User;
import com.example.shop.facade.account.AccountFacade;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.example.shop.util.Constants.*;


@Slf4j
@Controller
@RequestMapping("/account")
@AllArgsConstructor
public class AccountsController {

    private AccountFacade accountFacade;
    private UserService userService;
    private HttpSession session;


    @GetMapping()
    public String account(Model model) {
        accountFacade.getAccount(model, getUpdatedUser());
        return "account";
    }

    @GetMapping("/payments")
    public String findAllAccountPayments(Model model) {
        accountFacade.findAllAccountPayments(model, getUpdatedUser());
        return "account-payments";
    }

    @GetMapping("/items")
    public String findAllAccountItems(Model model) {
        accountFacade.findAllAccountItems(model, getUpdatedUser());
        return "account-items";
    }

    @PatchMapping()
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-user";
        }
        User updatedUser = userService.update(user);
        session.setAttribute("updatedUser", updatedUser);
        return "account";
    }

    @GetMapping("/{id}/edit-user")
    public String edit(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute("user", userService.findById(id));
        return "edit-user";
    }

    User getUpdatedUser(){
        return (User) session.getAttribute("updatedUser");
    }

}
