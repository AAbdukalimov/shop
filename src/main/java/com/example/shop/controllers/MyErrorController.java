package com.example.shop.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    @RequestMapping("/auth-error")
    public String authError(){
        return "auth-error";
    }

    @RequestMapping("/login-exist")
    public String loginExist(){
        return "login-exist";
    }

}
