package com.example.shop.controllers;

import com.example.shop.dto.authorize.RegistrationRequest;
import com.example.shop.entities.User;
import com.example.shop.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Slf4j
@Controller
@AllArgsConstructor
public class PublicController {

    private UserService userService;


    @ModelAttribute("registrationRequest")
    public RegistrationRequest getRegistrationRequest() {
        return new RegistrationRequest();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationRequest registrationRequest, BindingResult bindingResult) {
        if (!userService.userNameUniqCheck(registrationRequest.getUserName())) {
            return "login-exist";
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
                 userService.create(User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .userName(registrationRequest.getUserName())
                .password(registrationRequest.getPassword())
                .build());
        return "redirect:/login";
    }

}
