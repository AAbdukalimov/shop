package com.example.shop.facade.account;


import com.example.shop.entities.User;
import org.springframework.ui.Model;

public interface AccountFacade {


    void findAllAccountPayments(Model model, User updatedUser);
    void findAllAccountItems(Model model, User updatedUser);
    void getAccount(Model model, User updatedUser);
}
