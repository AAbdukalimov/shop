package com.example.shop.controllers;

import com.example.shop.entities.User;
import com.example.shop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import static com.example.shop.util.Constants.*;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private UserService userService;

    @GetMapping("/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") int currentPage) {
        userService.showPage(model, currentPage);
        return "users";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable(value = ID) Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = ID) Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/{userName}")
    public String findByUserName(@PathVariable(value = "userName") String userName) {
        userService.findByUserName(userName);
        return "redirect:/users";
    }

}
