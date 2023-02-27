package com.example.shop.facade.account;

import com.example.shop.entities.User;
import com.example.shop.service.item.ItemService;
import com.example.shop.service.payment.PaymentService;
import com.example.shop.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Slf4j
@Component
@AllArgsConstructor
public class AccountFacadeImpl implements AccountFacade {

    private UserService userService;
    private PaymentService paymentService;
    private ItemService itemService;

    @Override
    public void findAllAccountPayments(Model model, User updatedUser) {
        User currentUser = userService.findByUserName(userService.getCurrentUsername());
        if (currentUser != null) {
            log.debug("F ACC currentUser2: {}", currentUser);
            model.addAttribute("accountPayments", paymentService.findAllByUserId(currentUser.getId()));
        } else {
            log.debug("F ACC updatedUser: {}", updatedUser);
            model.addAttribute("accountPayments", paymentService.findAllByUserId(updatedUser.getId()));
        }
    }

    @Override
    public void findAllAccountItems(Model model, User updatedUser) {
        User currentUser = userService.findByUserName(userService.getCurrentUsername());
        if (currentUser != null) {
            log.debug("F ACC user4: {}", currentUser);
            model.addAttribute("accountItems", itemService.findAllByPayments(paymentService.findAllByUserId(currentUser.getId())));
        } else {
            log.debug("F ACC user4.1: {}", updatedUser);
            model.addAttribute("accountItems", itemService.findAllByPayments(paymentService.findAllByUserId(updatedUser.getId())));
        }
    }

    @Override
    public void getAccount(Model model, User updatedUser) {
        User currentUser = userService.findByUserName(userService.getCurrentUsername());
        if (currentUser != null) {
            log.debug("F ACC user5: {}", currentUser);
            model.addAttribute("user", currentUser);
        } else {
            log.debug("F ACC user5.1: {}", updatedUser);
            model.addAttribute("user", updatedUser);
        }
    }

}
