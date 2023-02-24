package com.example.shop.controllers;

import com.example.shop.entities.User;
import com.example.shop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import static com.example.shop.util.Constants.*;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private UserService userService;

    @GetMapping(PAGE_PATH)
    public String findAll(Model model, @PathVariable(PAGE_NUMBER) int currentPage) {
        Page<User> page = userService.findPage(currentPage);

        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(TOTAL_ITEMS, page.getTotalElements());
        model.addAttribute("users", page.getContent());

        return "users";
    }

    @GetMapping()
    public String getAllPages(Model model) {
        return findAll(model, 1);
    }

    @GetMapping(PATH_ID)
    public User findById(@PathVariable(value = ID) Long id) {
        return userService.findById(id);
    }

    @DeleteMapping(PATH_ID)
    public String delete(@PathVariable(value = ID) Long id) {
        userService.deleteById(id);
        return REDIRECT_TO_USERS_PAGE;
    }

    @GetMapping("/{userName}")
    public String findByUserName(@PathVariable(value = "userName") String userName) {
        userService.findByUserName(userName);
        return REDIRECT_TO_USERS_PAGE;
    }

}
